package com.safetynet.api.service.exception;

public class UnknownAddressException extends UnknownDataException {

    private static final long serialVersionUID = -1801757213732504833L;

    public UnknownAddressException(String unknownAddress) {
	super(String.format("No data available for this address, please check the entered value: %s", unknownAddress));
    }

}
