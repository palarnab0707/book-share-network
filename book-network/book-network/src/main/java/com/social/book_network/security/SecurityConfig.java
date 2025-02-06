package com.social.book_network.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //this is not reactive so only this security annotation is will be fine
@RequiredArgsConstructor //we use it to inject/create constructor for private and final fields
@EnableMethodSecurity(securedEnabled = true) //this will give method leve security
public class SecurityConfig {
    //At first in security we need to provide a bean of SecurityFilterChain to filter the requests, it is as per the diagram
    //any request will first pass through here

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults()) //Spring will try to find any CorsFilter available in application, Which is available in BeanConfig.class
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html").permitAll() //This will permit all the request inside requestmatchers without authentication
                                .anyRequest() //other then above request
                                .authenticated() // any request it will authenticate
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Its mean application will not store any session, and it will pretend that any request as new/unknown request
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //Its mean that it will first check token is exist
                // and validate the jwt token and user already exist and will check the flow is correct or not
                // then it will go to Usernamepasswordauthfilter class
                // jwtAuthFilter and authenticationProvider will be created by us
        return http.build();
    }
}
