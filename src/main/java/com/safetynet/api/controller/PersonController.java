
package com.safetynet.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.service.endpoint.PersonService;


@RestController
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public Iterable<PersonDto> getAllPersons() { return personService.getAll(); }

    @PostMapping("/person")
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto dto) {
	logger.debug("Appel avec le body personDto: " + dto.toString());
	PersonDto result = personService.createPerson(dto);
	logger.info("Résultat de la requete" + result);
	return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/person")
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto dto) {
	logger.debug("Appel avec le body personDto: " + dto.toString());
	PersonDto result = personService.update(dto);
	logger.info("Résultat de la requete" + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/person")
    public ResponseEntity<PersonDto> updatePerson(@RequestParam String firstName, String lastName) {
	logger.debug("Appel avec les parametres firstName: " + firstName + " lastName: " + lastName);
	PersonDto result = personService.deletePerson(firstName, lastName);
	logger.info("Résultat de la requete" + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
