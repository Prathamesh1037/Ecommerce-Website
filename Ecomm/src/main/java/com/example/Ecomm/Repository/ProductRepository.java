package com.example.Ecomm.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Ecomm.Entity.Product;
import com.example.Ecomm.Entity.User;

public interface ProductRepository extends JpaRepository<Product ,Long>{

	Optional<Product> findById(Long key);
	
}
