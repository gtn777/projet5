package com.safetynet.api.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.api.entity.Person;

import lombok.Data;

@Data
public class PhoneAlertDto implements Serializable {

    private static final long serialVersionUID = 3379660370989042845L;

    public PhoneAlertDto() {}

    public PhoneAlertDto(Iterable<Person> persons) {
	Set<String> s = new HashSet<String>();
	for (Person p : persons) {
	    s.add(p.getPhone().getPhoneNumber());
	}
	this.phoneAlert = s;
    }

    private Iterable<String> phoneAlert;

    @JsonProperty("Phone list")
    public Iterable<String> getPhoneAlert() {
	return this.phoneAlert;
    }
}
