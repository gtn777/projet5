package com.safetynet.api.service.exception;

public abstract class UnknownDataException extends RuntimeException {

    private static final long serialVersionUID = 4971592770532109621L;

    public UnknownDataException() {}

    public UnknownDataException(String string) {
	super(string);
    }
}
