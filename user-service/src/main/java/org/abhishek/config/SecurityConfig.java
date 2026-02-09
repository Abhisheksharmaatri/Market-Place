package org.abhishek.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // REQUIRED: prevents POST/PUT/DELETE 403
                .csrf(csrf -> csrf.disable())

                // CRITICAL: allow everything
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // Disable all default auth mechanisms
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                // Prevent request caching redirect logic
                .requestCache(cache -> cache.disable())

                // Keep anonymous (required for permitAll)
                .anonymous();

        return http.build();
    }
}
