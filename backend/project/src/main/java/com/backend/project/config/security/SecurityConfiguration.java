package com.backend.project.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;
    private final RateLimiterFilter rateLimiterFilter;

    public SecurityConfiguration(SecurityFilter securityFilter, RateLimiterFilter rateLimiterFilter) {
        this.securityFilter = securityFilter;
        this.rateLimiterFilter = rateLimiterFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                    .xssProtection(xss -> xss.disable()) // Desabilitado em favor de CSP
                    .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self'"))
                    .frameOptions(frame -> frame.deny())
                )
                .sessionManagement(session -> session.sessionCreationPolicy
                        (SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(rateLimiterFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }



}
