package com.ccd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccd.models.Patient;

/** This repository will store the data for individual patients*/
public interface PatientRepository extends JpaRepository<Patient, Long> {

}