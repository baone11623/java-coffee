package com.coffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffee.model.User;
import com.coffee.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthenController {
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@Autowired
	public UserService userService;

	@PostMapping("/login")
	public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session,
			RedirectAttributes redirectAttributes) {
		User user = userService.findByUsername(username.trim());
		if (user != null && userService.authenticate(username, password)) {
			session.setAttribute("currentUser", user);
			session.setAttribute("currentUserName", username.trim());
			if ("admin".equals(user.getRole())) {
				return "redirect:/admin/dashboard";
			}
			return "redirect:/home";
		} else {
			redirectAttributes.addFlashAttribute("error", "Sai thông tin đăng nhập!");
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		session.invalidate();
		redirectAttributes.addFlashAttribute("message", "Bạn đã đăng xuất thành công!");
		return "redirect:/login";
	}
}
