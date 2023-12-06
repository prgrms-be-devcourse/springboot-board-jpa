package com.example.board.global.security.config;


import com.example.board.global.security.detailsService.MemberDetailsService;
import com.example.board.global.security.handler.JwtAccessDeniedHandler;
import com.example.board.global.security.handler.JwtAuthenticationEntryPoint;
import com.example.board.global.security.jwt.filter.JwtExceptionFilter;
import com.example.board.global.security.jwt.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberDetailsService memberDetailsService;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtFilter jwtFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
            .requestMatchers("/h2-console/**", "/static/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .httpBasic(Customizer.withDefaults())
            .exceptionHandling(exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtFilter.class)
            .userDetailsService(memberDetailsService)

            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                .requestMatchers(HttpMethod.PATCH, "/api/v1/members/{id}").access(new WebExpressionAuthorizationManager("@memberGuard.check(#id)"))
                .requestMatchers(HttpMethod.DELETE, "/api/v1/members/list").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/members/{id}").access(new WebExpressionAuthorizationManager("@memberGuard.check(#id)"))
                .requestMatchers(HttpMethod.DELETE, "/api/v1/members").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.PATCH, "/api/v1/posts/{id}").access(new WebExpressionAuthorizationManager("@postGuard.check(#id)"))
                .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/list").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/{id}").access(new WebExpressionAuthorizationManager("@postGuard.check(#id)"))
                .requestMatchers(HttpMethod.DELETE, "/api/v1/posts").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.PATCH, "/api/v1/comments/{commentId}").access(new WebExpressionAuthorizationManager("@commentGuard.check(#commentId)"))
                .requestMatchers(HttpMethod.DELETE, "/api/v1/comments/{commentId}").access(new WebExpressionAuthorizationManager("@commentGuard.check(#commentId)"))
                .anyRequest().permitAll()

            );
        return http.build();
    }
}
