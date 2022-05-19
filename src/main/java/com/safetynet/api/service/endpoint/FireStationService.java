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
import com.safetynet.api.service.exception.DataAlreadyCreatedException;
import com.safetynet.api.service.exception.UnknownAddressException;
import com.safetynet.api.service.exception.UnknownFireStationException;

import lombok.Data;


@Data
@Service
public class FireStationService {
    @Autowired
    private HomeRepository homeDAO;

    @Transactional
    public FireStationDto create(FireStationDto dto) {
	Optional<Home> homeOptional = homeDAO.findByAddress(dto.getAddress());
	if (homeOptional.isEmpty()) {
	    throw new UnknownAddressException(dto.getAddress());
	} else {
	    Home home = homeOptional.get();
	    if (home.getStation() != dto.getStation()) {
		home.setStation(dto.getStation());
		Home savedHome = homeDAO.save(home);
		return new FireStationDto(savedHome.getAddress(), savedHome.getStation());
	    } else {
		throw new DataAlreadyCreatedException("Mapping already created. Impossible to create the entered value");
	    }
	}
    }

    @Transactional
    public Iterable<FireStationDto> createAllFromJson(JsonFileFirestationsDto jsonFileFirestationsDto) {
	Set<FireStationDto> returnValue = new HashSet<FireStationDto>();
	for (FireStationDto fireStationDto : jsonFileFirestationsDto.getFireStationDtos()) {
	    returnValue.add(this.create(fireStationDto));
	}
	return returnValue;
    }

    @Transactional
    public String deleteAddressMapping(String address) {
	Optional<Home> homeOptional = homeDAO.findByAddress(address);
	if (homeOptional.isEmpty()) {
	    throw new UnknownAddressException(address);
	} else {
	    Home home = homeOptional.get();
	    home.setStation(-1);
	    homeDAO.save(home);
	}
	return "The home at address \"" + address + "\" is no longer covered by any fire station.";
    }

    @Transactional
    public Set<String> deleteStationMapping(int station) {
	Set<String> deletedMapping = new HashSet<String>();
	Iterable<Home> homeIterable = homeDAO.findAllByStation(station);
	if (!homeIterable.iterator().hasNext()) {
	    throw new UnknownFireStationException(station);
	} else {
	    for (Home home : homeIterable) {
		home.setStation(-1);
		deletedMapping.add("The home at address \"" + home.getAddress() + "\" is no longer covered by any fire station.");
	    }
	    homeDAO.saveAll(homeIterable);
	    return deletedMapping;
	}
    }
    
    public Iterable<FireStationDto> getAll() {
	Set<FireStationDto> dtos = new HashSet<FireStationDto>();
	for (Home home : homeDAO.findAll()) {
	    dtos.add(new FireStationDto(home.getAddress(), home.getStation()));
	}
	return dtos;
    }

}
