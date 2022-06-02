
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

import com.safetynet.api.dto.endpoints.MedicalRecordDto;
import com.safetynet.api.service.endpoint.MedicalRecordService;


@RestController
public class MedicalRecordController extends Controller {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecord")
    public Iterable<MedicalRecordDto> getAll() { return medicalRecordService.getAll(); }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> create(@RequestBody MedicalRecordDto dto) {
	return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.create(dto));
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> update(@RequestBody MedicalRecordDto dto) {
	return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.update(dto));
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> delete(@RequestParam String firstName, String lastName) {
	return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.delete(firstName, lastName));
    }

}
//
