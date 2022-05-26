
package com.safetynet.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.api.dto.FloodOrFireAlertPersonDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.FloodAlertService;
import com.safetynet.api.service.exception.UnknownFireStationException;


@ExtendWith(MockitoExtension.class)
public class FloodAlertServiceTest {

    @InjectMocks
    FloodAlertService floodAlertService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HomeRepository homeRepository;

    @Captor
    private ArgumentCaptor<Set<Integer>> argSetInteger;

    @Captor
    private ArgumentCaptor<Integer> argInteger;

    private Set<Integer> stations;

    private Person person1;

    private Person person2;

    @BeforeEach
    public void beforEach() {
	stations = new HashSet<Integer>();
	stations.add(5);
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
    }

    @AfterEach
    public void afterEach() {
	verify(homeRepository, Mockito.times(1)).existsByStation(argInteger.capture());
	assertEquals(argInteger.getValue(), 5);
    }

    @Test
    public void getDataTest() {
	// given
	Set<Person> persons = new HashSet<Person>();
	persons.add(person1);
	persons.add(person2);
	when(homeRepository.existsByStation(5)).thenReturn(true);
	when(personRepository.findAllByHomeStationIn(stations)).thenReturn(persons);
	// WHEN
	Map<String, Set<FloodOrFireAlertPersonDto>> result = floodAlertService
		.getData(stations);
	// THEN
	verify(personRepository, Mockito.times(1))
		.findAllByHomeStationIn(argSetInteger.capture());
	assertEquals(argSetInteger.getValue(), stations);
	assertTrue(result.containsKey("15 rue du test"));
	assertTrue(result.size() == 1);
	assertTrue(result.get("15 rue du test").size() == 2);
    }

    @Test
    public void getData_daoResponseIsNullTest() {
	when(homeRepository.existsByStation(5)).thenReturn(false);
	assertThrows(UnknownFireStationException.class,
		() -> floodAlertService.getData(stations));
	verify(personRepository, Mockito.times(0))
		.findAllByHomeStationIn(argSetInteger.capture());
    }

    @Test
    public void getData_daoReturnEmptyPersonsSet() {
	Set<Person> persons = new HashSet<Person>();
	when(homeRepository.existsByStation(5)).thenReturn(true);
	when(personRepository.findAllByHomeStationIn(stations)).thenReturn(persons);
	assertThrows(UnknownFireStationException.class,
		() -> floodAlertService.getData(stations));
	verify(personRepository, Mockito.times(1))
		.findAllByHomeStationIn(argSetInteger.capture());
    }

    @Test
    public void getData_daoReturnNullTest() {
	when(homeRepository.existsByStation(5)).thenReturn(true);
	when(personRepository.findAllByHomeStationIn(stations)).thenReturn(null);
	assertThrows(UnknownFireStationException.class,
		() -> floodAlertService.getData(stations));
	verify(personRepository, Mockito.times(1))
		.findAllByHomeStationIn(argSetInteger.capture());
    }

}
