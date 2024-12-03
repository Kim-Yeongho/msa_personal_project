package com.example.msa_exam.product;

import com.example.msa_exam.product.core.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
