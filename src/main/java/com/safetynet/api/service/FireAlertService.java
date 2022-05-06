package com.safetynet.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.FireAlertDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class FireAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HomeRepository homeRepository;

    public FireAlertDto getAddressData(String address) {

	Iterable<Person> persons;
	int station = 0;
	Optional<Home> homeOptional = homeRepository.findByAddress(address);
	if (homeOptional.isEmpty()) {
	    return null;// no home at this address, address unknown
	} else {
	    station = homeOptional.get().getStation();
	    persons = homeOptional.get().getPersons();
	}

	return new FireAlertDto(address, persons, station);
    }

}
