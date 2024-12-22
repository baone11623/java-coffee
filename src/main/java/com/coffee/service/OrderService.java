package com.coffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffee.model.Order;
import com.coffee.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
    
    public long countOrders() {
        return orderRepository.count();
    }

    public double calculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .mapToDouble(order -> order.getPrice() * order.getQuantity())
                .sum();
    }
}

