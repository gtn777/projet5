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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "phone")
public class Phone implements Serializable {

    private static final long serialVersionUID = 4365254477438239553L;

    public Phone() {}

    public Phone(String phoneNumberString) {
	this.phoneNumber = phoneNumberString;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "phone", targetEntity = Person.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Person> persons = new HashSet<Person>();

}
