package com.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffee.model.Menu;
import com.coffee.service.MenuService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MenuController {
	@Autowired
	public MenuService menuService;

	@GetMapping("/home")

	public String home(Model model, HttpSession session) {
		List<Menu> list = menuService.getList();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			list.removeIf(menu -> "/login".equals(menu.getLink()));
		}
		model.addAttribute("listMenu", list);
		model.addAttribute("active", "home");
		model.addAttribute("username", username);
		return "index";
	}
}
