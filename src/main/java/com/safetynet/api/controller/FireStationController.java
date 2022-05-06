package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.endpoints.FireStationDto;
import com.safetynet.api.service.endpoint.FireStationService;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

//    @GetMapping("/firestation")
//    public Iterable<FireStationDto> readAllFireStations() {
//	return fireStationService.getAll();
//    }

    @PostMapping("/firestation")
    public FireStationDto create(@RequestBody FireStationDto dto) {
	return fireStationService.addOne(dto);
    }

    @PostMapping("/firestation/group")
    public Iterable<FireStationDto> createGroup(@RequestBody Iterable<FireStationDto> dto) {
	return fireStationService.addAll(dto);
    }
}
