package com.coffee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coffee.model.CartItem;
import com.coffee.model.Product;
import com.coffee.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
	 @Autowired
	    private ProductService productService;

	    @GetMapping
	    public String viewCart(HttpSession session, Model model) {
	        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	        if (cart == null) {
	            cart = new ArrayList<>();
	            session.setAttribute("cart", cart);
	        }
	        model.addAttribute("cartItems", cart);
	        return "cart";
	    }

	    @PostMapping("/add")
	    public String addToCart(@RequestParam Long productId, HttpSession session) {
	        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	        if (cart == null) {
	            cart = new ArrayList<>();
	            session.setAttribute("cart", cart);
	        }

	        Product product = productService.getProductById(productId);
	        if (product != null) {
	            boolean exists = false;
	            for (CartItem cartItem : cart) {
	                if (cartItem.getProductName().equals(product.getName())) {
	                    cartItem.setQuantity(cartItem.getQuantity() + 1);
	                    exists = true;
	                    break;
	                }
	            }

	            if (!exists) {
	                CartItem newItem = new CartItem(
	                    product.getName(),
	                    product.getDescription(),
	                    product.getPrice(),
	                    product.getCategory(),
	                    product.getImageUrl(),
	                    1
	                );
	                cart.add(newItem);
	            }
	        }
	        return "redirect:/cart";
	    }
	    
	    @PostMapping("/remove")
	    public String removeFromCart(@RequestParam String productName, HttpSession session) {
	        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	        if (cart != null) {
	            cart.removeIf(item -> item.getProductName().equals(productName));
	            session.setAttribute("cart", cart);
	        }
	        return "redirect:/cart";
	    }
	    
	    @PostMapping("/clear")
	    public String clearCart(HttpSession session) {
	        session.removeAttribute("cart");
	        return "redirect:/cart";
	    }
	    
	    @PostMapping("/checkout")
	    public String checkout(HttpSession session, Model model) {
	        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	        if (cart == null || cart.isEmpty()) {
	            model.addAttribute("error", "Giỏ hàng của bạn trống!");
	            return "cart";
	        }

	        double totalAmount = 0;
	        int totalQuantity = 0;
	        for (CartItem item : cart) {
	            totalAmount += item.getPrice() * item.getQuantity();
	            totalQuantity += item.getQuantity();
	        }

	        model.addAttribute("cartItems", cart);
	        model.addAttribute("totalAmount", totalAmount);
	        model.addAttribute("totalQuantity", totalQuantity);

	        session.removeAttribute("cart");

	        return "order-summary";
	    }
	    
}
