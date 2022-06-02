
package com.safetynet.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.util.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = -1002856601378791502L;

    public Person() {}

    public Person(String firstName, String lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
    }
 
    public Person(PersonDto dto) {
	this.firstName = dto.getFirstName();
	this.lastName = dto.getLastName();
	this.email = new Email(dto.getEmail());
	this.phone = new Phone(dto.getPhone());
	this.home = new Home(dto.getAddress(), dto.getCity(), dto.getZip());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(targetEntity = Email.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Email email;

    @ManyToOne(targetEntity = Phone.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "phone_id", referencedColumnName = "id")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Phone phone;

    @ManyToOne(targetEntity = Home.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "home_id", referencedColumnName = "id")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Home home;

    @OneToOne(targetEntity = MedicalRecord.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "medical_record_id", referencedColumnName = "id")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private MedicalRecord medicalRecord;

    public Set<String> getAllergiesSet() {
	Set<String> allergieSet = new HashSet<String>();
	if (this.getMedicalRecord() == null)
	    return allergieSet;
	for (Allergie a : this.getMedicalRecord().getRecordAllergies()) {
	    allergieSet.add(a.getAllergieString());
	}
	return allergieSet;
    }

    public Set<String> getMedicationsSet() {
	Set<String> medicationSet = new HashSet<String>();
	if (this.getMedicalRecord() == null)
	    return medicationSet;
	for (Medication m : this.getMedicalRecord().getRecordMedications()) {
	    medicationSet.add(m.getMedicationString());
	}
	return medicationSet;
    }

    public int getAge() {
	return this.getMedicalRecord() == null ? 999
	    : DateUtil.calculateAgeWithJava7(this.getMedicalRecord().getBirthdate());
    }

}
