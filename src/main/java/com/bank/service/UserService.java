package com.bank.service;

import com.bank.entities.User;

import java.util.Optional;

import com.bank.dto.UserDTO;

public interface UserService {
	User addUser(User user);
	
	UserDTO findUserById(Long id);
	
	UserDTO depositMoney(Long id, Double amount);
	
	UserDTO withdrawMoney(Long id, Double amount);

	Optional<User> findByEmail(String email);
	
	User findByEmail(User user);
}
