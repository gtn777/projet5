
package com.safetynet.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.PersonInfoDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownPersonException;


@Service
public class PersonInfoService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Iterable<PersonInfoDto> getPersonInfo(String firstName, String lastName) {
	Set<PersonInfoDto> dtos = new HashSet<PersonInfoDto>();
	Iterable<Person> persons = personRepository
		.findAllByFirstNameAndLastName(firstName, lastName);
	if (persons != null && persons.iterator().hasNext()) {
	    for (Person person : persons) {
		dtos.add(new PersonInfoDto(person, medicalRecordRepository
			.findByPersonFirstNameAndPersonLastName(firstName, lastName)));
	    }
	    return dtos;
	} else {
	    throw new UnknownPersonException(firstName, lastName);
	}
    }
}
