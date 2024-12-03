package com.example.msa_exam.product.core;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue
    private Long productId;
    private String name;
    private int supplyPrice;

    public Product(String name, int supplyPrice) {
        this.name = name;
        this.supplyPrice = supplyPrice;
    }
}
