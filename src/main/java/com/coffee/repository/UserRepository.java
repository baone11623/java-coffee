package com.coffee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffee.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

