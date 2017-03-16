package com.ccd.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String givenName;
	private String familyName;
    private String address;
	private String diagnosis;
	private String phoneNumber;
	private String insuranceProvider;
	private String insuranceId;
	
	private Patient(){};
	
	public Patient(String givenName, String familyName, String address, String diagnosis, String phoneNumber, String insuranceProvider, String insuranceId){
		this.givenName = givenName;
		this.familyName = familyName;
		this.address = address;
		this.diagnosis = diagnosis;
		this.phoneNumber = phoneNumber;
		this.insuranceProvider = insuranceProvider;
		this.insuranceId = insuranceId;
	}
	
	// Getter and setter methods to access and modify variables
	public long getId(){
		return id;
	}
	
	public String getGivenName(){
		return givenName;
	}
	
	public String getFamilyName(){
        return familyName;
    }
	
	// Using @JsonIgnore to prevent the full name field from being sent in the JSON Object
	@JsonIgnore
	/** Combines the given and family name to return the full name */
	public String getFullName(){
        return givenName + " " + familyName;
    }
	
	public String getAddress(){
	    return address;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public String getDiagnosis(){
		return diagnosis;
	}
	
	public String getInsuranceProvider(){
		return insuranceProvider;
	}
	
	public String getInsuranceId(){
		return insuranceId;
	}
	
	/** Setter method for the given name variable */
	public void setGivenName(String givenName){
		this.givenName = givenName;
	}
	
	/** Setter method for family name variable */
	public void setFamilyName(String familyName){
	    this.familyName = familyName;
	}
	
	/** Setter method for the family name variable */
	public void setLastName(String familyName){
	    this.familyName = familyName;
	}
	
	public void setAddress(String address){
	    this.address = address;
	}
	
	/** Setter method for the phone number variable */
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	/** Setter method for the diagnosis variable */
	public void setDiagnosis(String diagnosis){
		this.diagnosis = diagnosis;
	}
	
	/** Setter method for the insurance provider variable */
	public void setInsuranceProvider(String insuranceProvider){
		this.insuranceProvider = insuranceProvider;
	}
	
	/** Setter method for the insurance id variable */
	public void setInsuranceId(String insuranceId){
		this.insuranceId = insuranceId;
	}
}