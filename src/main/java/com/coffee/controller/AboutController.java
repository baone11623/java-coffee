package com.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffee.model.About;
import com.coffee.model.Menu;
import com.coffee.service.AboutService;
import com.coffee.service.MenuService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AboutController {
	@Autowired
	public MenuService menuService;

	@Autowired
	public AboutService aboutService;

	@GetMapping("/about")
	public String about(Model model,HttpSession session) {
		List<Menu> list = menuService.getList();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			list.removeIf(menu -> "/login".equals(menu.getLink()));
		}
		model.addAttribute("listMenu", list);
		model.addAttribute("active","about");
		model.addAttribute("username", username);
		
		About about = aboutService.getAboutInfo();
	    model.addAttribute("about", about);
		return "about";
	}
}
