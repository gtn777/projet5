package com.safetynet.api.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Locale;
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
	this.birthdate = DateFormat.getDateInstance( DateFormat.MEDIUM, Locale.US ).format(mr.getBirthdate());
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
