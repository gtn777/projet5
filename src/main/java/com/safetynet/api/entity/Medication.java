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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "medication")
public class Medication implements Serializable {

    public Medication() {};

    public Medication(String medicalString) {
	this.medicationString = medicalString;
    }

    private static final long serialVersionUID = -1879617330842941962L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "medication_name", unique = true)
    private String medicationString;

    @ManyToMany(mappedBy = "recordMedications", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<MedicalRecord> medicationRecords = new HashSet<MedicalRecord>();

}
