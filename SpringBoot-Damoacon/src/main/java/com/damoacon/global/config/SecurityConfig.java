package com.damoacon.global.config;

import com.damoacon.global.handler.ExceptionHandlerFilter;
import com.damoacon.global.util.JwtUtil;
import com.damoacon.global.util.ResponseUtil;
import com.damoacon.global.util.filter.JwtAuthenticationProcessingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final ResponseUtil responseUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(httpRequests -> httpRequests
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")
                                , new AntPathRequestMatcher("/css/**")
                                , new AntPathRequestMatcher("/images/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAfter(new JwtAuthenticationProcessingFilter(jwtUtil, responseUtil), LogoutFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(responseUtil), JwtAuthenticationProcessingFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/api/v1/member/login/oauth/google")
                .requestMatchers("/api/v1/member/login/oauth/google/callback")
                .requestMatchers("/api/v1/event")
                .requestMatchers("/api/v1/event/search")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}