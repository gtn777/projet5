
package com.safetynet.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.safetynet.api.dto.PersonInfoDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.MedicalRecord;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.PersonInfoService;


@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {

    @InjectMocks
    private PersonInfoService personInfoService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private final String firstName = "Franck";

    private final String lastName = "Test";

    private Person person1;

    private Set<Person> personIterable;

    Set<PersonInfoDto> result;

    private ArgumentCaptor<String> argString;

    @BeforeEach
    public void beforEach() {
	person1 = new Person("Franck", "Test");
	person1.setMedicalRecord(new MedicalRecord());
	person1.getMedicalRecord().setBirthdate(LocalDate.of(1950, 05, 20));
	person1.setPhone(new Phone("3154-54"));
	person1.setHome(new Home("15 rue du test", "Paris", "75010"));
	personIterable = new HashSet<Person>();
	personIterable.add(person1);
	result = new HashSet<PersonInfoDto>();
	argString = ArgumentCaptor.forClass(String.class);
    }

    @AfterEach
    public void afterEach() {
	verify(personRepository, Mockito.times(1))
		.findAllByFirstNameAndLastName(lastName, firstName);
    }

    @Test
    void getDataTest() {
	when(personRepository.findAllByFirstNameAndLastName(firstName, lastName))
		.thenReturn(personIterable);
    }

}
