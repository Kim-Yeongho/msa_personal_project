package com.example.msa_exam.gateway;

import com.example.msa_exam.gateway.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u) from User u where u.userId = :id and u.role = :role")
    int findUserCount(@Param("id") String id, @Param("role") String role);

}
