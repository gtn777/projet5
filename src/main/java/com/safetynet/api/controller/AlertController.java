
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
public class AlertController extends Controller {

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
    public FireStationCoverageDto getCoverageDataByStationNumber(
	    @RequestParam int station) {
	return fireStationCoverageService.getData(station);
    }

    @GetMapping("/childAlert")
    public ChildAlertDto getChildAlert(@RequestParam String address) {
	return childAlertService.getAlertData(address);
    }

    @GetMapping("/phoneAlert")
    public PhoneAlertDto getPhoneAlert(@RequestParam int firestation) {
	System.out.println(phoneAlertService.getData(firestation));
	return phoneAlertService.getData(firestation);
    }

    @GetMapping("/communityEmail")
    public Iterable<String> getCommunityEmail(@RequestParam String city) {
	return communityEmailService.getData(city);
    }

    @GetMapping("/personInfo")
    public Iterable<PersonInfoDto> getPersonInfo(@RequestParam String firstName,
	    String lastName) {
	return personInfoService.getPersonInfo(firstName, lastName);
    }

    @GetMapping("/flood")
    public Object getFloodAlert(@RequestParam Set<Integer> stations) {
	return floodAlertService.getFloodAlert(stations);
    }

    @GetMapping("/fire")
    public Object getFireAlert(@RequestParam String address) {
	return fireAlertService.getData(address);
    }

}
//
//  
