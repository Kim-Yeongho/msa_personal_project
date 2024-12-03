package com.example.msa_exam.product;

import com.example.msa_exam.product.core.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductRequestDto> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductRequestDto> productRequestDtos = new ArrayList<>();
        for (Product product : products) {
            productRequestDtos.add(new ProductRequestDto(product.getProductId(), product.getName(), product.getSupplyPrice()));
        }

        return productRequestDtos;
    }

    public String save(List<ProductRequestDto> products) {
        for (ProductRequestDto productRequestDto : products) {
            Product product = new Product(productRequestDto.getName(), productRequestDto.getSupplyPrice());
            productRepository.save(product);
        }

        return "Product saved";
    }

}
