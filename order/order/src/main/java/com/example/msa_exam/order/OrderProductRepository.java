package com.example.msa_exam.order;

import com.example.msa_exam.order.core.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
