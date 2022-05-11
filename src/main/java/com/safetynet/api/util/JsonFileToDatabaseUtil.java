package com.safetynet.api.util;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.dto.endpoints.JsonFileFirestationsDto;
import com.safetynet.api.dto.endpoints.JsonFileMedicalrecordsDto;
import com.safetynet.api.dto.endpoints.JsonFilePersonsDto;
import com.safetynet.api.service.endpoint.FireStationService;
import com.safetynet.api.service.endpoint.MedicalRecordService;
import com.safetynet.api.service.endpoint.PersonService;

import lombok.Data;

@Data
@Service
public class JsonFileToDatabaseUtil {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Transactional
    @PostConstruct
    public void readValues() {
	JsonFilePersonsDto personsDto = new JsonFilePersonsDto();
	JsonFileFirestationsDto firestationsDto = new JsonFileFirestationsDto();
	JsonFileMedicalrecordsDto medicalrecordsDto = new JsonFileMedicalrecordsDto();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    personsDto = mapper.readValue(new File("src/main/resources/data.json"), JsonFilePersonsDto.class);
	    personService.createAllFromJsonFile(personsDto);
	    firestationsDto = mapper.readValue(new File("src/main/resources/data.json"), JsonFileFirestationsDto.class);
	    fireStationService.createAllFromJsonFile(firestationsDto);
	    medicalrecordsDto = mapper.readValue(new File("src/main/resources/data.json"), JsonFileMedicalrecordsDto.class);
	    medicalRecordService.createAllFromJsonFile(medicalrecordsDto);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
