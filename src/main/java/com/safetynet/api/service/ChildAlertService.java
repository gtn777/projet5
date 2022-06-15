
package com.safetynet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownAddressException;


@Service
public class ChildAlertService {

    @Autowired
    PersonRepository personRepository;

    public ChildAlertDto getData(String address) {
	Iterable<Person> persons = personRepository.findAllByHomeAddress(address);
	if (persons != null && persons.iterator().hasNext()) {
	    return new ChildAlertDto(persons);
	} else {
	    throw new UnknownAddressException(address);
	}
    }

}
