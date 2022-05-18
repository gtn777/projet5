package com.safetynet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.service.exception.DataAlreadyCreatedException;
import com.safetynet.api.service.exception.UnknownDataException;

@RestController
public abstract class Controller {

    @ExceptionHandler(value = { UnknownDataException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUnknownException(Exception exception) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = { NumberFormatException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException exception) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request, number is expected for parameter format.");
    }

    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingServletRequestParameterException(Exception exception) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		.body(exception.getMessage() + "\n" + "Exception: " + exception.getClass().getName());
    }

    @ExceptionHandler(value = { DataAlreadyCreatedException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleDataAlreadyCreatedException(Exception exception) {
	return ResponseEntity.status(HttpStatus.CONFLICT)
		.body(exception.getMessage() + "\n" + "Exception: " + exception.getClass().getName());
    }
}
