package com.safetynet.api.service.exception;

public class UnknownFireStationException extends UnknownDataException {

    private static final long serialVersionUID = -6652653128379542993L;

    public UnknownFireStationException(int station) {
	super(String.format("No data available for this station number, please check the entered value: %s", station));
    }

    public UnknownFireStationException(Iterable<Integer> stations) {
	super(String.format("No data available for this station number, please check the entered value: %s", stations));
    }

}
