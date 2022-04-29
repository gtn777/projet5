package com.safetynet.api.dto.endpoints;

import java.io.Serializable;

import com.safetynet.api.entity.Person;

import lombok.Data;

@Data
public class PersonDto implements Serializable {

    private static final long serialVersionUID = 6671080096474023105L;

    public PersonDto() {
	super();
    }

    public PersonDto(Person person) {
	this.firstName = person.getFirstName();
	this.lastName = person.getLastName();
	this.address = person.getHome().getAddress();
	this.city = person.getHome().getCity();
	this.zip = person.getHome().getZip();
	this.email = person.getEmail().getEmailAddress();
	this.phone = person.getPhone().getPhoneNumber();
    }

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String zip;

    private String phone;

    private String email;
}
