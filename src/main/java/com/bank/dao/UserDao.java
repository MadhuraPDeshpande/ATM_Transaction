package com.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entities.User;

public interface UserDao extends JpaRepository<User, Long>{
	
}
