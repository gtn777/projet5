package com.safetynet.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.FloodAlertDto;
import com.safetynet.api.dto.PersonInfoDto;
import com.safetynet.api.dto.PhoneAlertDto;
import com.safetynet.api.service.CommunityEmailService;
import com.safetynet.api.service.FloodAlertService;
import com.safetynet.api.service.PersonInfoService;
import com.safetynet.api.service.PhoneAlertService;

@RestController
public class AlertController {

    @Autowired
    PhoneAlertService phoneAlertService;

    @Autowired
    CommunityEmailService communityEmailService;

    @Autowired
    PersonInfoService personInfoService;

    @Autowired
    FloodAlertService floodAlertService;

//    http://localhost:9000/phoneAlert?firestation=<firestation_number>
    @GetMapping("/phoneAlert")
    public PhoneAlertDto getPhoneAlert(@RequestParam int stationNumber) {
	return phoneAlertService.getAllPhoneByStation(stationNumber);
    }

//    http://localhost:8080/communityEmail?city=<city>
    @GetMapping("/communityEmail")
    public Iterable<String> getCommunityEmail(@RequestParam String city) {
	return communityEmailService.getMailByCity(city);
    }

//    http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @GetMapping("/personInfo")
    public Iterable<PersonInfoDto> getPersonInfo(@RequestParam String firstName, String lastName) {
	return personInfoService.getPersonInfo(firstName, lastName);
    }

//    http://localhost:8080/flood/stations?stations=<a list of station_numbers>
//    Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
//    personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
//    faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
    @GetMapping("/flood")
    public Object getFloodAlert(@RequestParam Set<Integer> stations) {
	return floodAlertService.getFloodAlert(stations);
    }

}
