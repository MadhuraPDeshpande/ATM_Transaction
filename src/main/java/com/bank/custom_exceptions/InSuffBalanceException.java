package com.bank.custom_exceptions;

public class InSuffBalanceException extends RuntimeException {
	public InSuffBalanceException(String errMesg) {
		super(errMesg);
	}
}