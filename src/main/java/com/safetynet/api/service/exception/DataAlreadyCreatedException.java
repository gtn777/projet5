package com.safetynet.api.service.exception;

public class DataAlreadyCreatedException extends RuntimeException{

    private static final long serialVersionUID = -1493230516596953246L;
    
    public DataAlreadyCreatedException(String string) {
	super(string);
    }

}
