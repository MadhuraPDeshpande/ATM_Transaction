package com.bank.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.custom_exceptions.ResourceNotFoundException;
import com.bank.dao.UserDao;
import com.bank.entities.User;

@Service
@jakarta.transaction.Transactional
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Username = " + username);
		User user = userDao.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("Email not found!!!!"));
		System.out.println("User info id = " + user.getId() );
		return new CustomUserDetails(user);
	}

}
