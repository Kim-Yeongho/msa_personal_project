package com.example.msa_exam.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCreateDto {
    private String name;
    private int supplyPrice;

}
