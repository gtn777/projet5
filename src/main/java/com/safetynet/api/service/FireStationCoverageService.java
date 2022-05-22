
package com.safetynet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.FireStationCoverageDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownFireStationException;


@Service
public class FireStationCoverageService {

    @Autowired
    private PersonRepository personRepository;

    public FireStationCoverageDto getData(int station) {
	Iterable<Person> findAllByHomeStation = personRepository
		.findAllByHomeStation(station);
	if ((findAllByHomeStation != null)
		&& (findAllByHomeStation.iterator().hasNext())) {
	    return new FireStationCoverageDto(findAllByHomeStation);
	} else {
	    throw new UnknownFireStationException(station);
	}
    }

}
