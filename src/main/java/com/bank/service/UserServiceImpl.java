package com.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.custom_exceptions.InSuffBalanceException;
import com.bank.custom_exceptions.ResourceNotFoundException;
import com.bank.dao.UserDao;
import com.bank.dto.UserDTO;
import com.bank.entities.User;
import com.bank.enums.UserRole;

import org.modelmapper.ModelMapper;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public User addUser(User user) {
		user.setRole(UserRole.ROLE_USER);
		return userDao.save(user);
	}

	@Override
	public UserDTO findUserById(Long id) {
		User user = userDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("Account does not exist"));
		
		UserDTO userDTO = mapper.map(user,UserDTO.class); // Map the User object to UserDTO
		//mapper.map(source, which class's object to convert into)
		return userDTO;
	}

	@Override
	public UserDTO depositMoney(Long id, Double amount) {
		User user = userDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("Account does not exist"));
		
		Double totalAmt = user.getBalance() + amount;  
		user.setBalance(totalAmt);

		User savedAccount = userDao.save(user);
		//save method returns saved account object
				    
		UserDTO userDTO = mapper.map(savedAccount, UserDTO.class);
				    
		return userDTO;

	}

	@Override
	public UserDTO withdrawMoney(Long id, Double amount) {
		User user = userDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("Account does not exist"));
		
		if(user.getBalance() < amount) {
			throw new InSuffBalanceException("Insufficient Balance");
		}
		
		Double totalAmt = user.getBalance() - amount;
		user.setBalance(totalAmt);
		
		User savedAccount = userDao.save(user);
		
		UserDTO userDTO = mapper.map(savedAccount, UserDTO.class);
		
		return userDTO;
	}
	
}
