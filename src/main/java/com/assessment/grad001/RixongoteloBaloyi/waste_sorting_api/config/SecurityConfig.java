package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.config;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.jwt.AuthEntryPointJwt;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(AuthEntryPointJwt unauthorizedHandler, AuthTokenFilter authTokenFilter, UserDetailsService userDetailsService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.authTokenFilter = authTokenFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(List.of(authProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/api/v1/users/register").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/recycling-tips/**", "/api/v1/waste-categories/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/disposal-guidelines/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/recycling-tips", "/api/v1/waste-categories", "/api/v1/disposal-guidelines").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/api/v1/recycling-tips/**", "/api/v1/waste-categories/**", "/api/v1/disposal-guidelines/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/recycling-tips/**", "/api/v1/waste-categories/**", "/api/v1/disposal-guidelines/**").hasRole("ADMIN")
                            .requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/users/add-role/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/v1/users/").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
                            .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




}
