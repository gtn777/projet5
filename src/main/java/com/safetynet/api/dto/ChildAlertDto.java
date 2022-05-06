package com.safetynet.api.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.safetynet.api.entity.Person;

import lombok.Data;

@Data
public class ChildAlertDto implements Serializable {

    private static final long serialVersionUID = -973005033413029891L;

    public ChildAlertDto() {}

    public ChildAlertDto(Iterable<Person> persons) {
	for (Person p : persons) {
	    if (p.getAge() == 999) {
		this.unknownAge.add(p.getFirstName() + " " + p.getLastName());
	    } else if (p.getAge() <= 18) {
		Map<String, Object> child = new HashMap<String, Object>();
		child.put("firstName", p.getFirstName());
		child.put("lastName", p.getLastName());
		child.put("age", p.getAge());
		this.children.add(child);
	    } else {
		adults.add(p.getFirstName() + " " + p.getLastName());
	    }
	}
    }

    private Set<Map<String, Object>> children = new HashSet<Map<String, Object>>();

    private Set<String> adults = new HashSet<String>();

    private Set<String> unknownAge = new HashSet<String>();

}
