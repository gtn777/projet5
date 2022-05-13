package com.safetynet.api.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.FloodOrFireAlertPersonDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownFireStationException;

import lombok.Data;

@Data
@Service
public class FloodAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private HomeRepository homeRepository;

    public Object getFloodAlert(Iterable<Integer> stations) {
	// if a station is unknown in database throw new exception
	for (Integer station : stations) {
	    if (!homeRepository.existsByStation(station))
		throw new UnknownFireStationException(station);
	}
	
	Map<String, Set<FloodOrFireAlertPersonDto>> result = new HashMap<String, Set<FloodOrFireAlertPersonDto>>();
	Iterable<Person> personsIterable = personRepository.findAllByHomeStationIn(stations);
	if (personsIterable != null && personsIterable.iterator().hasNext()) {
	    for (Person p : personsIterable) {
		String address = p.getHome().getAddress();
		if (!result.containsKey(address)) { result.put(address, new HashSet<FloodOrFireAlertPersonDto>()); }
		result.get(address).add(new FloodOrFireAlertPersonDto(p));
	    }
	    return result;
	} else {
	    throw new UnknownFireStationException(stations);
	}
    }

}
