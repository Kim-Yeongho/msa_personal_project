package com.example.msa_exam.auth.core;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    private Long userId;
    private String username;
    private String password;
    private String role;

}
