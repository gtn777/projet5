
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
@Table(name = "home")
public class Home implements Serializable {

    private static final long serialVersionUID = 6835112859185725245L;

    public Home() {}

    public Home(String address, String city, String zip) {
	this.address = address;
	this.city = city;
	this.zip = zip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String zip;

    @OneToMany(mappedBy = "home", targetEntity = Person.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Person> persons = new HashSet<Person>();

    @Column(name = "fire_station", nullable = true)
    private int station;

    public void setStation(int s) { this.station = s; }

}
