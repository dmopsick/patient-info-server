package com.ccd.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	@Size(max = 35)
	private String givenName;
	@NotNull
	@Size(max = 35)
	private String familyName;
	// @NotNull
	private LocalDate birthDate;
	@NotNull
    private String address;
	@NotNull
	@Size(min = 9, max = 9)
	private String diagnosis;
	@NotNull
	@Size(min = 12, max = 12)
	private String phoneNumber;
	@NotNull
	private String insuranceProvider;
	@NotNull
	@Size(min = 9, max = 9)
	private String insuranceId;
//	@ManyToOne
//	private User user;
	
	private Patient(){};
	
	public Patient(String givenName, String familyName, String birthDateString, String address, String diagnosis, String phoneNumber, String insuranceProvider, String insuranceId){
		this.givenName = givenName;
		this.familyName = familyName;
		this.birthDate = parseBirthDate(birthDateString);
		this.address = address;
		this.diagnosis = diagnosis;
		this.phoneNumber = phoneNumber;
		this.insuranceProvider = insuranceProvider;
		this.insuranceId = insuranceId;
	}
	
	private static final Logger logger = Logger.getLogger(Patient.class);
	/** Take in a string of the birth date in mm/dd/yyyy format and 
	 * convert it into a joda.LocalDate object */
	public LocalDate parseBirthDate(String birthDate){
		// Locate the index of the slashes
		int slashIndex1 = birthDate.indexOf("/");
		int slashIndex2 = birthDate.indexOf("/", slashIndex1 + 1);
		
		// Create substrings of month, day, and year
		String monthString = birthDate.substring(0, slashIndex1);
		String dayString = birthDate.substring(slashIndex1 + 1, slashIndex2);
		String yearString = birthDate.substring(slashIndex2 + 1);		
		
		// Parse month, day, and year as integers
		int month = Integer.parseInt(monthString);
		int day = Integer.parseInt(dayString);
		int year = Integer.parseInt(yearString);
		
		// Create a joda.LocalDate Object
		LocalDate birthdateObject = new LocalDate(year, month, day);
		// logger.info("FLAG: " + birthdateObject.toString());
		return birthdateObject;
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
	
	@JsonIgnore
	public LocalDate getBirthDate(){
	    return birthDate;
	}
	
	
	/** Display birthday in mm/dd/yyyy format */
	public String getSimpleBirthdate(){
	    return birthDate.getMonthOfYear() + "/" + birthDate.getDayOfMonth() + "/"
	    		+ birthDate.getYear();
	}
	
	
	/** Return the age of the patient */
	public int getAge(){
	    LocalDate now = new LocalDate();
	    Years age = Years.yearsBetween(birthDate, now);
	    return age.getYears();
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
	
//	public User getUser(){
//		return user;
//	}
	
	/** Setter method for the given name variable */
	public void setGivenName(String givenName){
		this.givenName = givenName;
	}
	
	/** Setter method for family name variable */
	public void setFamilyName(String familyName){
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