package com.safetynet.api.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.JSONPObject;
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

    public Object getFloodAlert(Set<Integer> stations) {	
	
	
	return personRepository.findAllByHomeStation(4);
	
    }

}
