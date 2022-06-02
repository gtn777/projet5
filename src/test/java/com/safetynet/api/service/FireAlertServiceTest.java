
package com.safetynet.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
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

import com.safetynet.api.dto.FireAlertDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.service.exception.UnknownAddressException;


@ExtendWith(MockitoExtension.class)
public class FireAlertServiceTest {

    @InjectMocks
    FireAlertService fireAlertService;

    @Mock
    HomeRepository homeRepository;

    private ArgumentCaptor<String> argCaptor;

    private final String address = "30 rue du Test";

    private Person person;

    private Home home;

    @BeforeAll
    @Test
    public static void fireAlertDtoConstructorTest() {
	assertTrue(new FireAlertDto().getPersons().isEmpty());
    }

    @BeforeEach
    public void beforEach() { argCaptor = ArgumentCaptor.forClass(String.class); }

    @AfterEach
    public void afterEach() {
	verify(homeRepository, Mockito.times(1)).findByAddress(argCaptor.capture());
	assertEquals(argCaptor.getValue(), address);
    }

    @Test
    public void getDataTest() {
	person = new Person("Franck", "Test");
	person.setPhone(new Phone("0658794-14"));
	Set<Person> persons = new HashSet<Person>();
	persons.add(person);
	home = new Home(address, "Paris", "75010");
	home.setStation(5);
	home.getPersons().add(person);
	when(homeRepository.findByAddress(address)).thenReturn(Optional.of(home));
	FireAlertDto result = fireAlertService.getData(address);
	assertEquals(new FireAlertDto(address, persons, 5), result);
    }

    @Test
    public void getData_daoResponseIsNullTest() {
	when(homeRepository.findByAddress(address)).thenReturn(null);
	assertThrows(UnknownAddressException.class, () -> fireAlertService.getData(address));
    }

    @Test
    public void getData_unknownAddressExceptioTest() {
	when(homeRepository.findByAddress(address)).thenReturn(Optional.empty());
	assertThrows(UnknownAddressException.class, () -> fireAlertService.getData(address));
    }

}
