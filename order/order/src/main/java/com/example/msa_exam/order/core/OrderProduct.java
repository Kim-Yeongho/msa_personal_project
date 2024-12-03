package com.example.msa_exam.order.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class OrderProduct {

    @Id @GeneratedValue
    private Long id;
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    public OrderProduct(Long productId, Order order) {
        this.productId = productId;
        this.order = order;
    }
}
