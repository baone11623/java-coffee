package com.coffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffee.model.Menu;
import com.coffee.repository.MenuRepository;


@Service
public class MenuService {
	@Autowired
	public MenuRepository repository;

	public List<Menu> getList() {
		return (List<Menu>) repository.findAll();
	}

}
