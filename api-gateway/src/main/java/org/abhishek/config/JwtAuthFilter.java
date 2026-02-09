package org.abhishek.config;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;


@Component
public class JwtAuthFilter
        extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {


        return (exchange, chain) -> {
            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                return chain.filter(exchange);
            }

            // 1️⃣ Check Authorization header
            if (!exchange.getRequest().getHeaders()
                    .containsKey(HttpHeaders.AUTHORIZATION)) {

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader =
                    exchange.getRequest().getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION);

            // 2️⃣ Check Bearer token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            try {
                // 3️⃣ Validate token
                Claims claims = jwtUtil.validateAndExtractClaims(token);

                // 4️⃣ Forward user info to downstream services
                exchange = exchange.mutate()
                        .request(
                                exchange.getRequest().mutate()
                                        .header("X-User-Id", claims.getSubject())
                                        .header("X-User-Role",
                                                claims.get("role", String.class))
                                        .build()
                        )
                        .build();

            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}

