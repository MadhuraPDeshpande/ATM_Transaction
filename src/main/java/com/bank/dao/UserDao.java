package com.bank.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entities.User;
import com.bank.enums.UserRole;

public interface UserDao extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String username);
	
	User findByEmailAndRole(String email, UserRole roleUser);
	
}
