
package com.safetynet.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.safetynet.api.dto.PhoneAlertDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.exception.UnknownFireStationException;


@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceTest {

    @InjectMocks
    PhoneAlertService phoneAlertService;

    @Mock
    PersonRepository personRepository;

    private final int station = 5;;

    private Set<Person> persons;

    private Person person1;

    private Person person2;

    private ArgumentCaptor<Integer> argCaptor;

    @BeforeEach
    public void beforeEach() {
	persons = new HashSet<Person>();
	person1 = new Person("Franck", "Test");
	person2 = new Person("Bob", "Check");
	person1.setPhone(new Phone("3154-54"));
	person2.setPhone(new Phone("9878-65"));
	persons.add(person1);
	persons.add(person2);
	argCaptor = ArgumentCaptor.forClass(Integer.class);
    }

    @AfterEach
    public void afterEach() {
	verify(personRepository, Mockito.times(1)).findAllByHomeStation(argCaptor.capture());
	assertEquals(argCaptor.getValue(), station);
    }

    @Test
    public void getDataTest() {
	PhoneAlertDto expectedResult = new PhoneAlertDto(persons);
	when(personRepository.findAllByHomeStation(station)).thenReturn(persons);
	assertEquals(expectedResult, phoneAlertService.getData(station));
    }

    @Test
    public void getData_daoResponseIsNullTest() {
	when(personRepository.findAllByHomeStation(station)).thenReturn(null);
	assertThrows(UnknownFireStationException.class, () -> phoneAlertService.getData(station));
    }

    @Test
    public void getData_unknownFireStationExceptioTest() {
	Set<Person> persons = new HashSet<Person>();
	when(personRepository.findAllByHomeStation(station)).thenReturn(persons);
	assertThrows(UnknownFireStationException.class, () -> phoneAlertService.getData(station));
    }

}
