package com.ccd.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ccd.models.Patient;
import com.ccd.repositories.PatientRepository;
import com.ccd.validators.PatientValidator;

@Service
public class PatientService {
    private static final Logger logger = Logger.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final ParserService parserService;
    
    /** This is the constructor to be used by the application */
    @Autowired
    public PatientService(PatientRepository patientRepository, ParserService parserService) {
        this.patientRepository = patientRepository;
        this.parserService = parserService;
        addPatientFromFile("PatientList1.xml");
    }
    
    /** This constructor is for testing services to avoid calling addPatientFromFile
     * I do not want to add items in the database when testing the application */
    public PatientService(PatientRepository patientRepository, ParserService parserService, Boolean live) {
        this.patientRepository = patientRepository;
        this.parserService = parserService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new PatientValidator());
    }

    /** Adds a list of patients into the repository from a XML file 
     * passed into the method via filename*/
    public void addPatientFromFile(String fileName) {
        // Save the returned list of patients parsed from XML
        Patient[] patientArray = this.parserService.parsePatientFromFile(fileName);

        // Add the parsed patients into the array
        addMultiple(patientArray);
    }

    /** Save multiple patients at once */
    public Patient[] addMultiple(Patient[] patientArray){
        // Create an array to hold saved patients
        Patient[] savedArray = new Patient[patientArray.length];
        
        // Save patients
        for (int i = 0; i < patientArray.length; i++) {
            savedArray[i] = add(patientArray[i]);
        }
        
        return savedArray;
    }
    
    /** Adds a patient into the repository */
    public Patient add(Patient bodyPatient) {
        Patient savedPatient = patientRepository.save(bodyPatient);
        //logger.info("Flag - saved patient's name " + savedPatient.getFullName());
        return savedPatient;
    }

    /** Returns a specific patient */
    public Patient read(Long patientId) {
        return this.patientRepository.findOne(patientId);
    }

    /** Returns all of the patients in the repository */
    public List<Patient> readAll() {
        // logger.info("FLAG - readAll() was called");
        return this.patientRepository.findAll();
    }
    
    /** Deletes the patient with the given id from the repository */
    public void delete(Long patientId){
        this.patientRepository.delete(patientId);
    }
    
    /** Updates a given patient's attributes with all of the attributes of a 
     * newly uploaded patient object in order to keep the patient at
     * the same id in the database */
    public Patient edit(Patient originalPatient, Patient updatedPatient){
        originalPatient.setGivenName(updatedPatient.getGivenName());
        originalPatient.setFamilyName(updatedPatient.getFamilyName());
        originalPatient.setAddress(updatedPatient.getAddress());
        originalPatient.setDiagnosis(updatedPatient.getDiagnosis());
        originalPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
        originalPatient.setInsuranceProvider(updatedPatient.getInsuranceProvider());
        originalPatient.setInsuranceId(updatedPatient.getInsuranceId());
        
        return this.patientRepository.save(originalPatient);
    }
}