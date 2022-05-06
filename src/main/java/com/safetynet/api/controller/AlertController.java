package com.safetynet.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.dto.FireStationCoverageDto;
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
public class AlertController {

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

//    	http://localhost:8080/firestation?stationNumber=<station_number>
    @GetMapping("/firestation")
    public FireStationCoverageDto getCoverageDataByStationNumber(@RequestParam int station) {
	return fireStationCoverageService.getCoverageDataForStation(station);
    }

//    http://localhost:8080/childAlert?address=<address>
    @GetMapping("/childAlert")
    public ChildAlertDto getChildAlert(@RequestParam String address) {
	return childAlertService.getAlertData(address);
    }

//    http://localhost:8080/phoneAlert?firestation=<firestation_number>
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
    @GetMapping("/flood")
    public Object getFloodAlert(@RequestParam Set<Integer> stations) {
	return floodAlertService.getFloodAlert(stations);
    }

//    http://localhost:8080/fire?address=<address>
    @GetMapping("/fire")
    public Object getFireAlert(@RequestParam String address) {
	return fireAlertService.getAddressData(address);
    }

}
