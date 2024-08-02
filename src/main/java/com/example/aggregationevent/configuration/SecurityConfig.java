package com.example.aggregationevent.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests(auth ->{

            auth
                    .requestMatchers("/api/v1/users/role").permitAll()
                    .requestMatchers("/api/v1/signin").permitAll()
                    .requestMatchers("/api/v1/users/role/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/events").hasAuthority("USER")
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/user").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/v1/events/event").hasAuthority("EVENT MANAGER")
                    .requestMatchers(HttpMethod.PATCH, "/api/v1/events/{id}").hasAuthority("EVENT MANAGER")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").hasAuthority("ADMIN")
                    .anyRequest().authenticated();
        }) .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
