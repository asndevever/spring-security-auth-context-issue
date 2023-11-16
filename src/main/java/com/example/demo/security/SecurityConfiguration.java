package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    var authFilter = new MockAuthorizationFilter();

    http.securityContext((securityContext) -> securityContext
            .securityContextRepository(new RequestAttributeSecurityContextRepository())
        )

        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(config -> {
          config.requestMatchers("/graphql").authenticated();
          config.requestMatchers("/error").permitAll();
          config.anyRequest().authenticated();
        })
        .addFilterAfter(authFilter, UsernamePasswordAuthenticationFilter.class);



    return http.build();
  }
}
