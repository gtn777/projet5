package com.safetynet.api.service.exception;

public class UnknownCityException extends UnknownDataException {

    private static final long serialVersionUID = 3765740588072751028L;

    public UnknownCityException(String city) {
	super(String.format("No data available for this city, please check the entered value: %s", city));
    }

}
