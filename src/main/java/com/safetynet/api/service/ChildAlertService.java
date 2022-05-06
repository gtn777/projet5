package com.safetynet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class ChildAlertService {

    @Autowired
    PersonRepository personRepository;
    
    public ChildAlertDto getAlertData(String address) {
	
	return new ChildAlertDto(personRepository.findAllByHomeAddress(address));
	
    }
    
}
