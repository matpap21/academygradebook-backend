package com.sda.j92.academy.service;

import com.sda.j92.academy.model.ApplicationUser;
import com.sda.j92.academy.model.ApplicationUserRole;
import com.sda.j92.academy.model.Student;
import com.sda.j92.academy.modelDto.ApplicationUserDto;
import com.sda.j92.academy.modelDto.LecturerDto;
import com.sda.j92.academy.modelDto.RegisterApplicationUserDto;
import com.sda.j92.academy.repository.ApplicationUserRepository;

import com.sda.j92.academy.repository.ApplicationUserRoleRepository;
import com.sda.j92.academy.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserRoleRepository applicationUserRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final StudentRepository studentRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByUsername(username);
        if(applicationUserOptional.isPresent()){
            return applicationUserOptional.get();
        }
        throw new UsernameNotFoundException("User with username: " + username + " was not found.");
    }

    public Set<ApplicationUserRole> loadRolesByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByUsername(username);
        if(applicationUserOptional.isPresent()){
            return applicationUserOptional.get().getRoles();
        }
        throw new UsernameNotFoundException("User with username: " + username + " was not found.");
    }

    public Optional<Long> getLoggedInUserId(Principal principal){
        if (principal != null){
            log.info("Jesteśmy zalogowani, informacja o użytkowniku: " + principal);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            try{
                ApplicationUser userDetails = (ApplicationUser) loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
                return Optional.of(userDetails.getId());
            }catch (UsernameNotFoundException usernameNotFoundException){
                log.info("Nie jesteśmy zalogowani");
            }
        }
        return Optional.empty();
    }

    public boolean isAdmin(Principal principal){ // do zmiany
        if (principal != null){
            log.info("Jesteśmy zalogowani, informacja o użytkowniku: " + principal);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            try{
                ApplicationUser userDetails = (ApplicationUser) loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
                return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_ADMIN");
            }catch (UsernameNotFoundException usernameNotFoundException){
                log.info("Nie jesteśmy zalogowani");
            }
        }
        return false;
    }


    public boolean isLecturer(Principal principal){ // do zmiany
        if (principal != null){
            log.info("Jesteśmy zalogowani, informacja o użytkowniku: " + principal);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            try{
                ApplicationUser userDetails = (ApplicationUser) loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
                return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_LECTURER");
            }catch (UsernameNotFoundException usernameNotFoundException){
                log.info("Nie jesteśmy zalogowani");
            }
        }
        return false;
    }


    public boolean isAdminOrLecturer(Principal principal){ // do zmiany
        if (principal != null){
            log.info("Jesteśmy zalogowani, informacja o użytkowniku: " + principal);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            try{
                ApplicationUser userDetails = (ApplicationUser) loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
                return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch (s -> s.equals ("ROLE_LECTURER") || s.equals ("ROLE_ADMIN"));
            }catch (UsernameNotFoundException usernameNotFoundException){
                log.info("Nie jesteśmy zalogowani");
            }
        }
        return false;
    }


    public boolean isUser(Principal principal){ // do zmiany
        if (principal != null){
            log.info("Jesteśmy zalogowani, informacja o użytkowniku: " + principal);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            try{
                ApplicationUser userDetails = (ApplicationUser) loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
                return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_USER");
            }catch (UsernameNotFoundException usernameNotFoundException){
                log.info("Nie jesteśmy zalogowani");
            }
        }
        return false;
    }


    public ApplicationUserDto getLoggedInUserDto(Long loggedInUserId) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(loggedInUserId);
        if(applicationUserOptional.isPresent()){
            ApplicationUser applicationUser = applicationUserOptional.get();
            return ApplicationUserDto.builder()
                    .id(applicationUser.getId())
                    .username(applicationUser.getUsername())
                    .admin(applicationUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_ADMIN"))
                    .lecturer (applicationUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_LECTURER"))
                    .build();
        }
        throw new UsernameNotFoundException("User with username: " + loggedInUserId + " was not found.");
    }

    public void register(RegisterApplicationUserDto dto) { // ZMODYFIKOWAC DODAC LECTURER'A, zamiast boolean' zrobic String
        String roleName = dto.getRole ();
        Set<ApplicationUserRole> roles = new HashSet ();
        Optional<ApplicationUserRole> optionalRole = applicationUserRoleRepository.findByName(roleName);
        if (optionalRole.isPresent()) {
            roles.add (optionalRole.get ( ));

        } else{
            log.error("Could not find role: " + roleName);
            throw new EntityNotFoundException ("Could not find role: " + roleName);
        }

        ApplicationUser user = ApplicationUser.builder()
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .name (dto.getName())
                .surname (dto.getSurname ())
                .pesel (dto.getPesel ())
                .build();

        applicationUserRepository.save(user);

    }

    public List<LecturerDto> listLecturers() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll ();

        return applicationUserList.stream ()
                .filter (applicationUser -> applicationUser.getAuthorities ().stream ().anyMatch (grantedAuthority -> grantedAuthority.getAuthority ().equals ("ROLE_LECTURER")))
                .map (applicationUser -> {
                    return LecturerDto.builder()
                            .id(applicationUser.getId())
                            .username(applicationUser.getUsername())
                            .pesel (applicationUser.getPesel ())
                            .name (applicationUser.getName ())
                            .surname (applicationUser.getSurname ())
                            .build();
                }).collect(Collectors.toList());
    }

    public List<ApplicationUserDto> listStudents() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll ();

        return applicationUserList.stream ()
                .filter (applicationUser -> applicationUser.getAuthorities ().stream ().anyMatch (grantedAuthority -> grantedAuthority.getAuthority ().equals ("ROLE_USER")))
                .map (applicationUser -> {
                    return ApplicationUserDto.builder()
                            .id(applicationUser.getId())
                            .username(applicationUser.getUsername())
                            .admin(applicationUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_ADMIN"))
                            .lecturer (applicationUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_LECTURER"))
                            .build();
                }).collect(Collectors.toList());
    }

    public void removeUser(Long id) {
        log.info("removeUser: " + id);
        applicationUserRepository.deleteById (id);
    }
}
