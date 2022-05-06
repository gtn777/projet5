package com.safetynet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.FireStationCoverageDto;
import com.safetynet.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class FireStationCoverageService {

    @Autowired
    private PersonRepository personRepository;

    public FireStationCoverageDto getCoverageDataForStation(int station) {
	return new FireStationCoverageDto(personRepository.findAllByHomeStation(station));
    }

}
