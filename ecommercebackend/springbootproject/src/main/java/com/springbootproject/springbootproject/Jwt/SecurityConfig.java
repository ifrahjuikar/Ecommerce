// package com.springbootproject.springbootproject.Jwt;

// import java.net.PasswordAuthentication;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfig {

//     // Autowiring JwtAuthenticationEntryPoint handles authentication failure
//     @Autowired
//     private JwtAuthenticationEntryPoint point;
    
//     // Autowiring JwtAuthenticationFilter perform jwt authentication
//     @Autowired
//     private JwtAuthenticationFilter filter;

//     @Autowired
//     private UserDetailsService userDetailsService;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     // Define SecurityFilterChain bean for security setting
//     @Deprecated
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         // Configure HttpSecurity
//         http.csrf(csrf -> csrf.disable()) // Disable CSRF protection
//             .authorizeRequests(requests -> requests
//                 .requestMatchers("/authenticate").permitAll() // Allow access to "/authenticate" endpoint without authentication to all
//                         )            .exceptionHandling(ex -> ex.authenticationEntryPoint(point)) // Set custom authentication entry point to handle error renponse
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Set session management to STATELESS
        
//         // Add JwtAuthenticationFilter-it perform first before UsernamePasswordAuthenticationFilter
//         http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        
//         // Build and return SecurityFilterChain- security setting
//         return http.build();
//     }

//      @Bean
//     public DaoAuthenticationProvider doDaoAuthenticationProvider(){
//         DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
//         doDaoAuthenticationProvider().setUserDetailsService(userDetailsService);
//         doDaoAuthenticationProvider().setPasswordEncoder(passwordEncoder);
//         return daoAuthenticationProvider;
//     }

// }









package com.springbootproject.springbootproject.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/authenticate","/registerNewUser").permitAll()
                .anyRequest().authenticated())
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(point))
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}






