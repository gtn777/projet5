package com.safetynet.api.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.dto.endpoints.PersonsDto;
import com.safetynet.api.service.endpoint.PersonService;

import lombok.Data;

@Data
@Service
public class JsonFileToDatabase {

    @Autowired
    private PersonService personService;

    @Transactional
    @PostConstruct
    public Iterable<PersonDto> readValues() {
	PersonsDto personsDto = new PersonsDto();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    personsDto = mapper.readValue(new File("src/main/resources/data.json"), PersonsDto.class);
//	    PersonService personService = new PersonService();
	    return personService.createAllFromJsonFile(personsDto);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return new HashSet<PersonDto>();
	}
    }
}
