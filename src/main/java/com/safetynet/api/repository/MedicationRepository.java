package com.safetynet.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.entity.Medication;

public interface MedicationRepository extends CrudRepository<Medication, Integer> {
    
    Optional<Medication> findByMedicationString(String medicationString);
}
