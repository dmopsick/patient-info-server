package com.ccd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccd.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}