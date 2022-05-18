package com.safetynet.api.service.exception;

public class UnknownPersonException extends UnknownDataException {

    public UnknownPersonException(String firstName, String lastName) {
	super(String.format("No data available for this person: " + firstName + " " + lastName));
    }

    private static final long serialVersionUID = -619180640356846141L;

}
