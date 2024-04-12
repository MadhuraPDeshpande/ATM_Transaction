package com.bank.controller;

import com.bank.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponse {
	private String jwt;
	private Long id;
	private String email;
	private String name;
	private UserRole role;
}