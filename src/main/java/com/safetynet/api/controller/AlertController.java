
package com.safetynet.api.controller;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.dto.FireAlertDto;
import com.safetynet.api.dto.FireStationCoverageDto;
import com.safetynet.api.dto.FloodOrFireAlertPersonDto;
import com.safetynet.api.dto.PersonInfoDto;
import com.safetynet.api.dto.PhoneAlertDto;
import com.safetynet.api.service.ChildAlertService;
import com.safetynet.api.service.CommunityEmailService;
import com.safetynet.api.service.FireAlertService;
import com.safetynet.api.service.FireStationCoverageService;
import com.safetynet.api.service.FloodAlertService;
import com.safetynet.api.service.PersonInfoService;
import com.safetynet.api.service.PhoneAlertService;


@RestController
public class AlertController extends Controller {

    private static Logger logger = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    PhoneAlertService phoneAlertService;

    @Autowired
    CommunityEmailService communityEmailService;

    @Autowired
    PersonInfoService personInfoService;

    @Autowired
    FloodAlertService floodAlertService;

    @Autowired
    FireAlertService fireAlertService;

    @Autowired
    ChildAlertService childAlertService;

    @Autowired
    FireStationCoverageService fireStationCoverageService;

    @GetMapping("/firestation")
    public ResponseEntity<FireStationCoverageDto> getCoverageDataByStationNumber(
	    @RequestParam int station) {
	logger.debug("Appel avec le parametre station: " + station);
	FireStationCoverageDto result = fireStationCoverageService.getData(station);
	logger.info("Résultat de la requete" + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDto> getChildAlert(@RequestParam String address) {
	logger.debug("Appel avec le parametre adresse: " + address);
	ChildAlertDto result = childAlertService.getData(address);
	logger.info("Résultat de la requete: " + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneAlertDto> getPhoneAlert(@RequestParam int firestation) {
	logger.debug("Appel avec le parametre station: " + firestation);
	PhoneAlertDto result = phoneAlertService.getData(firestation);
	logger.info("Resultat de la requete: " + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<Iterable<String>> getCommunityEmail(@RequestParam String city) {
	logger.debug("Appel avec le parametre city: " + city);
	Iterable<String> result = communityEmailService.getData(city);
	logger.info("Résultat de la requete: " + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<Set<PersonInfoDto>> getPersonInfo(@RequestParam String firstName,
	    String lastName) {
	logger.debug(
		"Appel avec les parametres firstName: " + firstName + " et lastName: " + lastName);
	Set<PersonInfoDto> result = personInfoService.getData(firstName, lastName);
	logger.info("Resultat de la requete: " + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/flood")
    public ResponseEntity<Map<String, Set<FloodOrFireAlertPersonDto>>> getFloodAlert(
	    @RequestParam Set<Integer> stations) {
	logger.debug("Appel avec le parametre stations: " + stations);
	Map<String, Set<FloodOrFireAlertPersonDto>> result = floodAlertService.getData(stations);
	logger.info("Resultat de la requete: " + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/fire")
    public ResponseEntity<FireAlertDto> getFireAlert(@RequestParam String address) {
	logger.debug("Appel avec le parametre adresse: " + address);
	FireAlertDto result = fireAlertService.getData(address);
	logger.info("Resultat de la requete: " + result);
	return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
//
//  
