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
@Table(name = "allergie")
public class Allergie implements Serializable {

    public Allergie() {
	super();
    }

    public Allergie(String newAllergieString) {
	super();
	this.allergieString = newAllergieString;
    }

    private static final long serialVersionUID = -5550061646608758187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "allergie_name", unique = true)
    private String allergieString;

    @ManyToMany(mappedBy = "recordAllergies", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<MedicalRecord> allergieRecords = new HashSet<MedicalRecord>();

}
