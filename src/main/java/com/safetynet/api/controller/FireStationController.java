package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.endpoints.FireStationDto;
import com.safetynet.api.service.endpoint.FireStationService;


@RestController
public class FireStationController extends Controller {
    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/firestation")
    public ResponseEntity<FireStationDto> createMapping(@RequestBody FireStationDto dto) {
	return ResponseEntity.status(HttpStatus.CREATED).body(fireStationService.create(dto));
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStationDto> updateMapping(@RequestBody FireStationDto dto) {
	return ResponseEntity.status(HttpStatus.OK).body(fireStationService.create(dto));
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Object> deleteMapping(@RequestParam(required = false) String address,
	    @RequestParam(required = false) Integer station) {
	if (address != null) {
	    return ResponseEntity.status(HttpStatus.OK).body(fireStationService.deleteAddressMapping(address));
	} else if (station != null) {
	    return ResponseEntity.status(HttpStatus.OK).body(fireStationService.deleteStationMapping(station));
	} else {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		    .body("No address of type string or station of type integer found as request paramater.");
	}
    }

    @GetMapping("/firestationAll")
    public Object getAll() { return fireStationService.getAll(); }
}
