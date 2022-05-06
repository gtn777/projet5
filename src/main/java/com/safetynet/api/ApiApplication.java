package com.safetynet.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.api.util.JsonFileToDatabase;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
	SpringApplication.run(ApiApplication.class, args);
	JsonFileToDatabase jsonFileToDatabase = new JsonFileToDatabase();
	jsonFileToDatabase.readValues();
    }
}
