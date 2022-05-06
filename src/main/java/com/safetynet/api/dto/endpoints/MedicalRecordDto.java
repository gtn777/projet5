package com.safetynet.api.dto.endpoints;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import com.safetynet.api.entity.Allergie;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Medication;

import lombok.Data;

@Data
public class MedicalRecordDto implements Serializable {

    private static final long serialVersionUID = -4116396510745859039L;

    public MedicalRecordDto() {}

    public MedicalRecordDto(MedicalRecord mr) {
	this.firstName = mr.getPerson().getFirstName();
	this.lastName = mr.getPerson().getLastName();
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	String formattedDate = mr.getBirthdate().format(formatter);
	this.birthdate = formattedDate;
	
	Set<String> newAllergies = new HashSet<String>();
	for (Allergie a : mr.getRecordAllergies()) { newAllergies.add(a.getAllergieString()); }
	this.allergies = newAllergies;
	
	Set<String> newMedications = new HashSet<String>();
	for (Medication m : mr.getRecordMedications()) { newMedications.add(m.getMedicationString()); }
	this.medications = newMedications;
    }

    private String firstName;
    private String lastName;
    private String birthdate;
    private Iterable<String> medications;
    private Iterable<String> allergies;

}
