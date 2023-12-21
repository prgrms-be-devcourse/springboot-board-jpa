package com.example.board.config;

import com.example.board.exception.CustomAuthenticationEntryPoint;
import com.example.board.exception.ExceptionHandlerFilter;
import com.example.board.jwt.JwtAuthFilter;
import com.example.board.jwt.JwtRefreshAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtRefreshAuthFilter jwtRefreshAuthFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        List<RequestMatcher> permitAllMatchers = List.of(
                new AntPathRequestMatcher("/api/*/users/sign-up", HttpMethod.POST.toString()),
                new AntPathRequestMatcher("/api/*/users/sign-in", HttpMethod.POST.toString()),
                new AntPathRequestMatcher("/api/*/users", HttpMethod.POST.toString()),
                new AntPathRequestMatcher("/api/*/posts", HttpMethod.GET.toString())
        );

        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(permitAllMatchers.toArray(new RequestMatcher[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(exceptionHandlerFilter, JwtAuthFilter.class)
                .addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtRefreshAuthFilter.class)
                .addFilterBefore(jwtRefreshAuthFilter, BasicAuthenticationFilter.class)
                .build();
    }
}
