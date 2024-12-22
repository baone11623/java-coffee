package com.coffee.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffee.model.CartItem;
import com.coffee.model.Order;
import com.coffee.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
    @PostMapping("/place")
    public String placeOrder(HttpSession session, Model model) {
        // Lấy giỏ hàng từ session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "redirect:/cart";
        }
        
        if (orderService == null) {
            throw new IllegalStateException("OrderService is not initialized");
        }

        // Duyệt qua các sản phẩm trong giỏ hàng và lưu thông tin đặt hàng
        for (CartItem item : cart) {
            Order order = new Order();
            order.setUsername((String) session.getAttribute("username")); // Giả sử bạn lưu tên khách hàng trong session
            order.setProductName(item.getProductName());
            order.setQuantity(item.getQuantity());
            order.setPrice(item.getPrice());
            order.setOrderDate(LocalDateTime.now());

            orderService.saveOrder(order);
        }

        // Clear giỏ hàng sau khi đặt hàng
        session.removeAttribute("cart");

        model.addAttribute("message", "Đặt hàng thành công!");
        return "redirect:/order/summary"; // Điều hướng đến trang tóm tắt đơn hàng
    }
    
}