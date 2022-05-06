package com.safetynet.api.dto;

import java.io.Serializable;

import com.safetynet.api.entity.Person;

import lombok.Data;

@Data
public class FireStationCoveragePersonDto implements Serializable {

    private static final long serialVersionUID = -6582474219637560774L;

    public FireStationCoveragePersonDto() {}

    public FireStationCoveragePersonDto(Person p) {
	this.firstName = p.getFirstName();
	this.lastName = p.getLastName();
	this.address = p.getHome().getAddress();
	this.phone = p.getPhone().getPhoneNumber();
    }

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

}
