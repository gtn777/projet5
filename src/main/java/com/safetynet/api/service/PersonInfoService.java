package com.safetynet.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.PersonInfoDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class PersonInfoService {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    
    public Iterable<PersonInfoDto> getPersonInfo(String firstName, String lastName) {
	Set<PersonInfoDto> dtos = new HashSet<PersonInfoDto>();
	for(Person p: personRepository.findAllByFirstNameAndLastName(firstName, lastName)) {
	    dtos.add(new PersonInfoDto(p, medicalRecordRepository.findByPersonFirstNameAndPersonLastName(firstName, lastName)));
	}
	return dtos;
	
    }
    
}
