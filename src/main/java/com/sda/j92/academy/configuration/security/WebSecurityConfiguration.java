package com.sda.j92.academy.configuration.security;

import com.sda.j92.academy.service.ApplicationUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    DONE - Rozróżnianie użytkowników po stronie backendu
//    Rozróżnianie użytkowników po stronie frontendu (+informacja o uprwanieniach)
//    (przechowywanie informacji o identyfikatorze gdy pracujemy wewnątrz aplikacji)
//    (np. ktoś wysyła zapytanie i chcemy wiedzieć jaki jest ID użytkownika który wysłał zapytanie)
//    Rejestracja użytkowników

    private ApplicationUserService applicationUserService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfiguration(ApplicationUserService applicationUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserService = applicationUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors ( ).and ( ).csrf ( ).disable ( )
                .authorizeRequests ( )
                .antMatchers(
                        "/**",
                        "/api/**",
                        "/static",
                        "/asset-manifest.json",
                        "/favicon.ico",
                        "/index.html",
                        "/manifest.json",
                        "/service-worker.js").permitAll()
                .antMatchers ("/api/login", "/login").permitAll ( )
                .antMatchers ("/api/user/register", "/user/register").permitAll ( ) // jeśli macie tą linie, to każdy może się zarejestrować
                // jeśli nie macie tej linii, to zarejestrować może tylko zalogowana osoba

//                // DODAWANIE ROL DLA POSZCZEGOLNYCH USEROW/GRUP
                .antMatchers ("/api/grades/${studentId}").hasAnyRole ("USER", "LECTURER", "ADMIN")

                .antMatchers ("/api/academicgroups/**").hasAnyRole ("ADMIN", "LECTURER")

                .antMatchers ( "/api/fieldofstudy/**").hasRole ("ADMIN")

                .antMatchers ("/api/academicsubjects/**").hasAnyRole ("ADMIN", "LECTURER")

                .antMatchers ("/api/grades/**").hasAnyRole ("ADMIN", "LECTURER")

                .antMatchers ("/api/universitylecturers/**").hasRole ("ADMIN")

                .antMatchers ("/api/students/**").hasAnyRole ("ADMIN", "LECTURER")

//
//                // DODAWANIE ROL DLA POSZCZEGOLNYCH USEROW/GRUP

                .anyRequest ( ).authenticated ( )
                .and ( )
                .addFilter (new JWTAuthenticationFilter (authenticationManager ( ), applicationUserService))
                .addFilter (new JWTAuthorizationFilter (authenticationManager ( )))
                .sessionManagement ( ).sessionCreationPolicy (SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService (applicationUserService).passwordEncoder (bCryptPasswordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration ( );
        configuration.setAllowedOrigins (Arrays.asList ("http://localhost:3000/", "http://localhost:*/"));
        configuration.setAllowedMethods (Arrays.asList ("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials (true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.addAllowedOriginPattern ("http://localhost:*/");
        configuration.setAllowedHeaders (Arrays.asList ("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ( );
        source.registerCorsConfiguration ("/**", configuration);
        return source;
    }
}
