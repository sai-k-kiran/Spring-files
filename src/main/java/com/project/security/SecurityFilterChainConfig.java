package com.project.security;

import com.project.jwt.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthFilter JWTAuthFilter;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                     JWTAuthFilter JWTAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.JWTAuthFilter = JWTAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers")
                        .permitAll().anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        JWTAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

}
