package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.custom_exceptions.ResourceNotFoundException;
import com.bank.dto.CustomResponse;
import com.bank.dto.LogIn;
import com.bank.dto.UserDTO;
import com.bank.entities.User;
import com.bank.security.JwtUtils;
import com.bank.service.UserService;

import java.util.Map;

import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
    private ModelMapper mapper;
	
	@Autowired
	private AuthenticationManager mgr;
	
	@Autowired
	private JwtUtils utils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Register
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user){
		
		//check if user is present or not
		boolean userPresent = userService.findByEmail(user.getEmail()).isPresent();
		if(userPresent) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new CustomResponse<>(true,"Email already in use! Please login.", null ));
		}
		
		else {
			//return new ResponseEntity<>(userService.addUser(user),HttpStatus.CREATED);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.addUser(user);
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(new CustomResponse<>(false, "User added successfully.", null));
		}
	}
	
	//login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LogIn user){
		System.out.println("Inside user login --");
		try {
			Authentication verifiedAuth = mgr
					.authenticate(new UsernamePasswordAuthenticationToken
							(user.getEmail(), user.getPassword()));
			
			User userFound = userService.findByEmail(user.getEmail())
					.orElseThrow(() -> new ResourceNotFoundException("No Email Found!"));
			
			SigninResponse resp = new SigninResponse(utils.generateJwtToken(verifiedAuth), userFound.getId()
					,userFound.getEmail(), userFound.getName(),userFound.getRole()
					);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new CustomResponse<>(false,"Login Successful",resp));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new CustomResponse<>(true, e.getMessage(), null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new CustomResponse<>(true, e.getMessage(), null));
		}
	}
	
	//Find User By Id
	@GetMapping("/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable Long id){
		UserDTO userDTO = userService.findUserById(id);
		//return ResponseEntity.ok(userDTO);
		return ResponseEntity.status(HttpStatus.OK)
		.body(new CustomResponse<>(false, "Sucess!", userDTO));
	}
	
	//Deposit
	@PutMapping("/{id}/deposit")
	public ResponseEntity<?> deposit(@PathVariable Long id,@RequestBody Map<String,Double> request){
		Double amount = request.get("amount");
		UserDTO userDTO = userService.depositMoney(id, amount);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomResponse<>(false, "Deposit Sucess!", userDTO));
	}
	
	//Withdraw
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<?> withdraw(@PathVariable Long id,@RequestBody Map<String,Double> request){
		Double amount = request.get("amount");
		UserDTO userDTO = userService.withdrawMoney(id, amount);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new CustomResponse<>(false, "Deposit Sucess!", userDTO));
	}
	
	
}
