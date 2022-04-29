package com.safetynet.api.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.entity.Allergie;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Medication;
import com.safetynet.api.entity.Person;
import com.safetynet.api.util.DateUtil;

import lombok.Data;

@Data
public class PersonInfoDto implements Serializable {

    private static final long serialVersionUID = 6531817147980692525L;

    public PersonInfoDto() {}

    public PersonInfoDto(Person p, MedicalRecord mr) {
	this.firstName = p.getFirstName();
	this.lastName = p.getLastName();
	this.address = p.getHome().getAddress();
	this.age = DateUtil.calculateAgeWithJava7(mr.getBirthdate());
	for (Medication m : mr.getRecordMedications()) {
	    this.medications.add(m.getMedicationString());
	}
	for (Allergie a : mr.getRecordAllergies()) {
	    this.allergies.add(a.getAllergieString());
	}
    }

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private Set<String> medications = new HashSet<String>();
    private Set<String> allergies = new HashSet<String>();

}
