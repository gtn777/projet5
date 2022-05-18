package com.safetynet.api.dto.endpoints;

import java.io.Serializable;

import lombok.Data;

@Data
public class FireStationDto implements Serializable {

    private static final long serialVersionUID = 4330804786615586960L;


    public FireStationDto(String a, int s) {
	this.address = a;
	this.station = s;
    }

    private String address;

    private int station;

}
