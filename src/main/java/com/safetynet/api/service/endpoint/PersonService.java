
package com.safetynet.api.service.endpoint;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.endpoints.JsonFilePersonsDto;
import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.entity.Email;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.EmailRepository;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.repository.PhoneRepository;
import com.safetynet.api.service.exception.DataAlreadyCreatedException;
import com.safetynet.api.service.exception.DataAlreadyUpToDateException;
import com.safetynet.api.service.exception.UnknownPersonException;


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

    @Transactional
    public PersonDto createPerson(PersonDto dto) {
	// Check if person is already known in DB
	if (personRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isPresent()) {
	    throw new DataAlreadyCreatedException("Person with name :" + dto.getFirstName() + " "
		+ dto.getLastName()
		+ " is ever known in database, use update or delete functionnality to modify person's data.");
	}
	Person newPerson = new Person();
	newPerson.setFirstName(dto.getFirstName());
	newPerson.setLastName(dto.getLastName());
	// Check if phone is already known in DB to avoid duplicates
	Optional<Phone> phoneOptional = phoneRepository.findByPhoneNumber(dto.getPhone());
	if (phoneOptional.isPresent()) {
	    newPerson.setPhone(phoneOptional.get());
	} else {
	    newPerson.setPhone(phoneRepository.save(new Phone(dto.getPhone())));
	}
	// Check if the email is already known in DB to avoid duplicates
	Optional<Email> emailOptional = emailRepository.findByEmailAddress(dto.getEmail());
	if (emailOptional.isPresent()) {
	    newPerson.setEmail(emailOptional.get());
	} else {
	    newPerson.setEmail(emailRepository.save(new Email(dto.getEmail())));
	}
	// Check if the home is already known in DB to avoid duplicates
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
    public PersonDto update(PersonDto dto) {
	Person person;
	Optional<Person> personOptional = personRepository.findByFirstNameAndLastName(dto.getFirstName(),
	    dto.getLastName());
	if (personOptional.isEmpty()) {
	    throw new UnknownPersonException(dto.getFirstName(), dto.getLastName());
	} else if (personOptional.get() == new Person(dto)) {
	    throw new DataAlreadyUpToDateException("Person with name :" + dto.getFirstName() + " "
		+ dto.getLastName() + " is already up to date");
	} else {
	    person = personOptional.get();
	}
	if (dto.getEmail() != person.getEmail().getEmailAddress()) {
	    Optional<Email> emailOptional = emailRepository.findByEmailAddress(dto.getEmail());
	    if (emailOptional.isPresent()) {
		person.setEmail(emailOptional.get());
	    } else {
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
	Optional<Home> homeOptional = homeRepository.findByAddressAndCity(dto.getAddress(), dto.getCity());
	if (homeOptional.isPresent()) {
	    person.setHome(homeOptional.get());
	} else {
	    Home newHome = new Home(dto.getAddress(), dto.getCity(), dto.getZip());
	    person.setHome(homeRepository.save(newHome));
	}
	return new PersonDto(personRepository.save(person));
    }

    @Transactional
    public PersonDto deletePerson(String firstName, String lastName) {
	Optional<Person> personOptional = personRepository.findByFirstNameAndLastName(firstName, lastName);
	if (personOptional.isEmpty()) {
	    throw new UnknownPersonException(firstName, lastName);
	} else {
	    return new PersonDto(personRepository.deleteByFirstNameAndLastName(firstName, lastName));
	}
    }

    public Iterable<PersonDto> createAllFromJsonFile(JsonFilePersonsDto personsDto) {
	for (PersonDto personDto : personsDto.getPersonDtos()) {
	    this.createPerson(personDto);
	}
	return this.getAll();
    }

}
