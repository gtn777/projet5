package com.safetynet.api.entity;

import java.time.LocalDate;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "medicalRecord", targetEntity = Person.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Person person;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @ManyToMany(targetEntity = Allergie.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "medical_record_allergie", joinColumns = @JoinColumn(name = "medical_record_id"), inverseJoinColumns = @JoinColumn(name = "allergie_id"))
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    Set<Allergie> recordAllergies = new HashSet<Allergie>();

    @ManyToMany(targetEntity = Medication.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "medical_record_medication", joinColumns = @JoinColumn(name = "medical_record_id"), inverseJoinColumns = @JoinColumn(name = "medication_id"))
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    Set<Medication> recordMedications = new HashSet<Medication>();
}
