//package org.abhishek.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                // ✅ Enable CORS
//                .cors(cors -> {})
//
//                // Disable CSRF (since you're allowing public access)
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()
//                )
//
//                .httpBasic(basic -> basic.disable())
//                .formLogin(form -> form.disable())
//                .requestCache(cache -> cache.disable())
//                .anonymous();
//
//        return http.build();
//    }
//
//    // ✅ CORS Configuration Bean (ADDED)
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////
////        configuration.setAllowedOriginPatterns(List.of("*"));
////        // Allows all origins including localhost
////
////        configuration.setAllowedMethods(List.of(
////                "GET", "POST", "PUT", "DELETE", "OPTIONS"
////        ));
////
////        configuration.setAllowedHeaders(List.of("*"));
////        configuration.setAllowCredentials(true); // remove if not using cookies/JWT in cookies
////
////        UrlBasedCorsConfigurationSource source =
////                new UrlBasedCorsConfigurationSource();
////
////        source.registerCorsConfiguration("/**", configuration);
////
////        return source;
////    }
//}
