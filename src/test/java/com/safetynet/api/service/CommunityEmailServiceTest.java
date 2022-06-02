
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

import com.safetynet.api.entity.Email;
import com.safetynet.api.repository.EmailRepository;
import com.safetynet.api.service.exception.UnknownCityException;


@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {

    @InjectMocks
    private CommunityEmailService communityEmailService;

    @Mock
    private EmailRepository emailRepository;

    private String city = "Paris";

    private ArgumentCaptor<String> argStringCaptor;

    @BeforeEach
    public void beforeEach() { argStringCaptor = ArgumentCaptor.forClass(String.class); }

    @AfterEach
    public void afterEach() {
	verify(emailRepository, Mockito.times(1)).findAllByPersonsHomeCity(argStringCaptor.capture());
	assertEquals(argStringCaptor.getValue(), city);
    }

    @Test
    public void getDataTest() {
	// given
	Set<Email> emailIterable = new HashSet<Email>();
	Set<String> expectedresult = new HashSet<String>();
	expectedresult.add("essai@mail.com");
	emailIterable.add(new Email("essai@mail.com"));
	when(emailRepository.findAllByPersonsHomeCity(city)).thenReturn(emailIterable);
	// WHEN
	Object result = communityEmailService.getData(city);
	// THEN
	assertEquals(result, expectedresult);
    }

    @Test
    public void getData_daoResponseIsNullTest() {
	// given
	when(emailRepository.findAllByPersonsHomeCity(city)).thenReturn(null);
	// WHEN
	assertThrows(UnknownCityException.class, () -> communityEmailService.getData(city));
    }

    @Test
    public void getData_unknownCityTest() {
	// given
	Set<Email> emailIterable = new HashSet<Email>();
	when(emailRepository.findAllByPersonsHomeCity(city)).thenReturn(emailIterable);
	// WHEN
	assertThrows(UnknownCityException.class, () -> communityEmailService.getData(city));
    }

}
