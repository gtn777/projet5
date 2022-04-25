package com.safetynet.api.entity;

import java.io.Serializable;

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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = -1002856601378791502L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(targetEntity = Email.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Email email;

    @ManyToOne(targetEntity = Phone.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id", referencedColumnName = "id")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Phone phone;

    @ManyToOne(targetEntity = Home.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "home_id", referencedColumnName = "id")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Home home;
    
    @OneToOne(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private MedicalRecord medicalRecord;
    

}
