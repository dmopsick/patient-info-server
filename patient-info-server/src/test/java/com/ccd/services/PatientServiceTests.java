package com.ccd.services;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ccd.models.Patient;
import com.ccd.parsers.XMLDOMParser;
import com.ccd.repositories.PatientRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PatientServiceTests {
	private static final Logger logger = Logger.getLogger(PatientServiceTests.class);

    @Mock
    private PatientRepository patientRepository;
    
    @Mock
    private Patient patient;
    
    @Mock
    private ParserService parserService;
    
    // @InjectMocks
    private PatientService patientService;
    
    @Before
    public void setup() throws Exception {
        // MockitoAnnotations.initMocks(this);
        patientService = new PatientService(patientRepository, parserService, true);
    }
  
    /** Tests that addPattientFrom file calls the parsePatientFromFile in Parser Service
     * Used in adding sample patients into the repository when the application is launched */
    @Test
    public void addPatientFromFileCallsParserServicParsePatientFromFile() throws Exception{
        Patient samplePatient1 = new Patient(1L, "Joe", "Dirt", "ABC123DEF", "5555555555", "Provider", "DEF123ABC");
        Patient samplePatient2 = new Patient(2L, "Bobby", "Johnson", "963JKL852", "7777777777", "Aetna", "WER456YTG");
        Patient[] samplePatientArray = new Patient[2];
        samplePatientArray[0] = samplePatient1;
        samplePatientArray[1] = samplePatient2;
        
        Mockito.when(this.parserService.parsePatientFromFile("SampleList")).thenReturn(samplePatientArray);
        
        patientService.addPatientFromFile("SampleList");
        
        Mockito.verify(this.parserService, Mockito.times(1)).parsePatientFromFile("SampleList");
    }
    
    /** Tests that the add method calls the save method in the patient repository */
    @Test
    public void addCallsPatientRepositorySave() throws Exception {
    	Patient samplePatient1 = new Patient(1L, "Joe", "Dirt", "ABC123DEF", "5555555555", "Provider", "DEF123ABC");
    	Patient samplePatient2 = new Patient(2L, "Bobby", "Johnson", "963JKL852", "7777777777", "Aetna", "WER456YTG");
    	Patient[] samplePatientArray = new Patient[1];
    	samplePatientArray[0] = samplePatient2;
    	
    	
    	ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
    	List<Patient> capturedPatients;
    	
    	
    	Mockito.when(this.patientRepository.save(Mockito.isA(Patient.class))).thenReturn(samplePatient2);
    	Mockito.when(this.parserService.parsePatientFromFile("PatientList.xml")).thenReturn(samplePatientArray);
    	
    	patientService.add(samplePatient1);
    	
    	
    	Mockito.verify(this.patientRepository, Mockito.times(1)).save(patientCaptor.capture());
    	capturedPatients = patientCaptor.getAllValues();
    	Assert.assertEquals(capturedPatients.get(0).getId(), 1L);
        Assert.assertEquals(capturedPatients.get(0).getFullName(), "Joe Dirt");
        Assert.assertEquals(capturedPatients.get(0).getDiagnosis(), "ABC123DEF");
        Assert.assertEquals(capturedPatients.get(0).getPhoneNumber(), "5555555555");
        Assert.assertEquals(capturedPatients.get(0).getInsuranceProvider(), "Provider");
        Assert.assertEquals(capturedPatients.get(0).getInsuranceId(), "DEF123ABC");
    }
    
    /** Tests that the add method returns the intended patient */
    @Test
    public void addReturnsIntendedPatient() throws Exception {
        Patient samplePatient1 = new Patient(1L, "Joe", "Dirt", "ABC123DEF", "5555555555", "Provider", "DEF123ABC");
        Patient samplePatient2 = new Patient(2L, "Bobby", "Johnson", "963JKL852", "7777777777", "Aetna", "WER456YTG");
    	
    	Mockito.when(this.patientRepository.save(Mockito.isA(Patient.class))).thenReturn(samplePatient2);
    	
    	Patient returnedPatient = patientService.add(samplePatient1);
    	
    	Assert.assertEquals(returnedPatient.getId(), 2L);
        Assert.assertEquals(returnedPatient.getFullName(), "Bobby Johnson");
        Assert.assertEquals(returnedPatient.getDiagnosis(), "963JKL852");
        Assert.assertEquals(returnedPatient.getPhoneNumber(), "7777777777");
        Assert.assertEquals(returnedPatient.getInsuranceProvider(), "Aetna");
        Assert.assertEquals(returnedPatient.getInsuranceId(), "WER456YTG");
    }
    
    /** Tests that addMultiple returns the expected patients, shows they are saved correctly */
    @Test
    public void addMultipleReturnsExpectedPatients() throws Exception{
        Patient samplePatient1 = new Patient(1L, "Joe", "Dirt", "ABC123DEF", "5555555555", "Provider", "DEF123ABC");
        Patient samplePatient2 = new Patient(2L, "Bobby", "Johnson", "963JKL852", "7777777777", "Aetna", "WER456YTG");
        Patient[] samplePatientArray1 = new Patient[1];
        Patient[] samplePatientArray2= new Patient[1];
        samplePatientArray1[0] = samplePatient1;
        samplePatientArray2[0] = samplePatient2;
        
        Mockito.when(this.patientRepository.save(Mockito.isA(Patient.class))).thenReturn(samplePatient2);
        
        Patient[] returnedPatientArray = patientService.addMultiple(samplePatientArray1).clone();
        
        Assert.assertEquals("Bobby", returnedPatientArray[0].getGivenName());
        Assert.assertEquals("Johnson", returnedPatientArray[0].getFamilyName());
        Assert.assertEquals("963JKL852", returnedPatientArray[0].getDiagnosis());
    }
    
    /** Tests that read successfully calls the findOne method in the Patient Repository */
    @Test
    public void readCallsPatientRepositoryFindOne() throws Exception{
        Mockito.when(this.patientRepository.findOne(1L)).thenReturn(null);
        
        patientService.read(1L);
        
        Mockito.verify(this.patientRepository, Mockito.times(1)).findOne(1L);
    }
    
    /** Tests that read returns the intended patient */
    @Test
    public void readReturnsExpectedPatient() throws Exception{
        Patient samplePatient2 = new Patient(2L, "Bobby", "Johnson", "963JKL852", "7777777777", "Aetna", "WER456YTG");
        
        Mockito.when(this.patientRepository.findOne(1L)).thenReturn(samplePatient2);
        
        Patient returnedPatient = patientService.read(1L);
        
        Assert.assertEquals("Bobby", returnedPatient.getGivenName());
        Assert.assertEquals("Johnson", returnedPatient.getFamilyName());
        Assert.assertEquals("963JKL852", returnedPatient.getDiagnosis());
    }
    
    /** Tests that readdALl calls the findAll method in the patientRepository */
    @Test
    public void readAllCallsPatientRepositoryFindAll() throws Exception{
        Mockito.when(this.patientRepository.findAll()).thenReturn(null);
        
        patientService.readAll();
        
        Mockito.verify(this.patientRepository, Mockito.times(1)).findAll();
    }
    
    /** Tests that readAll returns the expected list of patients from the patient Repository */
    @Test
    public void readAllReturnsExpectedPatients() throws Exception{
        Patient samplePatient1 = new Patient(1L, "Joe", "Dirt", "ABC123DEF", "5555555555", "Provider", "DEF123ABC");
        Patient samplePatient2 = new Patient(2L, "Bobby", "Johnson", "963JKL852", "7777777777", "Aetna", "WER456YTG");
        List<Patient> samplePatientList = new ArrayList<Patient>();
        samplePatientList.add(samplePatient1);
        samplePatientList.add(samplePatient2);
        
        Mockito.when(this.patientRepository.findAll()).thenReturn(samplePatientList);
        
        List<Patient> returnedPatientList = patientService.readAll();
        
        Assert.assertEquals("Joe", returnedPatientList.get(0).getGivenName());
        Assert.assertEquals("Dirt", returnedPatientList.get(0).getFamilyName());
        Assert.assertEquals("ABC123DEF", returnedPatientList.get(0).getDiagnosis());
        Assert.assertEquals("Bobby", returnedPatientList.get(1).getGivenName());
        Assert.assertEquals("Johnson", returnedPatientList.get(1).getFamilyName());
        Assert.assertEquals("963JKL852", returnedPatientList.get(1).getDiagnosis());
    }
}
