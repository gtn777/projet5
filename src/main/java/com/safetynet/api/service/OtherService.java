//package com.safetynet.api.service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.safetynet.api.dto.endpoints.PersonDto;
//import com.safetynet.api.entity.Email;
//import com.safetynet.api.entity.Medication;
//import com.safetynet.api.entity.Person;
//import com.safetynet.api.entity.Phone;
//import com.safetynet.api.repository.EmailRepository;
//import com.safetynet.api.repository.HomeRepository;
//import com.safetynet.api.repository.MedicationRepository;
//import com.safetynet.api.repository.PersonRepository;
//import com.safetynet.api.repository.PhoneRepository;
//
//import lombok.Data;
//
//@Data
//@Service
//public class OtherService {
//    @Autowired
//    private EmailRepository emailRepository;
//
//    @Autowired
//    private PersonRepository personRepository;
//
//    @Autowired
//    private MedicationRepository medicationRepository;
//
//    @Autowired
//    private HomeRepository homeRepository;
//
//    @Autowired
//    private PhoneRepository phoneRepository;
//
//    public Iterable<String> getAllPhoneNumber(){
//	Set<String> allPhoneSet = new HashSet<String>();
//	for (Phone phone : phoneRepository.findAll()) {
//	    allPhoneSet.add(phone.getPhoneNumber());
//	}
//	return allPhoneSet;
//    }
//
//    public Iterable<String> getAllEmail() {
//	Set<String> allEmails = new HashSet<String>();
//	for (Email email : emailRepository.findAll()) {
//	    allEmails.add(email.getEmailAddress());
//	}
//	return allEmails;
//    }
//
//    public Iterable<PersonDto> getEmailPersons(String emailAddress) {
//	Iterable<Person> persons = emailRepository.findByEmailAddress(emailAddress).get().getPersons();
//	Set<PersonDto> personsDto = new HashSet<PersonDto>();
//	for (Person person : persons) {
//	    personsDto.add(new PersonDto(person));
//	}
//	return personsDto;
//    }
//
//    public Email addEmail(String emailAddress) {
//	return emailRepository.save(new Email(emailAddress));
//    }
//
//    public Iterable<Medication> getAllMedications() {
//	Set<Medication> allMedications = new HashSet<Medication>();
//	for (Medication medication : medicationRepository.findAll()) {
//	    allMedications.add(medication);
//	}
//	return allMedications;
//    }
//
////    public Iterable<Home> getAllHomeByStationName(String stationName){
////	return homeRepository.findAllByFireStationStationName(stationName);
////    }
//
//}
