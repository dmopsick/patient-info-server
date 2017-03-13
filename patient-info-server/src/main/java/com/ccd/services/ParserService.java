package com.ccd.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ccd.models.Patient;
import com.ccd.parsers.XMLDOMParser;

@Service
public class ParserService {
    private static final Logger logger = Logger.getLogger(ParserService.class);
    private final XMLDOMParser parser;
    
    @Autowired
    public ParserService(XMLDOMParser parser){
        this.parser = parser;
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {}
    
    /** This method takes in a file name and calls the XMLDOMParser
     * to parse and return the patient(s) in a given file */
    public Patient[] parsePatientFromFile(String filename){
        return this.parser.parsePatientXML(filename);
    }
}
