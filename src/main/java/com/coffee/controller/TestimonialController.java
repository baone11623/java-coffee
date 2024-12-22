package com.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffee.model.Menu;
import com.coffee.service.MenuService;
import com.coffee.service.TestimonialService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TestimonialController {
	@Autowired
	public MenuService menuService; 
	@Autowired 
	public TestimonialService testimonialService;
	@GetMapping("/testimonial")
	public String testimonial(Model model,HttpSession session) {
		List<Menu> list = menuService.getList();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			list.removeIf(menu -> "/login".equals(menu.getLink()));
		}
		model.addAttribute("listMenu", list);
		model.addAttribute("active","Đánh giá");
		model.addAttribute("username", username);
		
		model.addAttribute("testimonials", testimonialService.getAllTestimonials());
		return "testimonial";
	}
}
