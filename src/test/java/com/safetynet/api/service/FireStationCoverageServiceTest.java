
package com.safetynet.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.api.dto.FireStationCoverageDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownFireStationException;


@ExtendWith(MockitoExtension.class)
public class FireStationCoverageServiceTest {

    @InjectMocks
    FireStationCoverageService fireStationCoverageService;

    @Mock
    private PersonRepository personRepository;

    private ArgumentCaptor<Integer> argCaptor;

    private final int station = 5;

    private Person person1;

    private Person person2;

    @BeforeAll
    @Test
    public static void fireStationCoverageDtoConstructorTest() {
	assertTrue(new FireStationCoverageDto().getPersons().isEmpty());
    }

    @BeforeEach
    public void beforEach() {
	person1 = new Person("Franck", "Test");
	person1.setMedicalRecord(new MedicalRecord());
	person1.getMedicalRecord().setBirthdate(LocalDate.of(1950, 05, 20));
	person2 = new Person("Bob", "Child");
	person2.setMedicalRecord(new MedicalRecord());
	person2.getMedicalRecord().setBirthdate(LocalDate.of(2010, 12, 19));
	person1.setPhone(new Phone("3154-54"));
	person2.setPhone(new Phone("9878-65"));
	person1.setHome(new Home("15 rue du test", "Paris", "75010"));
	person2.setHome(new Home("15 rue du test", "Paris", "75010"));
	argCaptor = ArgumentCaptor.forClass(Integer.class);
    }

    @AfterEach
    public void afterEach() {
	verify(personRepository, Mockito.times(1)).findAllByHomeStation(argCaptor.capture());
	assertEquals(argCaptor.getValue(), station);
    }

    @Test
    public void getDataTest() {
	// given
	Set<Person> persons = new HashSet<Person>();
	persons.add(person1);
	persons.add(person2);
	when(personRepository.findAllByHomeStation(station)).thenReturn(persons);
	// WHEN
	FireStationCoverageDto result = fireStationCoverageService.getData(station);
	// THEN
	assertEquals(result, new FireStationCoverageDto(persons));
	assertEquals(1, result.getAdults());
	assertEquals(1, result.getChildren());
	assertEquals(new FireStationCoverageDto(persons), result);
    }

    @Test
    public void getData_daoResponseIsNullTest() {
	when(personRepository.findAllByHomeStation(station)).thenReturn(null);
	assertThrows(UnknownFireStationException.class, () -> fireStationCoverageService.getData(station));
    }

    @Test
    public void getData_unknownFireStationExceptioTest() {
	Set<Person> persons = new HashSet<Person>();
	when(personRepository.findAllByHomeStation(station)).thenReturn(persons);
	assertThrows(UnknownFireStationException.class, () -> fireStationCoverageService.getData(station));
    }

}
