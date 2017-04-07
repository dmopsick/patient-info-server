package com.ccd.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ccd.models.Patient;
import com.ccd.services.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PatientControllerTests {
	private static final Logger logger = Logger.getLogger(PatientControllerTests.class);
	private MockMvc mockMvc;
    private MediaType jsonContent = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
    private ObjectMapper jsonMapper;

    @Mock
    private PatientService patientService;
    
    // @InjectMocks
    private PatientController patientController;
    
    @Before
    public void setup() throws Exception {
        // MockitoAnnotations.initMocks(this);

        this.patientController = new PatientController(patientService);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        this.jsonMapper  = new ObjectMapper();
    }
    
    /** This tests that add calls the add method in the patient service */
    @Test
    public void addCallsPatientServiceAdd() throws Exception{
        String jsonPatient = jsonMapper.writeValueAsString(new Patient("Joe", "Dirt", "1/2/1955", "Address1", "ABC123DEF", "5555555555", 
                "Provider", "DEF123ABC"));
        
        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
        List<Patient> capturedPatients;
        
        Mockito.when(this.patientService.add(Mockito.isA(Patient.class))).thenReturn(null);
        
        MockHttpServletRequestBuilder postPatient  = post("/patient/")
                .content(jsonPatient)
                .contentType(jsonContent);
        
        mockMvc.perform(postPatient);
        
        Mockito.verify(this.patientService, Mockito.times(1)).add(patientCaptor.capture());
        capturedPatients = patientCaptor.getAllValues();
        Assert.assertEquals(capturedPatients.get(0).getGivenName(), "Joe");
        Assert.assertEquals(capturedPatients.get(0).getFamilyName(), "Dirt");
        Assert.assertEquals(capturedPatients.get(0).getDiagnosis(), "ABC123DEF");
        Assert.assertEquals(capturedPatients.get(0).getPhoneNumber(), "5555555555");
        Assert.assertEquals(capturedPatients.get(0).getInsuranceProvider(), "Provider");
        Assert.assertEquals(capturedPatients.get(0).getInsuranceId(), "DEF123ABC");
    }
    
    @Test
    public void addReturnsIntendedPatient() throws Exception{
        String jsonPatient = jsonMapper.writeValueAsString(new Patient("Joe", "Dirt", "1/2/1955", "Address", "ABC123DEF", "5555555555", 
                "Provider", "DEF123ABC"));
        Patient samplePatient2 = new Patient("Bobby", "Johnson", "4/28/1995", "Address2","963JKL852", "7777777777", "Aetna", "WER456YTG");
        
        Mockito.when(this.patientService.add(Mockito.isA(Patient.class))).thenReturn(samplePatient2);
        
        MockHttpServletRequestBuilder postPatient  = post("/patient/")
                .content(jsonPatient)
                .contentType(jsonContent);
        
        mockMvc.perform(postPatient)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.givenName", is("Bobby")))
            .andExpect(jsonPath("$.familyName", is("Johnson")))
            .andExpect(jsonPath("$.diagnosis", is("963JKL852")));
        
    }
    
    @Test
    public void addPatientFileCallsPatientServiceAddPatientFromFile() throws Exception{
    	Mockito.when(this.patientService.addPatientFromFile("test.xml")).thenReturn(new Patient[1]);
    	
    	MockHttpServletRequestBuilder postPatient  = post("/patient/test");
    	
    	mockMvc.perform(postPatient)
    			.andExpect(status().isCreated());
    	
    	Mockito.verify(this.patientService, Mockito.times(1)).addPatientFromFile("test.xml");
    }
    
    @Test
    public void addPatientFileHandlesBadFileName() throws Exception{
    	MockHttpServletRequestBuilder postPatient  = post("/patient/test");
    	
    	mockMvc.perform(postPatient)
    			.andExpect(status().isNotFound());
    }
    
    @Test
    public void getCallsPatientServiceRead() throws Exception {
    	Mockito.when(this.patientService.read(1L)).thenReturn(null);
        
    	MockHttpServletRequestBuilder getPatient  = get("/patient/1");
    	
    	mockMvc.perform(getPatient);
    	
    	Mockito.verify(this.patientService, Mockito.times(1)).read(1l);
    }
    
    @Test
    public void getReturnsIntendedPatient() throws Exception {
        Patient samplePatient = new Patient("Joe", "Dirt", "1/2/1955", "Address1", "ABC123DEF", "5555555555", "Provider", "DEF123ABC");
    	
    	Mockito.when(this.patientService.read(1L)).thenReturn(samplePatient);
        
    	MockHttpServletRequestBuilder getPatient  = get("/patient/1");
        
        mockMvc.perform(getPatient)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.givenName", is("Joe")))
                .andExpect(jsonPath("$.familyName", is("Dirt")))
                .andExpect(jsonPath("$.address", is("Address1")))
                .andExpect(jsonPath("$.diagnosis", is("ABC123DEF")))
                .andExpect(jsonPath("$.phoneNumber", is("5555555555")))
                .andExpect(jsonPath("$.insuranceProvider", is("Provider")))
                .andExpect(jsonPath("$.insuranceId", is("DEF123ABC")));
    }
    
    @Test
    public void getHandlesBadPatientId() throws Exception {
    	Mockito.when(this.patientService.read(1L)).thenReturn(null);
        
    	MockHttpServletRequestBuilder getPatient  = get("/patient/1");
    	
        mockMvc.perform(getPatient)
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void getAllCallsPatientServiceReadAll() throws Exception{
    	Mockito.when(this.patientService.readAll()).thenReturn(null);
    	
    	MockHttpServletRequestBuilder getPatient = get("/patient/");
    	
    	mockMvc.perform(getPatient)
    			.andExpect(status().isNotFound());
    	
    	Mockito.verify(this.patientService, Mockito.times(1)).readAll();
    }
    
    @Test
    public void getAllReturnsIntendedPatientList() throws Exception{
    	// Create sample patients to populate a sample patient list to ensure proper input is being returned
        Patient samplePatient1 = new Patient("Joe", "Dirt", "1/2/1955", "Address1","ABC123DEF", "5555555555", "Provider", "DEF123ABC");
        Patient samplePatient2 = new Patient("Bobby", "Johnson", "4/28/1995", "Address2", "963JKL852", "7777777777", "Aetna", "WER456YTG");
    	Patient samplePatient3 = new Patient("Aubrey", "Graham", "5/20/1988", "Address3", "UIO789PAS", "9876543210", "Humana", "JOK852SNK");
    	List<Patient> samplePatientList = new ArrayList<Patient>();
    	samplePatientList.add(samplePatient1);
    	samplePatientList.add(samplePatient2);
    	samplePatientList.add(samplePatient3);
    	
    	Mockito.when(this.patientService.readAll()).thenReturn(samplePatientList);
    	
    	MockHttpServletRequestBuilder getPatient = get("/patient/");
    	
    	mockMvc.perform(getPatient)
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$[0].givenName", is("Joe")))
    			.andExpect(jsonPath("$[0].familyName", is("Dirt")))
    			.andExpect(jsonPath("$[1].givenName", is("Bobby")))
    			.andExpect(jsonPath("$[1].familyName", is("Johnson")))
    			.andExpect(jsonPath("$[2].givenName", is("Aubrey")))
    			.andExpect(jsonPath("$[2].familyName", is("Graham")));
    }
    
    
    @Test
    public void deleteCallsPatientServiceRead() throws Exception{
        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        List<Long> capturedLongs;
        Long testLong = 1L;
        
        Mockito.when(this.patientService.read(1L)).thenReturn(null);
        
        MockHttpServletRequestBuilder deletePatient = delete("/patient/1");
        
        mockMvc.perform(deletePatient)
                .andExpect(status().isNotFound());
        
        Mockito.verify(this.patientService, Mockito.times(1)).read(longCaptor.capture());
        capturedLongs = longCaptor.getAllValues();
        Assert.assertEquals(testLong, capturedLongs.get(0));
    }
    
    @Test
    public void deleteCallsPatientServiceDelete() throws Exception{
        Patient samplePatient1 = new Patient("Joe", "Dirt", "1/2/1955", "Address1","ABC123DEF", "5555555555", "Provider", "DEF123ABC");
        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        List<Long> capturedLongs;
        Long testLong = 1L;
        
        Mockito.when(this.patientService.read(1L)).thenReturn(samplePatient1);
        Mockito.doNothing().when(this.patientService).delete(1L);
        
        MockHttpServletRequestBuilder deletePatient = delete("/patient/1");
        
        mockMvc.perform(deletePatient)
                .andExpect(status().isOk());
        
        Mockito.verify(this.patientService, Mockito.times(1)).delete(longCaptor.capture());
        capturedLongs = longCaptor.getAllValues();
        Assert.assertEquals(testLong, capturedLongs.get(0));
    }

    @Test
    public void putCallsPatientServiceRead() throws Exception{
        String jsonPatient = jsonMapper.writeValueAsString(new Patient("Joe", "Dirt",  "1/2/1955","Address1", "ABC123DEF", "5555555555", 
                "Provider", "DEF123ABC"));
        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        List<Long> capturedLongs;
        Long testLong = 1L;
        
        Mockito.when(this.patientService.read(1L)).thenReturn(null);
        
        MockHttpServletRequestBuilder putPatient = put("/patient/1")
                .content(jsonPatient)
                .contentType(jsonContent);
                
        mockMvc.perform(putPatient)
                .andExpect(status().isNotFound());
        
        Mockito.verify(this.patientService, Mockito.times(1)).read(longCaptor.capture());
        capturedLongs = longCaptor.getAllValues();
        Assert.assertEquals(testLong, capturedLongs.get(0));
    }
    
    @Test
    public void putCallsPatientServiceEdit() throws Exception{
        String jsonPatient = jsonMapper.writeValueAsString(new Patient("Joe", "Dirt", "1/2/1955", "Address1", "ABC123DEF", "5555555555", 
                "Provider", "DEF123ABC"));
        Patient samplePatient2 = new Patient("Bobby", "Johnson", "4/28/1995", "Address2", "963JKL852", "7777777777", "Aetna", "WER456YTG");
        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
        List<Patient> capturedPatients;
        
        
        Mockito.when(this.patientService.read(1L)).thenReturn(samplePatient2);
        Mockito.when(this.patientService.edit(Mockito.isA(Patient.class), Mockito.isA(Patient.class))).thenReturn(null);
        
        MockHttpServletRequestBuilder putPatient = put("/patient/1")
                .content(jsonPatient)
                .contentType(jsonContent);
        
        mockMvc.perform(putPatient);
        
        Mockito.verify(this.patientService, Mockito.times(1)).edit(patientCaptor.capture(), patientCaptor.capture());
        capturedPatients = patientCaptor.getAllValues();
        Assert.assertEquals("Bobby Johnson", capturedPatients.get(0).getFullName());
        Assert.assertEquals("Address2", capturedPatients.get(0).getAddress());
        Assert.assertEquals("963JKL852", capturedPatients.get(0).getDiagnosis());
        Assert.assertEquals("Joe Dirt", capturedPatients.get(1).getFullName());
        Assert.assertEquals("Address1", capturedPatients.get(1).getAddress());
        Assert.assertEquals("ABC123DEF", capturedPatients.get(1).getDiagnosis());
    }
    
    @Test
    public void putReturnsIntendedPatient() throws Exception{
        String jsonPatient = jsonMapper.writeValueAsString(new Patient("Joe", "Dirt", "1/2/1955", "Address1", "ABC123DEF", "5555555555", 
                "Provider", "DEF123ABC"));
        Patient samplePatient2 = new Patient("Bobby", "Johnson", "4/28/1995", "Address2", "963JKL852", "7777777777", "Aetna", "WER456YTG");
        Patient samplePatient3 = new Patient("Aubrey", "Graham", "5/20/1988", "Address3", "UIO789PAS", "9876543210", "Humana", "JOK852SNK");
        
        Mockito.when(this.patientService.read(1L)).thenReturn(samplePatient2);
        Mockito.when(this.patientService.edit(Mockito.isA(Patient.class), Mockito.isA(Patient.class))).thenReturn(samplePatient3);
        
        MockHttpServletRequestBuilder putPatient = put("/patient/1")
                .content(jsonPatient)
                .contentType(jsonContent);
        
        mockMvc.perform(putPatient)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.givenName", is("Aubrey")))
                .andExpect(jsonPath("$.familyName", is("Graham")))
                .andExpect(jsonPath("$.address", is("Address3")));
    }
    
}