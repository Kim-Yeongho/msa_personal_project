package com.example.msa_exam.order;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {

    private Long orderId;
    @Setter
    private List<Long> productIds;

    public OrderResponseDto(Long orderId) {
        this.orderId = orderId;
        this.productIds = new ArrayList<Long>();
    }

    public void setProductIds(Long productId) {
        this.productIds.add(productId);
    }
}
