package com.example.msa_exam.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalJwtAuthenticationFilter implements GlobalFilter {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    private final UserRepository userRepository;    // 여기서 검증하려면 이렇게 해도 되는건지...

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.equals("/auth/sign-up") || path.equals("/auth/sign-in") || path.equals("/ab")) {
            return chain.filter(exchange);
        }

        String token = extractJwtToken(exchange);

        if (token == null || !validateToken(token, exchange)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private String extractJwtToken(ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token, ServerWebExchange exchange) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token); //여기서 expiration 검사를 하네
            log.info("#####payload : " + claimsJws.getPayload().toString());
            Claims claims = claimsJws.getBody();

            Date now = new Date();
            boolean isNotExpired = now.after(claims.getIssuedAt()) && now.before(claims.getExpiration());

            if (!isNotExpired) return false;

            int userCount = userRepository.findUserCount(claims.get("user_id").toString(), claims.get("role").toString());  // 필터에서 직접 DB에 가는 것 말고 다른 방법이 있나...

            return userCount > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
