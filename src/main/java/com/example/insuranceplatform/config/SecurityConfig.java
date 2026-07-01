package com.example.insuranceplatform.config;

import com.example.insuranceplatform.exception.CustomAccessDeniedHandler;
import com.example.insuranceplatform.exception.CustomAuthenticationEntryHandler;
import com.example.insuranceplatform.security.JwtTokenFilter;
import com.example.insuranceplatform.security.JwtTokenUtil;
import com.example.insuranceplatform.security.user_details.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryHandler customAuthenticationEntryHandler;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                                    CorsConfiguration config = new CorsConfiguration();
                                    config.setAllowedOrigins(List.of("http://localhost:8080","http://localhost:3000"));
                                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                                    config.setAllowedHeaders(List.of("*"));

                                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                                    source.registerCorsConfiguration("/**", config);
                                    return source.getCorsConfiguration(request);
                                }
                        )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(
                                        "/api/auth/**",
                                        "/v3/api-docs/**",
                                        "/v3/api-docs.yaml",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryHandler)
                )
                .addFilterBefore(new JwtTokenFilter(jwtTokenUtil, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }
}
