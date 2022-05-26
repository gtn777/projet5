
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.ChildAlertService;
import com.safetynet.api.service.exception.UnknownAddressException;


@ExtendWith(MockitoExtension.class)
public class ChildAlertServiceTest {

    @InjectMocks
    private ChildAlertService childAlertService;

    @Mock
    private PersonRepository personRepository;

    private ArgumentCaptor<String> argCaptor;

    private final String address = "30 rue du Test";

    private Person person1;

    private Person person2;

    @BeforeEach
    public void beforEach() {
	person1 = new Person("Franck", "Test");
	person1.setMedicalRecord(new MedicalRecord());
	person1.getMedicalRecord().setBirthdate(LocalDate.of(1950, 05, 20));
	person2 = new Person("Bob", "Child");
	person2.setMedicalRecord(new MedicalRecord());
	person2.getMedicalRecord().setBirthdate(LocalDate.of(2010, 12, 19));
	argCaptor = ArgumentCaptor.forClass(String.class);
    }

    @AfterEach
    public void afterEach() {
	verify(personRepository, Mockito.times(1))
		.findAllByHomeAddress(argCaptor.capture());
	assertEquals(argCaptor.getValue(), address);
    }

    @Test
    public void getDataTest() {
	// given
	Set<Person> persons = new HashSet<Person>();
	persons.add(person1);
	persons.add(person2);
	when(personRepository.findAllByHomeAddress(address)).thenReturn(persons);
	// WHEN
	ChildAlertDto result = childAlertService.getData(address);
	// THEN
	assertEquals(result, new ChildAlertDto(persons));
	assertTrue(result.getAdults()
		.contains(person1.getFirstName() + " " + person1.getLastName()));
	assertTrue(result.getChildren()
		.iterator()
		.next()
		.containsValue(person2.getFirstName()));
    }

    @Test
    public void getData_unknownAddressTest() {
	Set<Person> persons = new HashSet<Person>();
	when(personRepository.findAllByHomeAddress(address)).thenReturn(persons);
	assertThrows(UnknownAddressException.class,
		() -> childAlertService.getData(address));
    }

    @Test
    public void getData_daoResponseIsNullTest() {
	when(personRepository.findAllByHomeAddress(address)).thenReturn(null);
	assertThrows(UnknownAddressException.class,
		() -> childAlertService.getData(address));
    }

}
