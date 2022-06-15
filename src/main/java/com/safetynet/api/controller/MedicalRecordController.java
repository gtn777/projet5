
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

    /**
     * @return All the medical records.
     */
    @GetMapping("/medicalRecord")
    public Iterable<MedicalRecordDto> getAll() { return medicalRecordService.getAll(); }

    /**
     * 
     * @param dto json object MedicalRecordDto to be created.
     * @return json object MedicalRecordDto after data created and saved in database.
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> create(@RequestBody MedicalRecordDto dto) {
	return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.create(dto));
    }

    /**
     * 
     * @param dto json object MedicalRecordDto to be updated.
     * @return json object MedicalRecordDto after data updated. *
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDto> update(@RequestBody MedicalRecordDto dto) {
	return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.update(dto));
    }

    /**
     * 
     * @param firstName from person whose medical record will be deleted.
     * @param lastName from person whose medical record will be deleted.
     * @return message after person deleted.
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<String> delete(@RequestParam String firstName, String lastName) {
	return ResponseEntity.status(HttpStatus.OK)
		.body(medicalRecordService.delete(firstName, lastName));
    }

}
//
