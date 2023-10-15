package com.wendy.backendassignment.config;

import com.wendy.backendassignment.filter.JwtRequestFilter;
import com.wendy.backendassignment.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()

                //User
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.GET,"/{username}").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.POST,"/users/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "{username}/authorities/{authority}").hasRole("ADMIN")

                //Calendars
                .requestMatchers(HttpMethod.GET, "/calendars").permitAll()
                .requestMatchers(HttpMethod.GET, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/calendars").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")


                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
