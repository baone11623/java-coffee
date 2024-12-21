package com.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffee.model.Menu;
import com.coffee.model.Product;
import com.coffee.service.MenuService;
import com.coffee.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	public MenuService menuService; 
	@Autowired
    private ProductService productService;
	@GetMapping
	public String product(Model model,HttpSession session) {
		List<Menu> list = menuService.getList();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			list.removeIf(menu -> "/login".equals(menu.getLink()));
		}
		model.addAttribute("listMenu", list);
		model.addAttribute("active","product");
		model.addAttribute("username", username);
		
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        
		return "products";
	}

	    @GetMapping("/{id}")
	    public String getProductById(@PathVariable Long id, Model model) {
			List<Menu> list = menuService.getList();
			model.addAttribute("listMenu", list);
			model.addAttribute("active","product");
	        Product product = productService.getProductById(id);
	        model.addAttribute("product", product);
	        return "product-detail";
	    }

	    @GetMapping("/add")
	    public String showAddProductForm(Model model) {
	        model.addAttribute("product", new Product());
	        return "product-form";
	    }

	    @PostMapping
	    public String saveProduct(@ModelAttribute Product product) {
	        productService.saveProduct(product);
	        return "redirect:/products";
	    }

	    @GetMapping("/delete/{id}")
	    public String deleteProduct(@PathVariable Long id) {
	        productService.deleteProduct(id);
	        return "redirect:/products";
	    }
}
