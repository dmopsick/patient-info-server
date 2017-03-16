package com.ccd.services;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ccd.models.Patient;
import com.ccd.parsers.XMLDOMParser;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ParserServiceTests {
    private static final Logger logger = Logger.getLogger(ParserServiceTests.class);
    
    @Mock
    private XMLDOMParser parser;
    
    private ParserService parserService;
    
    @Before
    public void setup() throws Exception {
        // MockitoAnnotations.initMocks(this);
        parserService = new ParserService(parser);
    }
    
    /** Tests that parsePatientFromFile calls the parsePatientXML method in
     * the XMLDOMParser class*/
    @Test
    public void parsePatientFromFileCallsXMLDOMParserParsePatientXML() throws Exception{
        Mockito.when(this.parser.parsePatientXML("SampleFile.xml")).thenReturn(null);
        
        parserService.parsePatientFromFile("SampleFile.xml");
        
        Mockito.verify(this.parser, Mockito.times(1)).parsePatientXML("SampleFile.xml");
    }
    
    /** Tests that parsePatientFromFile returns the intended value */
    @Test
    public void parsePatientFromFileReturnsExpectedPatients() throws Exception{
        Patient samplePatient1 = new Patient("Joe", "Dirt", "Address1", "ABC123DEF", "5555555555", "Provider", "DEF123ABC");
        Patient samplePatient2 = new Patient("Bobby", "Johnson", "Address2","963JKL852", "7777777777", "Aetna", "WER456YTG");
        Patient[] samplePatientArray = new Patient[2];
        samplePatientArray[0] = samplePatient1;
        samplePatientArray[1] = samplePatient2;
        
        Mockito.when(this.parser.parsePatientXML("SampleFile.xml")).thenReturn(samplePatientArray);
        
        Patient[] returnedPatientArray = parserService.parsePatientFromFile("SampleFile.xml").clone();
        
        Assert.assertEquals("Joe", returnedPatientArray[0].getGivenName());
        Assert.assertEquals("Dirt", returnedPatientArray[0].getFamilyName());
        Assert.assertEquals("ABC123DEF", returnedPatientArray[0].getDiagnosis());
        Assert.assertEquals("Bobby", returnedPatientArray[1].getGivenName());
        Assert.assertEquals("Johnson", returnedPatientArray[1].getFamilyName());
        Assert.assertEquals("963JKL852", returnedPatientArray[1].getDiagnosis());
    }
}