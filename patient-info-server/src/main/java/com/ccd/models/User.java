package com.ccd.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;

import com.ccd.security.MyPasswordEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String username;
	@NotNull
	@JsonIgnore
	private String password;
	
//	@OneToMany(mappedBy = "user")
//	private List<Patient> patientList;
	
	@Transient
	@Autowired
	private static final MyPasswordEncoder passwordEncoder = new MyPasswordEncoder();
	
	public User(String username, String password){
		this.username = username;
		// Encode the password, bad to store raw password
		// Maybe use validation to ensure that this is not empty
		// What is password sent to if an empty one is passed? Not good
		if(!password.equals("")){
			this.password = encode(password);
		}
	}
	
	public Long getId(){
		return id;
	}
	
	public String getUsername(){
		return username;
	}
	
	/** I feel like I shouldn't have a method providing 
	 * access to this. Seems insecure but ok */
	@JsonIgnore
	public String getPassword(){
		return password;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	// I feel like I shouldn't have the password setter as public but again I'm not sure
	public void setPassword(String password){
		this.password = password;
	}
	
//	public List<Patient> getPatientList(){
//		return patientList;
//	}
	
	/** Calls MyPassWordEncoder to encode the raw password passed by the user */
	public String encode(String rawPassword){
		return passwordEncoder.encode(rawPassword);
	}
	
	/** Compare the entered password with the stored password */
	public Boolean matchPassword(String enteredPassword){
		return passwordEncoder.checkmatch(enteredPassword, this.password);
	}
	
}
