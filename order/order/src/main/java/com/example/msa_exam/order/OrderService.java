package com.example.msa_exam.order;

import com.example.msa_exam.order.core.Order;
import com.example.msa_exam.order.core.OrderProduct;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j(topic = "orderService=====")
public class OrderService {

//    private final ProductClient productClient;
    private final RestTemplate restTemplate;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void registerEventListener() {
        circuitBreakerRegistry.circuitBreaker("OrderService").getEventPublisher()
                .onStateTransition(event -> log.info("CircuitBreaker State Transition: {}", event))
                .onFailureRateExceeded(event -> log.info("CircuitBreaker Failure Rate Exceeded: {}", event))
                .onCallNotPermitted(event -> log.info("CircuitBreaker Call Not Permitted: {}", event))
                .onError(event -> log.info("CircuitBreaker Error: {}", event));
    }


    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackSaveOrder")
    public String saveOrder(List<OrderRequestDto> orderItems) {

//        List<ProductResponseDto> products = productClient.getResponse();

        //product-service에서 가져옴
        ProductResponseDto[] forObject = restTemplate.getForObject("http://product-service/products", ProductResponseDto[].class);
        List<ProductResponseDto> products =Arrays.stream(forObject).toList();


        List<OrderRequestDto> filteredOrderItems = orderItems.stream()
                .filter(orderItem -> products.stream()
                        .anyMatch(product -> product.getProductId() == orderItem.getProductId()))
                .toList();

        Order order = new Order();

        for (OrderRequestDto filteredOrderItem : filteredOrderItems) {
            OrderProduct orderProduct = new OrderProduct(filteredOrderItem.getProductId(), order);
            orderProductRepository.save(orderProduct);
        }
        orderRepository.save(order);


        return "saved Order";
    }

    public String fallbackSaveOrder(List<OrderRequestDto> orderItems, Throwable t) {
        log.error("####Fallback triggered for SaveOrderd >>> due to: {}", t.getMessage());
        return "잠시 후에 주문 추가를 요청 해주세요.";
    }


    public String updateOrder(Long orderId, Long productId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("OrderProduct not foud"));
        OrderProduct orderProduct = new OrderProduct(productId, order);
        orderProductRepository.save(orderProduct);

        return "updated Order";
    }

    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not foud"));
        OrderResponseDto orderResponseDto = new OrderResponseDto(orderId);
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            orderResponseDto.setProductIds(orderProduct.getProductId());
        }

        return orderResponseDto;
    }
}