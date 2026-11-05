package com.college.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Restrict grade management and professor layers solely to Faculty roles
                .requestMatchers("/grades/**", "/professors/**").hasRole("FACULTY")
                // Allow both students and faculty to access the student dashboard/course modules
                .requestMatchers("/", "/courses/**").hasAnyRole("STUDENT", "FACULTY")
                .anyRequest().authenticated()
            )
            // Activates Spring Security's default built-in login form window interface
            .formLogin(form -> form
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // Disables CSRF for easier local testing with dynamic H2 forms
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Faculty credential configurations
        @SuppressWarnings("deprecation")
        UserDetails faculty = User.withDefaultPasswordEncoder()
                .username("faculty@college.edu")
                .password("faculty123")
                .roles("FACULTY")
                .build();

        // Student credential configurations
        @SuppressWarnings("deprecation")
        UserDetails student = User.withDefaultPasswordEncoder()
                .username("student@college.edu")
                .password("student123")
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(faculty, student);
    }
}
