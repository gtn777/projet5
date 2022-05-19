
package com.safetynet.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.dto.endpoints.FireStationDto;
import com.safetynet.api.dto.endpoints.JsonFileFirestationsDto;
import com.safetynet.api.entity.Home;
import com.safetynet.api.repository.HomeRepository;
import com.safetynet.api.service.endpoint.FireStationService;
import com.safetynet.api.service.exception.DataAlreadyCreatedException;
import com.safetynet.api.service.exception.UnknownAddressException;


@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private HomeRepository homeRepository;

    @InjectMocks
    private FireStationService fireStationService;

    private Home home = new Home();

    private FireStationDto fireStationDto = new FireStationDto();

    private String address = "address for test";

    private String city = "Paris";

    private String zip = "75001";

    private int station = 15;

    @BeforeEach
    public void setUp() {
	home = new Home(address, city, zip);
	home.setStation(station);
	fireStationDto = new FireStationDto(address, station);
    }

    @Test
    public void createTest() {
	// GIVEN
	fireStationDto.setStation(4);
	Home updatedHome = new Home(address, city, zip);
	updatedHome.setStation(4);
	when(homeRepository.findByAddress(address)).thenReturn(Optional.of(home));
	when(homeRepository.save(updatedHome)).thenReturn(updatedHome);
	// WHEN
	FireStationDto result = fireStationService.create(fireStationDto);
	// THEN
	verify(homeRepository, Mockito.times(1)).findByAddress(address);
	verify(homeRepository, Mockito.times(1)).save(home);
	assertEquals(result, new FireStationDto(address, 4));
    }

    @Test
    public void create_unknownAddressExceptionTest() {
	// GIVEN
	when(homeRepository.findByAddress(address)).thenReturn(Optional.empty());
	// WHEN
	Exception exception = assertThrows(UnknownAddressException.class,
		() -> fireStationService.create(fireStationDto));
	// THEN
	verify(homeRepository, Mockito.times(1)).findByAddress(address);
	verify(homeRepository, Mockito.times(0)).save(Mockito.any());
	assertTrue(exception.getMessage().contains("No data available"));
    }

    @Test
    public void create_dataAlreadyCreatedExceptionTest() {
	// GIVEN
	when(homeRepository.findByAddress(address)).thenReturn(Optional.of(home));
	// WHEN
	Exception exception = assertThrows(DataAlreadyCreatedException.class,
		() -> fireStationService.create(fireStationDto));
	// THEN
	verify(homeRepository, Mockito.times(1)).findByAddress(address);
	verify(homeRepository, Mockito.times(0)).save(Mockito.any());
	assertTrue(exception.getMessage().contains("already created"));
    }

    @Test
    public void deleteAddressMappingTest() {
	// GIVEN
	Home updatedHome = new Home(address, city, zip);
	updatedHome.setStation(-1);
	when(homeRepository.findByAddress(address)).thenReturn(Optional.of(home));
	when(homeRepository.save(updatedHome)).thenReturn(updatedHome);
	// WHEN
	String result = fireStationService.deleteAddressMapping(address);
	// THEN
	verify(homeRepository, Mockito.times(1)).findByAddress(address);
	verify(homeRepository, Mockito.times(1)).save(home);
	assertEquals(true, result.contains("is no longer covered"));
	assertEquals(true, result.contains(address));
    }

    @Test
    public void deleteAddressMapping_unknownAddressExceptionTest() {
	// GIVEN
	when(homeRepository.findByAddress(address)).thenReturn(Optional.empty());
	// WHEN
	Exception exception = assertThrows(UnknownAddressException.class,
		() -> fireStationService.deleteAddressMapping(address));
	// THEN
	verify(homeRepository, Mockito.times(1)).findByAddress(address);
	verify(homeRepository, Mockito.times(0)).save(Mockito.any());
	assertTrue(exception.getMessage().contains("No data available"));
    }

    @Test
    public void createAllFromJsonTest() {
	// GIVEN
	JsonFileFirestationsDto firestationsDto = new JsonFileFirestationsDto();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    firestationsDto = mapper.readValue(new File("src/main/resources/data.json"),
		    JsonFileFirestationsDto.class);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// WHEN
	Iterable<FireStationDto> result = fireStationService
		.createAllFromJson(firestationsDto);
	// THEN
	assertThat(result).isInstanceOf(Set.class);
	
	
    }

}
