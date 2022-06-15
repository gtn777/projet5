
package com.safetynet.api.service.endpoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.endpoints.JsonFileMedicalrecordsDto;
import com.safetynet.api.dto.endpoints.MedicalRecordDto;
import com.safetynet.api.entity.Allergie;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Medication;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.AllergieRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.MedicationRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.DataAlreadyCreatedException;
import com.safetynet.api.service.exception.UnknownMedicalRecordException;
import com.safetynet.api.service.exception.UnknownPersonException;


@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordDAO;

    @Autowired
    private PersonRepository personDAO;

    @Autowired
    private AllergieRepository allergieDAO;

    @Autowired
    private MedicationRepository medicationDAO;

    public Iterable<MedicalRecordDto> getAll() {
	Iterable<MedicalRecord> all = medicalRecordDAO.findAll();
	Set<MedicalRecordDto> dto = new HashSet<MedicalRecordDto>();
	for (MedicalRecord mr : all) {
	    dto.add(new MedicalRecordDto(mr));
	}
	return dto;
    }

    @Transactional
    public MedicalRecordDto create(MedicalRecordDto dto) {
	Person person;
	MedicalRecord newRecord;
	// Check if a person exists with firstName and lastName
	Optional<Person> optionalPerson = personDAO.findByFirstNameAndLastName(dto.getFirstName(),
		dto.getLastName());
	if (optionalPerson == null || optionalPerson.isEmpty()) {
	    throw new UnknownPersonException(dto.getFirstName(), dto.getLastName());
	} else {
	    person = optionalPerson.get();
	    // check if person medical record is already created in database
	    if (person.getMedicalRecord() != null) {
		throw new DataAlreadyCreatedException("Medical record already created for "
			+ dto.getFirstName() + "0" + dto.getLastName()
			+ " , use update or delete request functionnality to modify record data.");
	    } else {
		newRecord = new MedicalRecord();
		newRecord.setBirthdate(LocalDate.parse(dto.getBirthdate(),
			DateTimeFormatter.ofPattern("MM/dd/yyyy")));
	    }
	}
	// Management of allergy duplicates
	for (String dtoAllergie : dto.getAllergies()) {
	    Optional<Allergie> allergieOptional = allergieDAO.findByAllergieString(dtoAllergie);
	    newRecord.getRecordAllergies()
		    .add(allergieOptional.isPresent() ? allergieOptional.get()
			    : allergieDAO.save(new Allergie(dtoAllergie)));
	}
	// Management of medication duplicates
	for (String dtoMedication : dto.getMedications()) {
	    Optional<Medication> newMedication = medicationDAO
		    .findByMedicationString(dtoMedication);
	    newRecord.getRecordMedications()
		    .add(newMedication.isPresent() ? newMedication.get()
			    : medicationDAO.save(new Medication(dtoMedication)));
	}
	person.setMedicalRecord(newRecord);
	newRecord.setPerson(person);
	return new MedicalRecordDto(personDAO.save(person).getMedicalRecord());
    }

    public MedicalRecordDto update(MedicalRecordDto dto) {
	Person person;
	Optional<Person> optionalPerson = personDAO.findByFirstNameAndLastName(dto.getFirstName(),
		dto.getLastName());
	if (optionalPerson == null || optionalPerson.isEmpty()) {
	    throw new UnknownPersonException(dto.getFirstName(), dto.getLastName());
	} else {
	    person = optionalPerson.get();
	    if (person.getMedicalRecord() == null) {
		throw new UnknownMedicalRecordException(dto.getFirstName(), dto.getLastName());
	    }
	}
	person.getMedicalRecord()
		.setBirthdate(LocalDate.parse(dto.getBirthdate(),
			DateTimeFormatter.ofPattern("MM/dd/yyyy")));
	for (String dtoAllergie : dto.getAllergies()) {
	    if (!person.getAllergiesSet().contains(dtoAllergie)) {
		Optional<Allergie> allergieOptional = allergieDAO.findByAllergieString(dtoAllergie);
		person.getMedicalRecord()
			.getRecordAllergies()
			.add(allergieOptional.isPresent() ? allergieOptional.get()
				: allergieDAO.save(new Allergie(dtoAllergie)));
	    }
	}
	for (String dtoMedication : dto.getMedications()) {
	    if (!person.getMedicationsSet().contains(dtoMedication)) {
		Optional<Medication> medicationOptional = medicationDAO
			.findByMedicationString(dtoMedication);
		person.getMedicalRecord()
			.getRecordMedications()
			.add(medicationOptional.isPresent() ? medicationOptional.get()
				: medicationDAO.save(new Medication(dtoMedication)));
	    }
	}
	return new MedicalRecordDto(personDAO.save(person).getMedicalRecord());
    }

    @Transactional
    public String delete(String firstName, String lastName) {
	Optional<MedicalRecord> optionalMedicalRecord = medicalRecordDAO
		.findByPersonFirstNameAndPersonLastName(firstName, lastName);
	if (optionalMedicalRecord.isEmpty()) {
	    throw new UnknownMedicalRecordException(firstName, lastName);
	}
	medicalRecordDAO.deleteByPersonFirstNameAndPersonLastName(firstName, lastName);
	return "Medical record data deleted for person " + firstName + " " + lastName;
    }

    @Transactional
    public Iterable<MedicalRecordDto> createAllFromJsonFile(JsonFileMedicalrecordsDto dto) {
	for (MedicalRecordDto medicalRecord : dto.getMedicalRecordDtos()) {
	    create(medicalRecord);
	}
	return this.getAll();
    }

}
