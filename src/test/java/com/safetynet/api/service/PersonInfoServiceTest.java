
package com.safetynet.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.api.dto.PersonInfoDto;
import com.safetynet.api.entity.Email;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownPersonException;


@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {

    @InjectMocks
    private PersonInfoService personInfoService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private final String firstName = "Franck";

    private ArgumentCaptor<String> argCaptor;

    private final String lastName = "Test";

    private Set<Person> persons;

    private Person person1;

    @BeforeEach
    public void beforEach() {
	person1 = new Person("Franck", "Test");
	person1.setMedicalRecord(new MedicalRecord());
	person1.getMedicalRecord().setBirthdate(LocalDate.of(1950, 05, 20));
	person1.setPhone(new Phone("3154-54"));
	person1.setEmail(new Email("essai@mail.com"));
	person1.setHome(new Home("15 rue du test", "Paris", "75010"));
	persons = new HashSet<Person>();
	persons.add(person1);
	argCaptor = ArgumentCaptor.forClass(String.class);
    }

    @AfterEach
    public void afterEach() {
	Set<String> expectedArgs = new HashSet<String>();
	expectedArgs.add(firstName);
	expectedArgs.add(lastName);
	verify(personRepository, Mockito.times(1)).findAllByFirstNameAndLastName(argCaptor.capture(),
	    argCaptor.capture());
	assertTrue(argCaptor.getAllValues().containsAll(expectedArgs));
    }

    @Test
    void getDataTest() {
	when(personRepository.findAllByFirstNameAndLastName(firstName, lastName)).thenReturn(persons);
	when(medicalRecordRepository.findByPersonFirstNameAndPersonLastName(firstName, lastName))
	    .thenReturn(Optional.of(person1.getMedicalRecord()));
	// WHEN
	Set<PersonInfoDto> result = personInfoService.getData(firstName, lastName);
	// THEN
	assertEquals(result.size(), 1);
	assertEquals(result.iterator().next(), new PersonInfoDto(person1, person1.getMedicalRecord()));
    }

    @Test
    public void getData_daoResponseIsNullTest() {
	when(personRepository.findAllByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
	assertThrows(UnknownPersonException.class, () -> personInfoService.getData(firstName, lastName));
    }

    @Test
    public void getData_unknownFireStationExceptioTest() {
	Set<Person> persons = new HashSet<Person>();
	when(personRepository.findAllByFirstNameAndLastName(firstName, lastName)).thenReturn(persons);
	assertThrows(UnknownPersonException.class, () -> personInfoService.getData(firstName, lastName));
    }

}
