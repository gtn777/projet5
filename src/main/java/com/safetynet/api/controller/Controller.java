
package com.safetynet.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.service.exception.DataAlreadyCreatedException;
import com.safetynet.api.service.exception.DataAlreadyUpToDateException;
import com.safetynet.api.service.exception.UnknownDataException;


@RestController
public abstract class Controller {

    private static Logger logger = LoggerFactory.getLogger(AlertController.class);

    @ExceptionHandler(value = { UnknownDataException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUnknownException(Exception e) {
	logger.error(e.getMessage());
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = { NumberFormatException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
	logger.error(e.getMessage());
	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		.body("Bad Request, number is expected for parameter format.");
    }

    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingServletRequestParameterException(Exception e) {
	logger.error(e.getMessage());
	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		.body(e.getMessage() + "\n" + "Exception: " + e.getClass().getName());
    }

    @ExceptionHandler(value = { DataAlreadyCreatedException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleDataAlreadyCreatedException(Exception e) {
	logger.error(e.getMessage());
	return ResponseEntity.status(HttpStatus.CONFLICT)
		.body(e.getMessage() + "\n" + "Exception: " + e.getClass().getName());
    }
    

    @ExceptionHandler(value = { DataAlreadyUpToDateException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleDataAlreadyUptoDateEntity(Exception e) {
	logger.error(e.getMessage());
	return ResponseEntity.status(HttpStatus.CONFLICT)
		.body(e.getMessage() + "\n" + "Exception: " + e.getClass().getName());
    }
    
    

}
