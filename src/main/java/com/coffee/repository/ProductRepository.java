package com.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffee.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
