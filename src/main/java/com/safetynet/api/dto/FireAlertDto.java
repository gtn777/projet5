package com.safetynet.api.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.safetynet.api.entity.Person;

import lombok.Data;

@Data
public class FireAlertDto implements Serializable {

    private static final long serialVersionUID = -8085816394687247297L;

    public FireAlertDto() {}

    public FireAlertDto(String address, Iterable<Person> persons, int station) {
	this.address = address;
	this.station = station;
	for (Person p : persons) {
	    this.persons.add(new FloodOrFireAlertPersonDto(p));
	}
    }

    private String address;

    private int station;

    private Set<FloodOrFireAlertPersonDto> persons = new HashSet<FloodOrFireAlertPersonDto>();

}
