package com.example.msa_exam.order;

import lombok.Getter;

@Getter
public class ProductResponseDto {

    private final Long productId;
    private final String name;
    private final int supplyPrice;

    public ProductResponseDto(Long productId, String name, int supplyPrice) {
        this.productId = productId;
        this.name = name;
        this.supplyPrice = supplyPrice;
    }
}
