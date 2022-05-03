package com.safetynet.api.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.safetynet.api.entity.Person;

import lombok.Data;

@Data
public class FloodPersonDto implements Serializable {

    private static final long serialVersionUID = 8639882802901483528L;

    public FloodPersonDto() {}

    public FloodPersonDto(Person p) {
	this.name = p.getFirstName() + " " + p.getLastName();
	this.phone = p.getPhone().getPhoneNumber();
	this.age = p.getAge();
	this.medications = p.getMedicationsSet();
	this.allergies = p.getAllergiesSet();
    }

    private String name;

    private String phone;

    private Object age;

    Set<String> medications = new HashSet<String>();

    Set<String> allergies = new HashSet<String>();

}
