package com.safetynet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.PhoneAlertDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.repository.PhoneRepository;
import com.safetynet.api.service.exception.UnknownFireStationException;

import lombok.Data;

@Data
@Service
public class PhoneAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    public PhoneAlertDto getData(int stationNumber) {
	Iterable<Person> persons = personRepository.findAllByHomeStation(stationNumber);
	if (persons != null && persons.iterator().hasNext()) {
	    return new PhoneAlertDto(persons);
	} else {
	    throw new UnknownFireStationException(stationNumber);
	}
    }
}
