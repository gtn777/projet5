
package com.safetynet.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.api.dto.endpoints.MedicalRecordDto;
import com.safetynet.api.entity.Allergie;
import com.safetynet.api.entity.Medication;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.AllergieRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.MedicationRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.endpoint.MedicalRecordService;
import com.safetynet.api.service.exception.UnknownPersonException;


@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @InjectMocks
    MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordDAO;

    @Mock
    private PersonRepository personDAO;

    @Mock
    private AllergieRepository allergieDAO;

    @Mock
    private MedicationRepository medicationDAO;

    private final String firstName = "Franck";

    private final String lastName = "Test";

    private final String birthDate = "07/20/1990";

    private final String allergie = "allergieTest1";

    private final String medication = "medicationTest1";

    private MedicalRecordDto dto;

    private Person person;

    @BeforeEach
    public void beforeEach() {
	dto = new MedicalRecordDto();
	Set<String> allergies = new HashSet<String>();
	Set<String> medications = new HashSet<String>();
	allergies.add(allergie);
	medications.add(medication);
	dto.setFirstName(firstName);
	dto.setLastName(lastName);
	dto.setBirthdate(birthDate);
	dto.setAllergies(allergies);
	dto.setMedications(medications);
	person = new Person(firstName, lastName);
    }

    @AfterEach
    public void afterEach() {
	verify(personDAO, Mockito.times(1)).findByFirstNameAndLastName(firstName, lastName);
    }

    @Test
    public void createTest_withAllergieAndMedication_alreadyInDatabase() {
	// GIVEN
	when(personDAO.findByFirstNameAndLastName(firstName, lastName))
		.thenReturn(Optional.of(person));
	when(allergieDAO.findByAllergieString(allergie)).thenReturn(Optional.empty());
	when(medicationDAO.findByMedicationString(medication)).thenReturn(Optional.empty());
	when(personDAO.save(any(Person.class))).then(returnsFirstArg());
	when(allergieDAO.save(any(Allergie.class))).then(returnsFirstArg());
	when(medicationDAO.save(any(Medication.class))).then(returnsFirstArg());
	// WHEN
	MedicalRecordDto result = medicalRecordService.create(dto);
	// THEN
	verify(allergieDAO, Mockito.times(1)).findByAllergieString(any(String.class));
	verify(medicationDAO, Mockito.times(1)).findByMedicationString(any(String.class));
	verify(allergieDAO, Mockito.times(1)).save(new Allergie(allergie));
	verify(medicationDAO, Mockito.times(1)).save(new Medication(medication));
	verify(personDAO, Mockito.times(1)).save(new Person(firstName, lastName));
	assertEquals(result, dto);
    }

    @Test
    public void createTest_withAllergieAndMedication_unknownInDatabase() {
	// GIVEN
	when(personDAO.findByFirstNameAndLastName(firstName, lastName))
		.thenReturn(Optional.of(person));
	when(allergieDAO.findByAllergieString(allergie))
		.thenReturn(Optional.of(new Allergie(allergie)));
	when(medicationDAO.findByMedicationString(medication))
		.thenReturn(Optional.of(new Medication(medication)));
	when(personDAO.save(any(Person.class))).then(returnsFirstArg());
	// WHEN
	MedicalRecordDto result = medicalRecordService.create(dto);
	// THEN
	verify(allergieDAO, Mockito.times(1)).findByAllergieString(any(String.class));
	verify(medicationDAO, Mockito.times(1)).findByMedicationString(any(String.class));
	verify(allergieDAO, Mockito.times(0)).save(any());
	verify(medicationDAO, Mockito.times(0)).save(any());
	verify(personDAO, Mockito.times(1)).save(new Person(firstName, lastName));
	assertEquals(result, dto);
    }

    @Test
    public void createTest_whenPersonIsUnknown() {
	Optional<Person> person = Optional.empty();
	when(personDAO.findByFirstNameAndLastName(firstName, lastName)).thenReturn(person);
	assertThrows(UnknownPersonException.class, () -> medicalRecordService.create(dto));
    }

    @Test
    public void createTest_whenDaoReturnNull() {
	when(personDAO.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
	assertThrows(UnknownPersonException.class, () -> medicalRecordService.create(dto));
    }

    @Test
    public void updateTest_withAllergieAndMedication_alreadyInDatabase() {
	// GIVEN
	when(personDAO.findByFirstNameAndLastName(firstName, lastName))
		.thenReturn(Optional.of(person));
	when(allergieDAO.findByAllergieString(allergie)).thenReturn(Optional.empty());
	when(medicationDAO.findByMedicationString(medication)).thenReturn(Optional.empty());
	when(personDAO.save(any(Person.class))).then(returnsFirstArg());
	when(allergieDAO.save(any(Allergie.class))).then(returnsFirstArg());
	when(medicationDAO.save(any(Medication.class))).then(returnsFirstArg());
	// WHEN
	MedicalRecordDto result = medicalRecordService.update(dto);
	// THEN
	verify(allergieDAO, Mockito.times(1)).findByAllergieString(any(String.class));
	verify(medicationDAO, Mockito.times(1)).findByMedicationString(any(String.class));
	verify(allergieDAO, Mockito.times(1)).save(new Allergie(allergie));
	verify(medicationDAO, Mockito.times(1)).save(new Medication(medication));
	verify(personDAO, Mockito.times(1)).save(new Person(firstName, lastName));
	assertEquals(result, dto);
    }

    @Test
    public void updateTest_withAllergieAndMedication_unknownInDatabase() {
	// GIVEN
	when(personDAO.findByFirstNameAndLastName(firstName, lastName))
		.thenReturn(Optional.of(person));
	when(allergieDAO.findByAllergieString(allergie))
		.thenReturn(Optional.of(new Allergie(allergie)));
	when(medicationDAO.findByMedicationString(medication))
		.thenReturn(Optional.of(new Medication(medication)));
	when(personDAO.save(any(Person.class))).then(returnsFirstArg());
	// WHEN
	MedicalRecordDto result = medicalRecordService.update(dto);
	// THEN
	verify(allergieDAO, Mockito.times(1)).findByAllergieString(any(String.class));
	verify(medicationDAO, Mockito.times(1)).findByMedicationString(any(String.class));
	verify(allergieDAO, Mockito.times(0)).save(any());
	verify(medicationDAO, Mockito.times(0)).save(any());
	verify(personDAO, Mockito.times(1)).save(new Person(firstName, lastName));
	assertEquals(result, dto);
    }

    @Test
    public void updateTest_whenPersonIsUnknown() {
	Optional<Person> person = Optional.empty();
	when(personDAO.findByFirstNameAndLastName(firstName, lastName)).thenReturn(person);
	assertThrows(UnknownPersonException.class, () -> medicalRecordService.update(dto));
    }

    @Test
    public void updateTest_whenDaoReturnNull() {
	when(personDAO.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
	assertThrows(UnknownPersonException.class, () -> medicalRecordService.update(dto));
    }

}