package com.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffee.model.About;
import com.coffee.model.Menu;
import com.coffee.model.Product;
import com.coffee.service.AboutService;
import com.coffee.service.MenuService;
import com.coffee.service.ProductService;
import com.coffee.service.TestimonialService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MenuController {
	@Autowired
	public MenuService menuService;
	@Autowired
	public AboutService aboutService;
	@Autowired 
	public TestimonialService testimonialService;
	@Autowired
    private ProductService productService;

	@GetMapping("/home")

	public String home(Model model, HttpSession session) {
		List<Menu> list = menuService.getList();
		String username = (String) session.getAttribute("currentUserName");
		if (username != null) {
			list.removeIf(menu -> "/login".equals(menu.getLink()));
		}
		model.addAttribute("listMenu", list);
		model.addAttribute("active", "trang chủ");
		model.addAttribute("username", username);
		
		About about = aboutService.getAboutInfo();
	    model.addAttribute("about", about);
	    
	    model.addAttribute("testimonials", testimonialService.getAllTestimonials());
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
		return "index";
	}
}
