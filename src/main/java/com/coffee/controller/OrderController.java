package com.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffee.model.CartItem;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {
    @PostMapping("/place")
    public String placeOrder(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "redirect:/cart";
        }

        double totalAmount = cart.stream()
                                 .mapToDouble(item -> item.getPrice() * item.getQuantity())
                                 .sum();

        model.addAttribute("cartItems", cart);
        model.addAttribute("totalAmount", totalAmount);

        // Clear cart after placing order
        session.removeAttribute("cart");

        return "order_summary";
    }
}