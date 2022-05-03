package com.safetynet.api;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.dto.endpoints.PersonsDto;
import com.safetynet.api.service.endpoint.PersonService;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
	SpringApplication.run(ApiApplication.class, args);
	PersonService service = new PersonService();
	PersonsDto personsDto = new PersonsDto();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    personsDto = mapper.readValue(new File("src/main/resources/data.json"), PersonsDto.class);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	service.createAllPerson(personsDto);
    }
}
