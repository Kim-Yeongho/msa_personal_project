package com.example.msa_exam.order;

import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    @Value("${server.port}")
    private String serverPort;

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderRequestDto> orderItems){
        String order = orderService.saveOrder(orderItems);
        return ResponseEntity.ok().header("Server-Port", serverPort).body(order);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody OrderRequestDto orderItem){
//    public ResponseEntity<String> updateOrder(@PathVariable("orderId") Long orderId){

        String order = orderService.updateOrder(orderId, orderItem.getProductId());
        return ResponseEntity.ok().header("Server-Port", serverPort).body(order);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("orderId") Long orderId){
        OrderResponseDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok().header("Server-Port", serverPort).body(order);
    }

}
