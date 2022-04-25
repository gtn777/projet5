package com.safetynet.api.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.PersonDto;
import com.safetynet.api.entity.Email;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.EmailRepository;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.repository.PhoneRepository;

import lombok.Data;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private EmailRepository emailRepository;

    public Iterable<PersonDto> getAll() {
	Iterable<Person> persons = personRepository.findAll();
	Set<PersonDto> personsDto = new HashSet<PersonDto>();
	for (Person person : persons) {
	    personsDto.add(new PersonDto(person));
	}
	return personsDto;
    }

    public PersonDto createPerson(PersonDto dto) {
	// Si la personne est déja présente en base on retourne NULL
	if (personRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isPresent()) {
	    return null;
	}
	// on crée une npersonne et lui attribue les nouveaux nom et prénom
	Person newPerson = new Person();
	newPerson.setFirstName(dto.getFirstName());
	newPerson.setLastName(dto.getLastName());

	// on check si le phone est connu, si oui on l'attribue a person, ou alors on
	// crée et enregistre un nouveau numéero
	Optional<Phone> phoneOptional = phoneRepository.findByPhoneNumber(dto.getPhone());
	if (phoneOptional.isPresent()) {
	    newPerson.setPhone(phoneOptional.get());
	} else {
	    newPerson.setPhone(phoneRepository.save(new Phone(dto.getPhone())));
	}

	Optional<Email> emailOptional = emailRepository.findByEmailString(dto.getEmail());
	if (emailOptional.isPresent()) {
	    newPerson.setEmail(emailOptional.get());
	} else {
	    newPerson.setEmail(emailRepository.save(new Email(dto.getEmail())));
	}

	Optional<Home> homeOptional = homeRepository.findByAddressAndCity(dto.getAddress(), dto.getCity());
	if (homeOptional.isPresent()) {
	    newPerson.setHome(homeOptional.get());
	} else {
	    Home newHome = new Home(dto.getAddress(), dto.getCity(), dto.getZip());
	    newPerson.setHome(homeRepository.save(newHome));
	}
	return new PersonDto(personRepository.save(newPerson));
    }

    @Transactional
    public PersonDto updatePerson(PersonDto dto) {

	Person person = personRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).get();

	if (dto.getEmail() != person.getEmail().getEmailString()) {
	    Optional<Email> emailOptional = emailRepository.findByEmailString(dto.getEmail());
	    if (emailOptional.isPresent()) {
		person.setEmail(emailOptional.get());
	    } else {
//		Optional<Email> oldMail = emailRepository.findByEmailString(person.getEmailString());
		person.setEmail(new Email(dto.getEmail()));
	    }
	}
	if (dto.getPhone() != person.getPhone().getPhoneNumber()) {
	    Optional<Phone> phoneOptional = phoneRepository.findByPhoneNumber(dto.getPhone());
	    if (phoneOptional.isPresent()) {
		person.setPhone(phoneOptional.get());
	    } else {
		person.setPhone(new Phone(dto.getPhone()));
	    }
	}
	
	return new PersonDto(personRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).get());

    }

    @Transactional
    public void deletePerson(String firstName, String lastName) {
	personRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }

    public Iterable<PersonDto> createAllPerson(Iterable<PersonDto> dto){
	Set<PersonDto> allPersonSaved = new HashSet<PersonDto>();
	for(PersonDto personDto: dto) {
	    PersonDto personSaved = this.createPerson(personDto);
	    allPersonSaved.add(personSaved);
	}
	return this.getAll();
    }
}
