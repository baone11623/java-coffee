package com.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffee.model.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
}
