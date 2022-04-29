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
@Table(name = "email")
public class Email implements Serializable {

    private static final long serialVersionUID = 3265549871L;

    public Email() {}

    public Email(String emailAddress) {
	this.emailAddress = emailAddress;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @OneToMany(mappedBy = "email", targetEntity = Person.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Person> persons = new HashSet<Person>();

}
