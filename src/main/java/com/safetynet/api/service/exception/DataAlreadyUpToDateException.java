
package com.safetynet.api.service.exception;

public class DataAlreadyUpToDateException extends RuntimeException {

    private static final long serialVersionUID = -4626476966174416334L;

    public DataAlreadyUpToDateException(String string) { super(string); }

}