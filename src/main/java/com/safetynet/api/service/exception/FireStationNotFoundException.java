package com.safetynet.api.service.exception;

public class FireStationNotFoundException extends RuntimeException {

    public FireStationNotFoundException() {}

    public FireStationNotFoundException(int notFoundStation) {

	super(String.format("firestation not found %s", notFoundStation));
    }

}
