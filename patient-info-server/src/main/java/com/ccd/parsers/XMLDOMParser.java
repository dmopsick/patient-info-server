package com.ccd.parsers;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ccd.models.Patient;

@Component
public class XMLDOMParser {
	public XMLDOMParser(){}
	
	/** This method initializes the XML parser and takes in the raw data before feeding it into the appropriate methods */
	public Patient[] parsePatientXML(String fileName){
		// Builds the object that creates instances of the document builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			// Creates an instance of the document builder
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// Builds a document with the filename passed in the method call
			Document doc = builder.parse(fileName);
			
			// Builds a list of the patient elements, each one containing the attributes of each patient
			NodeList patientNodeList = doc.getElementsByTagName("patient");	
			
			// Parse the patient list. Pass the node list onto the parsePatientList method
			Patient[] patientList = parsePatientList(patientNodeList);
		
			return patientList;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// If the XML file fails to be parsed for some reason it will return null
		return null;
	}
	
	/**This method parses the patient list from XML*/
	private Patient[] parsePatientList(NodeList patientNodeList){
		
		// Create a patient list that will hold the output of patients
		Patient[] patientList = new Patient[patientNodeList.getLength()];
		// System.out.println(patientModelList.length);
		
		for(int i = 0; i < patientNodeList.getLength(); i ++){
			Node p = patientNodeList.item(i);
			
			// Check that the patient is a valid element
			if(p.getNodeType() == Node.ELEMENT_NODE){
				// Cast the node as an XML element
				Element patient = (Element) p;

				// Parse the patient 
				Patient patientModel = parsePatient(patient);
				
				// Add it the patient list
				patientList[i] = patientModel;
			}
		}
		return patientList;
	}
	
	/** This method parses a single patient from the patient list*/
	private Patient parsePatient(Element patient){
		// Parse the id of the patient
		String idString = patient.getAttribute("id");
		Long id = Long.parseLong(idString);
		//System.out.println("Flag " + id);
		
		// Parse the given name of the patient
		String givenName = patient.getElementsByTagName("givenName").item(0).getTextContent();
		
		// Parse the family name of the patient
		String familyName = patient.getElementsByTagName("familyName").item(0).getTextContent();
		
		// Parse the diagnosis code of the patient 
		String diagnosis = patient.getElementsByTagName("diagnosis").item(0).getTextContent();
		
		// Parse the phone number of the patient
		String phoneNumber = patient.getElementsByTagName("phonenumber").item(0).getTextContent();
		
		// Format the phone number
		phoneNumber = phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
		// System.out.println(phoneNumber);
		
		// Parse the insuranceProvder of the patient
		String insuranceProvider = patient.getElementsByTagName("insuranceProvider").item(0).getTextContent();
		
		// Parse the insurance id
		String insuranceId = patient.getElementsByTagName("insuranceId").item(0).getTextContent();
		
		// Create a patient model
		Patient patientModel = new Patient(id, givenName, familyName, diagnosis, phoneNumber, insuranceProvider, insuranceId);
		
		return patientModel;
	}
}
