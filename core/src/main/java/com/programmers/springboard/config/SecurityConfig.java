package com.programmers.springboard.config;

import com.programmers.springboard.jwt.Jwt;
import com.programmers.springboard.jwt.JwtAuthenticationFilter;
import com.programmers.springboard.jwt.JwtAuthenticationProvider;
import com.programmers.springboard.repository.PostRepository;
import com.programmers.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationContext applicationContext;
    private final JwtConfigure jwtConfigure;

    private final PostRepository postRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring()
                    .requestMatchers(

                            new AntPathRequestMatcher("/assets/**"),
                            new AntPathRequestMatcher("/h2-console/**"));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Jwt jwt() {
        return new Jwt(
                jwtConfigure.getIssuer(),
                jwtConfigure.getClientSecret(),
                jwtConfigure.getExpirySeconds()
        );
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, MemberService memberService){
        return new JwtAuthenticationProvider(jwt, memberService);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        Jwt jwt = applicationContext.getBean(Jwt.class);
        return new JwtAuthenticationFilter(jwtConfigure.getHeader(), jwt);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/{postId}", HttpMethod.DELETE.name())).hasAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/{postId}", HttpMethod.PUT.name())).hasAuthority("ADMIN")

                        .requestMatchers(new AntPathRequestMatcher("/api/v1/users/{member-id}/group/{group-id}", HttpMethod.PUT.name())).hasAuthority("ADMIN")
                        .anyRequest().permitAll()
                )
                .headers(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtAuthenticationFilter(), SecurityContextPersistenceFilter.class);

        return http.build();
    }
}
