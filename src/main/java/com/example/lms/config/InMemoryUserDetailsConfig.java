package com.example.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InMemoryUserDetailsConfig {

    @Bean
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .and()
                .withUser("instructor")
                .password(passwordEncoder.encode("instructor123"))
                .roles("INSTRUCTOR")
                .and()
                .withUser("student")
                .password(passwordEncoder.encode("student123"))
                .roles("STUDENT");
    }
}
