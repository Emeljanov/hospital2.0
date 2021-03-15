package by.emel.anton.restcontroller.v1;

import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
/*

//@TestConfiguration
public class SpringSecurityTestConfig {

//    @Bean
    public UserDetailsService userDetailsService() {
        User admin = User.builder()
//                .login("ADMIN")
//                .role(Role.ADMIN)
                .build();

        User doctor = User.builder()
//                .login("DOCTOR")
//                .role(Role.DOCTOR)
                .build();

        User patient = User.builder()
//                .login("PATIENT")
                .role(Role.PATIENT)
                .build();

        return new InMemoryUserDetailsManager(Arrays.asList(*/
/*admin,doctor,*//*
patient));
    }
}
*/
