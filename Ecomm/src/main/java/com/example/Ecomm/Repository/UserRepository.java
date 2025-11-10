package com.example.Ecomm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecomm.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Custom queries should match field types:
    User findByEmail(String email);
    User findByName(String name);
}

