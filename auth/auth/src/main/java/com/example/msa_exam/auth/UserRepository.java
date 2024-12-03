package com.example.msa_exam.auth;

import com.example.msa_exam.auth.core.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
