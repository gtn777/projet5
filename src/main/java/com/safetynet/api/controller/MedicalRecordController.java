package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.endpoints.MedicalRecordDto;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.service.endpoint.MedicalRecordService;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecord")
    public Iterable<MedicalRecordDto> getAll() {
	return medicalRecordService.getAll();
    }

    @PostMapping("/medicalRecord")
    public MedicalRecord create(@RequestBody MedicalRecordDto dto) {
	return medicalRecordService.create(dto);
    }

    @PutMapping("/medicalRecord")
    public MedicalRecordDto update(@RequestBody MedicalRecordDto dto) {
	return medicalRecordService.update(dto);
    }
    
    @DeleteMapping("/medicalrecord")
    public MedicalRecordDto delete(@RequestBody MedicalRecordDto dto) {
	return medicalRecordService.delete(dto);
    }

}
