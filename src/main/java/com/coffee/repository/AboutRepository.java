package com.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffee.model.About;

public interface AboutRepository extends JpaRepository<About, Long> {
}