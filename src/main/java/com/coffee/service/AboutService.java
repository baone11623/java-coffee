package com.coffee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffee.model.About;
import com.coffee.repository.AboutRepository;

@Service
public class AboutService {

    @Autowired
    private AboutRepository aboutRepository;

    public About getAboutInfo() {
        return aboutRepository.findAll().stream().findFirst().orElse(null);
    }
}