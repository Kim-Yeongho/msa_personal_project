//package com.example.msa_exam.gateway;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class GatewayController {
//    private final ProductClient productClient;
//
//    @GetMapping("/ab")
//    public ResponseEntity<List<ProductResponseDto>> createOrder() {
//        List<ProductResponseDto> products = productClient.getResponse();
//
//        return ResponseEntity.ok(products);
//    }
//}
