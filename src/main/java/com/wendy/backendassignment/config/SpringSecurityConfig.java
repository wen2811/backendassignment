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
                .requestMatchers("/**").permitAll()

                //User
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.GET,"/{username}").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.POST,"/users/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "{username}/authorities/{authority}").hasRole("ADMIN")

                //Bookings
                .requestMatchers(HttpMethod.GET, "/bookings").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/bookings/{id}").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/bookings/").permitAll()
                .requestMatchers(HttpMethod.PUT, "/bookings/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/bookings/{id}").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/bookings/updateTreatments/{id}").hasAnyRole("hasRole('ADMIN') or hasRole('EMPLOYEE') or (principal.username == #booking.customer.username)")
                .requestMatchers(HttpMethod.POST, "/registerbookings").permitAll()
                .requestMatchers(HttpMethod.GET, "/customerbookings/{customerId}").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/createWithoutRegistration").permitAll()
                .requestMatchers(HttpMethod.POST, "/createinvoices").permitAll()

                // BookingTreatments
                .requestMatchers(HttpMethod.GET, "/bookingtreatments").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/bookingtreatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/bookingtreatments").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/bookingtreatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/bookingtreatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/bookingtreatments/add/{treatmentId}").permitAll()


                //Treatments
                .requestMatchers(HttpMethod.GET, "/treatments").permitAll()
                .requestMatchers(HttpMethod.GET, "/treatments/{type}").permitAll()
                .requestMatchers(HttpMethod.POST, "/treatments").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/treatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/treatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/{id}/updatewithcalendar").permitAll()
                .requestMatchers(HttpMethod.GET, "/treatments/{id}/with-calendar").permitAll()


                //Calendars
                .requestMatchers(HttpMethod.GET, "/calendars").permitAll()
                .requestMatchers(HttpMethod.GET, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/calendars").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")

                //Customers
                .requestMatchers(HttpMethod.GET, "/customers").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/customers/{id}").hasAnyRole("hasRole('ADMIN') or hasRole('EMPLOYEE') or (principal.username == #booking.customer.username)")
                .requestMatchers(HttpMethod.POST, "/customers").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/customers/{id}").hasAnyRole("hasRole('ADMIN') or hasRole('EMPLOYEE') or (principal.username == #booking.customer.username)")
                .requestMatchers(HttpMethod.DELETE, "/customers/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/customers/{customerId}/invoices").hasAnyRole("ADMIN", "EMPLOYEE")

                //Invoices
                .requestMatchers(HttpMethod.GET, "/invoices").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/invoices/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/invoices/new").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/invoices/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/invoices/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/{bookingId}/invoices").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/{customerId}/invoices").hasAnyRole("ADMIN", "EMPLOYEE")

                //Files
                .requestMatchers(HttpMethod.POST, "/upload").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/store").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/get/{fileId}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/assign/{fileId}/to-customer/{customerId}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/download/{fileId}").hasAnyRole("hasRole('ADMIN') or hasRole('EMPLOYEE') or (principal.username == #booking.customer.username)")
                .requestMatchers(HttpMethod.DELETE, "/{id}").hasAnyRole("ADMIN", "EMPLOYEE")

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
