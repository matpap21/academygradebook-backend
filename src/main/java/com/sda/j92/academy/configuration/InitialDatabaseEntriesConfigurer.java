package com.sda.j92.academy.configuration;

import com.sda.j92.academy.model.ApplicationUser;
import com.sda.j92.academy.model.ApplicationUserRole;
import com.sda.j92.academy.repository.ApplicationUserRepository;
import com.sda.j92.academy.repository.ApplicationUserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDatabaseEntriesConfigurer implements ApplicationListener<ContextRefreshedEvent> {
    private final static String ADMIN_USERNAME = "admin";
    private final static String ADMIN_PASSWORD = "admin";

    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_USER = "ROLE_USER";
    public final static String ROLE_LECTURER = "ROLE_LECTURER";

    private final static String[] AVAILABLE_ROLES = {ROLE_ADMIN, ROLE_USER, ROLE_LECTURER};

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserRoleRepository applicationUserRoleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createRoles();
        createUsers();
    }

    private void createUsers() {
        addUserIfNotExist(ADMIN_USERNAME, ADMIN_PASSWORD, Arrays.asList(AVAILABLE_ROLES),"Administrator", "Administrator","");

//        addUserIfNotExist("user", "user", Arrays.asList(ROLE_USER));
//        addUserIfNotExist("lecturer", "lecturer", Arrays.asList(ROLE_LECTURER));
    }

    private void addUserIfNotExist(String username, String password, List<String> availableRoles, String name, String surname, String pesel ) {
        if(!applicationUserRepository.existsByUsername(username)){

            Set<ApplicationUserRole> roles = new HashSet();
            for (String role : availableRoles) {
                Optional<ApplicationUserRole> optionalRole = applicationUserRoleRepository.findByName(role);
                if (optionalRole.isPresent()){
                    roles.add(optionalRole.get());
                }else{
                    log.error("Could not find role: " + role);
                }
            }

            ApplicationUser user = ApplicationUser.builder()
                    .username(username)
                    .password(bCryptPasswordEncoder.encode(password))
                    .name (name)
                    .surname (surname)
                    .pesel (pesel)
                    .roles(roles)
                    .build();

            applicationUserRepository.save(user);
        }
    }

    private void createRoles() {
        for (String availableRole : AVAILABLE_ROLES) {
            addIfNotExist(availableRole);
        }
    }

    private void addIfNotExist(String availableRole) {
        if(!applicationUserRoleRepository.existsByName(availableRole)){
            ApplicationUserRole role = ApplicationUserRole.builder().name(availableRole).build();
            applicationUserRoleRepository.save(role);
        }
    }
}
