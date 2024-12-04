package com.spring.authImplementation.tokenSetup.config;

import com.spring.authImplementation.tokenSetup.daosEntity.UserInfo;
import com.spring.authImplementation.tokenSetup.filter.JwtAuthFilter;
import com.spring.authImplementation.tokenSetup.service.impl.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 This is for Spring boot 3.0
     Below are the mandatory methods::
         1) UserDetailsService  -> For users
         1) securityChain
         2) passwordDecoder
         3) AuthenticationProvider -> FOR Authentication from DAO
         4) AuthenticationManager -> FOR Authentication from DAO
 */

// https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html
@Configuration
@EnableWebSecurity // This Annotation was used for enabling the Spring Security.
@EnableMethodSecurity // This annotation was used for @PreAuthorize the Role as admin, user in the controller class.
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    // ----- Authentication -----
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoService(); // Ensure UserInfoService implements UserDetailsService --> fetching from the repo
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoding
    }

    /**
     *
     * @param
     * @return
     * @throws Exception
     */
    // Authorization -----
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless APIs
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/tokenValidation/postUser",
                                "/tokenValidation/postMultipleUsers",
                                "/tokenValidation/{userID}",
                                "/tokenValidation/getAllUsers",
                                "/tokenValidation/updateSelectiveUser",
                                "/tokenValidation/getAllUsersWithSort/{fieldName}",
                                "/tokenValidation/getAllUsersWithPagination",
                                "/tokenValidation/getAllUsersWithSortAndPagination/{fieldName}",
                                "/tokenValidation/addNewUser",
                                "/tokenValidation/welcome",
                                "/tokenValidation/generateToken").permitAll()
                        .requestMatchers("/tokenValidation/user/userProfile").hasAuthority("ROLE_USER")
                        .requestMatchers("/tokenValidation/admin/adminProfile").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))// No sessions
                .authenticationProvider(authenticationProvider()) // Custom authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build(); // Add JWT filter
        }


    // Authentication ----- (Encrypt the password) ---->
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    // ****** in new impl ***** In JWT
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
