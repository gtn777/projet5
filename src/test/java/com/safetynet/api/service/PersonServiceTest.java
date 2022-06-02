
package com.safetynet.api.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.entity.Person;
import com.safetynet.api.repository.EmailRepository;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.repository.PhoneRepository;
import com.safetynet.api.service.endpoint.PersonService;
import com.safetynet.api.service.exception.DataAlreadyUpToDateException;
import com.safetynet.api.service.exception.UnknownPersonException;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    PersonService personService;

    @Mock
    PersonRepository personRepository;

    @Mock
    private HomeRepository homeRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private EmailRepository emailRepository;

    private PersonDto dto;

    private Person person;

    private String firstName = "Franck";

    private String lastName = "Test";

    private String address = "15 rue de la paix";

    private String city = "Paris";

    private String zip = "75-654";

    private String phone = "655-6545";

    private String email = "exemple]mail.com";

    @BeforeEach
    public void beforeEach() {
	dto = new PersonDto();
	dto.setFirstName(firstName);
	dto.setLastName(lastName);
	dto.setEmail(email);
	dto.setAddress(address);
	dto.setCity(city);
	dto.setPhone(phone);
	dto.setZip(zip);
	person = new Person(dto);
	dto = new PersonDto(person);
    }

    @Test
    public void updateTest_whenDataIsAlreadyUpToDate() {
	when(personRepository.findByFirstNameAndLastName(firstName, lastName))
	    .thenReturn(Optional.of(person));
	assertThrows(DataAlreadyUpToDateException.class, () -> personService.update(dto));
    }

    @Test
    public void updateTest_whenPersonIsUnknown() {
	when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.empty());
	assertThrows(UnknownPersonException.class, () -> personService.update(dto));
    }

    @Test
    public void deleteTest() {
	// GIVEN
	when(personRepository.findByFirstNameAndLastName(firstName, lastName))
	    .thenReturn(Optional.of(person));
	when(personRepository.deleteByFirstNameAndLastName(firstName, lastName)).thenReturn(person);
	// WHEN
	PersonDto result = personService.deletePerson(firstName, lastName);
	// THEN
	verify(personRepository, Mockito.times(1)).deleteByFirstNameAndLastName(firstName, lastName);
	verify(personRepository, Mockito.times(1)).findByFirstNameAndLastName(firstName, lastName);
	assertTrue(result.getAddress() == address);
    }

    @Test
    public void deleteTest_whenPersonIsUnknown() {
	// GIVEN
	when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.empty());
	assertThrows(UnknownPersonException.class, () -> personService.deletePerson(firstName, lastName));
	verify(personRepository, Mockito.times(1)).findByFirstNameAndLastName(firstName, lastName);
    }

}
