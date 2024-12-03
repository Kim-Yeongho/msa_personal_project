package com.example.msa_exam.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private Long productId;
    private String name;
    private int supplyPrice;

    public ProductRequestDto(Long productId, String name, int supplyPrice) {
        this.productId = productId;
        this.name = name;
        this.supplyPrice = supplyPrice;
    }


}

