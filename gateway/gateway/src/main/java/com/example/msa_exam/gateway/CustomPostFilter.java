package com.example.msa_exam.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j(topic = "CustomPostFilter")
@Component
public class CustomPostFilter implements GlobalFilter, Ordered {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.beforeCommit(()->{
//            response.getHeaders().set("Server-Port", serverPort);
//            return Mono.empty();
//        });
//        log.info("Server-Port: {}", serverPort);
//        return chain.filter(exchange);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            log.info("Post Filter: Response status code is " + response.getStatusCode());
            // Add any custom logic here
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
