package com.example.msa_exam.order;


import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderRequestDto {

    private final Long productId;
//    @Setter
//    private Integer quantity;

//    public OrderRequestDto(Long productId, Integer quantity) {
    public OrderRequestDto(Long productId) {
        this.productId = productId;
//        this.quantity = quantity;
    }
}
