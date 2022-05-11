package com.safetynet.api.dto.endpoints;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonFileFirestationsDto implements Serializable{
    
    private static final long serialVersionUID = 7727571533245676526L;

    @JsonProperty("firestations")
    Set<FireStationDto> fireStationDtos = new HashSet<FireStationDto>();
    
    
}
