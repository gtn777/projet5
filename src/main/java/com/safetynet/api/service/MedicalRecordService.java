package com.safetynet.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.dto.MedicalRecordDto;
import com.safetynet.api.entity.Allergie;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Medication;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.AllergieRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.MedicationRepository;
import com.safetynet.api.repository.PersonRepository;

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

    public MedicalRecord create(MedicalRecordDto dto) {
	Person linkedPerson;
	// si la personne est inconnue ou si un dossier medical lié à la même personne existe
	// deja
	// on retourne null
	if (personDAO.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isEmpty()) {
	    return null;
	} else {
	    linkedPerson = personDAO.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).get();
	    if (medicalRecordDAO.findById(linkedPerson.getId()).isPresent()) { return null; }
	}

	MedicalRecord newRecord = new MedicalRecord();
	try {
	    newRecord.setBirthdate(new SimpleDateFormat("MM/dd/yyyy").parse(dto.getBirthdate()));
	} catch (ParseException e) {
	    e.printStackTrace();
	}
//	pour chaque allergie du dto, on verifie si existant, si oui on add a newRecord.allergies, sinon on créée 
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

	newRecord.setPerson(linkedPerson);

	MedicalRecord savedRecord = medicalRecordDAO.save(newRecord);
	return savedRecord;
    }

    public Iterable<MedicalRecordDto> createAll(Iterable<MedicalRecordDto> dto) {
	for (MedicalRecordDto medicalRecord : dto) { create(medicalRecord); }
	return this.getAll();
    }
}
