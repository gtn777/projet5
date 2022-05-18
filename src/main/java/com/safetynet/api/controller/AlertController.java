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
    public FireStationCoverageDto getCoverageDataByStationNumber(@RequestParam int station) {
	return fireStationCoverageService.getData(station);
    }

    @GetMapping("/childAlert")
    public ChildAlertDto getChildAlert(@RequestParam String address) { return childAlertService.getAlertData(address); }

    @GetMapping("/phoneAlert")
    public PhoneAlertDto getPhoneAlert(@RequestParam int firestation) { return phoneAlertService.getData(firestation); }

    @GetMapping("/communityEmail")
    public Iterable<String> getCommunityEmail(@RequestParam String city) { return communityEmailService.getData(city); }

    @GetMapping("/personInfo")
    public Iterable<PersonInfoDto> getPersonInfo(@RequestParam String firstName, String lastName) {
	return personInfoService.getPersonInfo(firstName, lastName);
    }

    @GetMapping("/flood")
    public Object getFloodAlert(@RequestParam Set<Integer> stations) { return floodAlertService.getFloodAlert(stations); }

    @GetMapping("/fire")
    public Object getFireAlert(@RequestParam String address) { return fireAlertService.getData(address); }
//    @ExceptionHandler(value = { UnknownDataException.class })
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleUnknownException(Exception exception) {
//	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
//    }
//
//    @ExceptionHandler(value = { NumberFormatException.class })
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handleNumberFormatException(NumberFormatException exception) {
//	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request, number format expected for parameter.");
//    }
//
//    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handleMissingServletRequestParameterException(Exception exception) {
//	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//		.body(exception.getMessage() + "\n" + "Exception: " + exception.getClass().getName());
//    }
}
//  http://localhost:8080/firestation?stationNumber=<station_number>
//  http://localhost:8080/fire?address=<address>
//  http://localhost:8080/flood/stations?stations=<a list of station_numbers>
//  http://localhost:8080/childAlert?address=<address>
//  http://localhost:8080/phoneAlert?firestation=<firestation_number>
//  http://localhost:8080/communityEmail?city=<city>
//  http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
