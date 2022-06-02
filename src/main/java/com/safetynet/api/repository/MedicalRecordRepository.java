
package com.safetynet.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.entity.MedicalRecord;


public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Integer> {

    Optional<MedicalRecord> findByPersonFirstNameAndPersonLastName(String firstName, String lastName);

    MedicalRecord deleteByPersonFirstNameAndPersonLastName(String firstName, String lastName);

}
