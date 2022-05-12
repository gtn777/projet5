package com.safetynet.api.service.endpoint;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.endpoints.FireStationDto;
import com.safetynet.api.dto.endpoints.JsonFileFirestationsDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.repository.HomeRepository;

import lombok.Data;

@Data
@Service
public class FireStationService {

    @Autowired
    private HomeRepository homeDAO;

    public Iterable<FireStationDto> getAll() {
	Set<FireStationDto> dtos = new HashSet<FireStationDto>();
	for (Home home : homeDAO.findAll()) {
	    dtos.add(new FireStationDto(home.getAddress(), home.getStation()));
	}
	return dtos;
    }

    @Transactional
    public FireStationDto addOne(FireStationDto dto) {
	Optional<Home> homeOptional = homeDAO.findByAddress(dto.getAddress());
	if (homeOptional.isEmpty()) {
	    return null; // address unknown
	} else {
	    Home home = homeOptional.get();
	    if (home.getStation() != dto.getStation()) {
		home.setStation(dto.getStation());
		homeDAO.save(home);
		return new FireStationDto(home.getAddress(), home.getStation());
	    } else {
		return null; // ever associated
	    }
	}

    }

    @Transactional
    public Iterable<FireStationDto> addAll(Iterable<FireStationDto> dto) {
	Set<FireStationDto> returnValue = new HashSet<FireStationDto>();
	for (FireStationDto fireStationDto : dto) {
	    returnValue.add(addOne(fireStationDto));
	}
	return returnValue;
    }

    @Transactional
    public Iterable<FireStationDto> createAllFromJsonFile(JsonFileFirestationsDto dto) {
	Set<FireStationDto> returnValue = new HashSet<FireStationDto>();
	for (FireStationDto fireStationDto : dto.getFireStationDtos()) {
	    returnValue.add(addOne(fireStationDto));
	}
	return returnValue;
    }

}
