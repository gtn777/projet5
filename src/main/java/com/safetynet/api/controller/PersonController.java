package com.safetynet.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.dto.endpoints.PersonsDto;
import com.safetynet.api.service.endpoint.PersonService;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<PersonDto> getAllPersons() {
	return personService.getAll();
    }

    @PostMapping("/persons")
    public PersonDto createPerson(@RequestBody PersonDto dto) {
	return personService.createPerson(dto);
    }

    @PostMapping("/persons/group")
    public Iterable<PersonDto> createPerson(@RequestBody Iterable<PersonDto> personList) {
	return personService.createAllPerson(new PersonsDto());
    }

    @PutMapping("/persons")
    public PersonDto updatePerson(@RequestBody PersonDto dto) {
	return personService.updatePerson(dto);
    }

    @DeleteMapping("/persons")
    public void updatePerson(@RequestBody Map<String, String> map) {
	personService.deletePerson(map.get("firstName"), map.get("lastName"));
    }
}
