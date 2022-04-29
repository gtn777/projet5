package com.safetynet.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.entity.MedicalRecord;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Integer> {

    MedicalRecord findByPersonFirstNameAndPersonLastName(String firstName, String lastName);


}
