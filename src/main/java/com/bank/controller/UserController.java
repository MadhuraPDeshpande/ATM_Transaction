package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.UserDTO;
import com.bank.entities.User;
import com.bank.service.UserService;

import java.util.Map;

import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
    private ModelMapper mapper;
	
	//Add User
	@PostMapping
	public ResponseEntity<User> addAccount(@RequestBody User user){
		return new ResponseEntity<>(userService.addUser(user),HttpStatus.CREATED);
	}
	
	//Find User By Id
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getAccountById(@PathVariable Long id){
		UserDTO userDTO = userService.findUserById(id);
		return ResponseEntity.ok(userDTO);
	}
	
	//Deposit
	@PutMapping("/{id}/deposit")
	public ResponseEntity<UserDTO> deposit(@PathVariable Long id,@RequestBody Map<String,Double> request){
		Double amount = request.get("amount");
		UserDTO userDTO = userService.depositMoney(id, amount);
		return ResponseEntity.ok(userDTO);
	}
	
	//Withdraw
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<UserDTO> withdraw(@PathVariable Long id,@RequestBody Map<String,Double> request){
		Double amount = request.get("amount");
		UserDTO userDTO = userService.withdrawMoney(id, amount);
		return ResponseEntity.ok(userDTO);
	}
}
