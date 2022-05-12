package com.safetynet.api.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.FireStationCoverageDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.FireStationNotFoundException;

import lombok.Data;

@Data
@Service
public class FireStationCoverageService {

    @Autowired
    private PersonRepository personRepository;

    public FireStationCoverageDto getCoverageDataForStation(int station) {

	Set<Person> findAllByHomeStation = (Set<Person>) personRepository.findAllByHomeStation(station);
	if ((findAllByHomeStation != null) && (findAllByHomeStation.size() > 0)) {
	    return new FireStationCoverageDto(findAllByHomeStation);
	} else {
	    throw new FireStationNotFoundException(station);
	}

    }

}
