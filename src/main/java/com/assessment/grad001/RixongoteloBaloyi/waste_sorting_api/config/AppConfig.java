package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.config;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.Role;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.User;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.RoleRepository;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    /**
     * This CommandLineRunner bean is executed when the application starts. It performs the following tasks:
     * 1. Checks if the roles "ROLE_ADMIN" and "ROLE_USER" exist in the database. If not, they are created and saved.
     * 2. If the "admin" user does not already exist, it creates a new "admin" user and assigns both roles "ROLE_ADMIN" and "ROLE_USER" to the user.
     *    This is for testing purposes to ensure that the application has an admin user with both roles.
     * 3. The user's password is encoded using the PasswordEncoder before saving to the database.
     */
    @Bean
    public CommandLineRunner initRolesAndUser(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
                Role role = new Role("ROLE_ADMIN");
                return roleRepository.save(role);
            });


            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
                Role role = new Role("ROLE_USER");
                return roleRepository.save(role);
            });

            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("adminPassword")); // Strong password
                user.setRoles(Set.of(adminRole, userRole));
                userRepository.save(user);
                System.out.println("Admin user with both roles (ROLE_ADMIN, ROLE_USER) created.");
            }
        };
    }
}