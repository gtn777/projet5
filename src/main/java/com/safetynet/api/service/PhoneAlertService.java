package com.safetynet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.PhoneAlertDto;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.repository.PhoneRepository;

import lombok.Data;

@Data
@Service
public class PhoneAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    public PhoneAlertDto getAllPhoneByStation(int stationNumber) {
	return new PhoneAlertDto(personRepository.findAllByHomeStation(stationNumber));
    }
}
