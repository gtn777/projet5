package com.safetynet.api.dto.endpoints;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonsDto implements Serializable {

    private static final long serialVersionUID = 4699801137029008254L;

    @JsonProperty("persons")
    private Set<PersonDto> personDtos = new HashSet<PersonDto>();

}
