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
import com.safetynet.api.service.exception.UnknownPersonException;

import lombok.Data;

@Data
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
    public MedicalRecord create(MedicalRecordDto dto) {
	Person linkedPerson;
	Optional<Person> optionalPerson = personDAO.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());
	if (optionalPerson.isEmpty()) {
	    throw new UnknownPersonException(dto.getFirstName(), dto.getLastName());
	} else {
	    linkedPerson = optionalPerson.get();
	}

	MedicalRecord newRecord = new MedicalRecord();
	newRecord.setBirthdate(LocalDate.parse(dto.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));

	for (String dtoAllergie : dto.getAllergies()) {
	    Optional<Allergie> allergieOptional = allergieDAO.findByAllergieString(dtoAllergie);
	    newRecord.getRecordAllergies()
		    .add(allergieOptional.isPresent() ? allergieOptional.get() : allergieDAO.save(new Allergie(dtoAllergie)));
	}

	for (String dtoMedication : dto.getMedications()) {
	    Optional<Medication> newMedication = medicationDAO.findByMedicationString(dtoMedication);
	    newRecord.getRecordMedications()
		    .add(newMedication.isPresent() ? newMedication.get() : medicationDAO.save(new Medication(dtoMedication)));
	}

	linkedPerson.setMedicalRecord(newRecord);
	newRecord.setPerson(linkedPerson);
	personDAO.save(linkedPerson);
	return linkedPerson.getMedicalRecord();
    }

    public MedicalRecordDto update(MedicalRecordDto dto) {
	Person linkedPerson;
	Optional<Person> optionalPerson = personDAO.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());
	
	if (optionalPerson.isEmpty()) {
	    throw new UnknownPersonException(dto.getFirstName(), dto.getLastName());
	} else {
	    linkedPerson = optionalPerson.get();
	}
	
	for (String dtoAllergie : dto.getAllergies()) {
	    if (!linkedPerson.getAllergiesSet().contains(dtoAllergie)) {
		Optional<Allergie> allergieOptional = allergieDAO.findByAllergieString(dtoAllergie);
		linkedPerson.getMedicalRecord()
			.getRecordAllergies()
			.add(allergieOptional.isPresent() ? allergieOptional.get() : allergieDAO.save(new Allergie(dtoAllergie)));
	    }
	}
	
	for (String dtoMedication : dto.getMedications()) {
	    if (!linkedPerson.getMedicationsSet().contains(dtoMedication)) {
		Optional<Medication> medicationOptional = medicationDAO.findByMedicationString(dtoMedication);
		linkedPerson.getMedicalRecord()
			.getRecordMedications()
			.add(medicationOptional.isPresent() ? medicationOptional.get()
				: medicationDAO.save(new Medication(dtoMedication)));
	    }
	}
	
	personDAO.save(linkedPerson);
	return new MedicalRecordDto(linkedPerson.getMedicalRecord());
    }

    public MedicalRecordDto delete(MedicalRecordDto dto) {
	return null;
    }

    @Transactional
    public Iterable<MedicalRecordDto> createAllFromJsonFile(JsonFileMedicalrecordsDto dto) {
	for (MedicalRecordDto medicalRecord : dto.getMedicalRecordDtos()) {
	    create(medicalRecord);
	}
	return this.getAll();
    }

}
