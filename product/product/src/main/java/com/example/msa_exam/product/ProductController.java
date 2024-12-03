package com.example.msa_exam.product;

import com.example.msa_exam.product.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    @Value("${server.port}")
    private String serverPort;

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody List<ProductRequestDto> products) {
        String save = productService.save(products);
        return ResponseEntity.ok().header("Server-Port", serverPort).body(save);

    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductRequestDto>> getProducts() {
        List<ProductRequestDto> products = productService.getProducts();
        return ResponseEntity.ok().header("Server-Port", serverPort).body(products);
    }


}
