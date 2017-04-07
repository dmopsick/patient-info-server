package com.ccd.validators;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ccd.models.Patient;

public class PatientValidator implements Validator {
    private static final Logger logger = Logger.getLogger(PatientValidator.class);
    
    public boolean supports(Class clazz){
        return Patient.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors){
        Patient patient = (Patient) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "givenName", "givenName.empty", "A given name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "familyName", "familyName.empty", "A family name is  required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "birthDate.empty", "A birth date is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "address.empty", "An adddress is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "diagnosis", "diagnosis.empty", "A diagnosis code required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "phoneNumber.empty", "A phone number required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "insuranceProvider", "insuranceProvder.empty", "An insurance provider is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "insuranceId", "insuranceId.empty", "An insurance Id required");
    }
}
