package com.safetynet.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.FireAlertDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownAddressException;

import lombok.Data;

@Data
@Service
public class FireAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HomeRepository homeRepository;

    public FireAlertDto getData(String address) {

	Iterable<Person> persons;
	int station = -1;
	Optional<Home> homeOptional = homeRepository.findByAddress(address);
	if (homeOptional != null && homeOptional.isPresent()) {
	    station = homeOptional.get().getStation();
	    persons = homeOptional.get().getPersons();
	} else {
	    throw new UnknownAddressException(address);
	}

	return new FireAlertDto(address, persons, station);
    }

}
