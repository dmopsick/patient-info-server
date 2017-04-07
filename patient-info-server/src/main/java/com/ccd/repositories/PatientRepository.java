package com.ccd.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccd.models.Patient;

/** This repository will store the data for individual patients*/
public interface PatientRepository extends JpaRepository<Patient, Long> {
	// Creates a custom query to display all patients with a specified family name
	List<Patient> findByFamilyName(String familyName);
}