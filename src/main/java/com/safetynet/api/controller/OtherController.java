package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.entity.Allergie;
import com.safetynet.api.entity.Email;
import com.safetynet.api.entity.Medication;
import com.safetynet.api.repository.AllergieRepository;
import com.safetynet.api.repository.EmailRepository;
import com.safetynet.api.repository.PhoneRepository;
import com.safetynet.api.service.OtherService;

@RestController
public class OtherController {

    @Autowired
    private OtherService otherService;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private AllergieRepository allergieRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @GetMapping("/emails")
    public Iterable<String> getAllEmails() {
//	return emailRepository.findAll();
	return otherService.getAllEmail();
    }

    @GetMapping("/phone")
    public Iterable<String> getAllPhoneNumber() {
	return otherService.getAllPhoneNumber();
    }

    @GetMapping("/email")
    public Iterable<PersonDto> getPersonsByEmailAddress(@RequestBody String emailString) {
	return otherService.getEmailPersons(emailString);
    }

    @PostMapping("/emails")
    public Email addNewEmail(@RequestBody String emailString) {
	return otherService.addEmail(emailString);
    }

    @GetMapping("/medications")
    public Iterable<Medication> getAllMedications() {
	return otherService.getAllMedications();
    }

    @GetMapping("/allergies")
    public Iterable<Allergie> getAllAllergies() {
	return allergieRepository.findAll();
    }

    @PostMapping("/allergies")
    public Allergie addNewAllergie(@RequestBody String newAllergieString) {
	return allergieRepository.save(new Allergie(newAllergieString));
    }

//    @GetMapping("/home")
//    public Iterable<Home> getAllHome(@RequestParam String dto) {
//	return otherService.getAllHomeByStationName(dto);
//    }
}
