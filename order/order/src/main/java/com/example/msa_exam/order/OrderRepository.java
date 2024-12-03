package com.example.msa_exam.order;

import com.example.msa_exam.order.core.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
