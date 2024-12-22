package com.coffee.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coffee.model.Order;
import com.coffee.model.Product;
import com.coffee.model.User;
import com.coffee.service.OrderService;
import com.coffee.service.ProductService;
import com.coffee.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		User user = (User) session.getAttribute("currentUser");
		if (user == null || !"admin".equals(user.getRole())) {
			return "redirect:/login";
		}
		model.addAttribute("content", "admin/dashboard");
		return "admin/admin_layout";
	}

	@GetMapping("/statistics")
	public String statistics(Model model) {
	    model.addAttribute("totalOrders", orderService.countOrders());
	    model.addAttribute("totalRevenue", orderService.calculateTotalRevenue());
	    model.addAttribute("totalUsers", userService.countUsers());
	    model.addAttribute("totalProducts", productService.countProducts());

	    model.addAttribute("content", "admin/statistics");
	    return "admin/admin_layout";
	}

	@GetMapping("/products")
	public String manageProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("content", "admin/products");
		return "admin/admin_layout";
	}

	@GetMapping("/products/add")
	public String showAddProductForm(Model model, @RequestParam(required = false) Long id) {
		Product product = (id != null) ? productService.getProductById(id) : new Product();
		model.addAttribute("product", product);
		model.addAttribute("content", "admin/product_form");
		return "admin/admin_layout";
	}

	@PostMapping("/products/add")
	public String addProduct(@ModelAttribute Product product) {
		productService.saveProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/products/edit/{id}")
	public String showEditProductForm(@PathVariable Long id, Model model) {
		Product product = productService.getProductById(id);
		if (product == null) {
			return "redirect:/admin/products";
		}
		model.addAttribute("product", product);
		model.addAttribute("content", "admin/product_form");
		return "admin/admin_layout";
	}
	
	
	@PostMapping("/products/edit")
	public String editProduct(@ModelAttribute Product product) {
		productService.saveProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/admin/products";
	}

	
	@GetMapping("/orders")
	public String manageOrders(Model model) {
		List<Order> orders = orderService.getAllOrders();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		orders.forEach(order -> {
			if (order.getOrderDate() != null) {
				order.setFormattedDate(order.getOrderDate().format(formatter));
			}
		});

		model.addAttribute("orders", orders);
		model.addAttribute("content", "admin/orders");
		return "admin/admin_layout";
	}
	@GetMapping("/users")
	public String manageUsers(Model model) {
	    model.addAttribute("users", userService.getAllUsers()); // Lấy danh sách người dùng
		model.addAttribute("content", "admin/users");
		return "admin/admin_layout";
	}
	@GetMapping("/users/edit/{id}")
	public String showEditUserForm(@PathVariable Long id, Model model) {
	    User user = userService.getUserById(id); 
	    if (user == null) {
	        return "redirect:/admin/users";
	    }
	    model.addAttribute("user", user);
	    model.addAttribute("content", "admin/user_form");
	    return "admin/admin_layout";
	}
	@PostMapping("/users/edit")
	public String editUser(@ModelAttribute User user) {
	    // Kiểm tra nếu mật khẩu trống, giữ mật khẩu cũ
	    if (user.getPassword() == null || user.getPassword().isEmpty()) {
	        User existingUser = userService.getUserById(user.getId());
	        if (existingUser != null) {
	            user.setPassword(existingUser.getPassword());  // Giữ mật khẩu cũ
	        }
	    }
	    userService.save(user); 
	    return "redirect:/admin/users";
	}


	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
	    userService.deleteUser(id); 
	    return "redirect:/admin/users";
	}
}