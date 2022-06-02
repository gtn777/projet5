
package com.safetynet.api.service.exception;

public class UnknownMedicalRecordException extends UnknownDataException {

    private static final long serialVersionUID = 939359159286629246L;

    public UnknownMedicalRecordException(String firstName, String lastName) {
	super("No medical record found for " + firstName + " " + lastName);
    }

}
