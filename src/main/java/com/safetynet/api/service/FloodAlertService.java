package com.safetynet.api.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.FloodPersonDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class FloodAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    HomeRepository homeRepository;

    public Object getFloodAlert(Iterable<Integer> stations) {
	Map<String, Set<FloodPersonDto>> result = new HashMap<String, Set<FloodPersonDto>>();
	Iterable<Person> personsIterable = personRepository.findAllByHomeStationIn(stations);

	for (Person p : personsIterable) {
	    String address = p.getHome().getAddress();
	    if (!result.containsKey(address)) { result.put(address, new HashSet<FloodPersonDto>()); }
	    result.get(address).add(new FloodPersonDto(p));
	}
	return result;
    }

}
