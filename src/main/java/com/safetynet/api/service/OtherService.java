package com.safetynet.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.PersonDto;
import com.safetynet.api.entity.Email;
import com.safetynet.api.entity.Medication;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.EmailRepository;
import com.safetynet.api.repository.MedicationRepository;
import com.safetynet.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class OtherService {
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    public Iterable<Email> getAllEmail() {
	Set<Email> allEmails = new HashSet<Email>();
	for (Email email : emailRepository.findAll()) {
	    allEmails.add(email);
	}
	return allEmails;
    }

    public Iterable<PersonDto> getEmailPersons(String emailString) {
	Iterable<Person> persons = emailRepository.findByEmailString(emailString).get().getPersons();
	Set<PersonDto> personsDto = new HashSet<PersonDto>();
	for (Person person : persons) {
	    personsDto.add(new PersonDto(person));
	}
	return personsDto;
    }

    public Email addEmail(String emailString) {
	return emailRepository.save(new Email(emailString));
    }

    public Iterable<Medication> getAllMedications() {
	Set<Medication> allMedications = new HashSet<Medication>();
	for (Medication medication : medicationRepository.findAll()) {
	    allMedications.add(medication);
	}
	return allMedications;
    }

}
