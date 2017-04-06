package com.ccd.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** This class will be used to encode the password of users for security purposes */
public class MyPasswordEncoder extends BCryptPasswordEncoder {
	public MyPasswordEncoder(){	}
	
	/** Takes in a raw password and encodes it */
	public String encode(String rawPassword){
		return super.encode(rawPassword);
	}
	
	/** Checks to see if the entered password matches the stored encoded password */
	public Boolean checkmatch(String enteredPassword, String storedPassword){
		return super.matches(enteredPassword, storedPassword);
	}
}
