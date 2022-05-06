package com.safetynet.api.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.safetynet.api.entity.Person;

import lombok.Data;

@Data
public class FireStationCoverageDto implements Serializable {

    private static final long serialVersionUID = 2907882609434635717L;

    public FireStationCoverageDto() {}

    public FireStationCoverageDto(Iterable<Person> persons) {
	for (Person p : persons) {
	    if (p.getAge() == 999) {
		this.unknownAge++;
	    } else if (p.getAge() <= 18) {
		this.children++;
	    } else {
		this.adults++;
	    }
	    this.persons.add(new FireStationCoveragePersonDto(p));
	}
    }

    private Set<FireStationCoveragePersonDto> persons = new HashSet<FireStationCoveragePersonDto>();
    
    private int adults = 0;

    private int children = 0;

    private int unknownAge = 0;

}
