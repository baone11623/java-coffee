package com.coffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffee.model.Testimonial;
import com.coffee.repository.TestimonialRepository;

@Service
public class TestimonialService {
		@Autowired
	  	private TestimonialRepository testimonialRepository;

	    public List<Testimonial> getAllTestimonials() {
	        return testimonialRepository.findAll();
	    }

	    public void addTestimonial(Testimonial testimonial) {
	        testimonialRepository.save(testimonial);
	    }
}
