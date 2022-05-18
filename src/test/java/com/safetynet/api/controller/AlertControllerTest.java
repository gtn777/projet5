package com.safetynet.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.api.controller.AlertController;
import com.safetynet.api.service.ChildAlertService;
import com.safetynet.api.service.CommunityEmailService;
import com.safetynet.api.service.FireAlertService;
import com.safetynet.api.service.FireStationCoverageService;
import com.safetynet.api.service.FloodAlertService;
import com.safetynet.api.service.PersonInfoService;
import com.safetynet.api.service.PhoneAlertService;
import com.safetynet.api.service.exception.UnknownAddressException;
import com.safetynet.api.service.exception.UnknownCityException;
import com.safetynet.api.service.exception.UnknownFireStationException;
import com.safetynet.api.service.exception.UnknownPersonException;


@WebMvcTest(controllers = AlertController.class)
public class AlertControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PhoneAlertService phoneAlertService;

    @MockBean
    CommunityEmailService communityEmailService;

    @MockBean
    PersonInfoService personInfoService;

    @MockBean
    FloodAlertService floodAlertService;

    @MockBean
    FireAlertService fireAlertService;

    @MockBean
    ChildAlertService childAlertService;

    @MockBean
    FireStationCoverageService fireStationCoverageService;

    @BeforeEach
    public void doBeforeEachTest() {}

    @Test
    public void getStationCoverageTest() throws Exception {
	mockMvc.perform(get("/firestation?station=3")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getChildAlertTest() throws Exception {
	mockMvc.perform(get("/childAlert?address=\"892 Downing Ct\"")).andExpect(status().isOk());
    }

    @Test
    public void getPhoneAlertTest() throws Exception {
	mockMvc.perform(get("/phoneAlert?firestation=2")).andExpect(status().isOk());
    }

    @Test
    public void getCommunityEmailTest() throws Exception {
	mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
    }

    @Test
    public void getPersonInfoTest() throws Exception {
	mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd")).andExpect(status().isOk());
    }

    @Test
    public void getFloodAlertTest() throws Exception {
	mockMvc.perform(get("/flood?stations=1,2,3,4")).andExpect(status().isOk());
    }

    @Test
    public void GetFireAlertTest() throws Exception {
	mockMvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk());
    }

    @Test
    public void unknownFireStationExceptionHandlerTest() throws Exception {
	when(fireStationCoverageService.getData(99)).thenThrow(new UnknownFireStationException(99));
	mockMvc.perform(get("/firestation?station=99")).andExpect(status().isNotFound());
    }

    @Test
    public void unknownAddressExceptionHandlerTest() throws Exception {
	when(fireAlertService.getData("1509 Culver S")).thenThrow(new UnknownAddressException("1509 Culver S"));
	mockMvc.perform(get("/fire?address=1509 Culver S")).andExpect(status().isNotFound());
    }

    @Test
    public void unknownCityExceptionHandlerTest() throws Exception {
	when(communityEmailService.getData("Paris")).thenThrow(new UnknownCityException("Paris"));
	mockMvc.perform(get("/communityEmail?city=Paris")).andExpect(status().isNotFound());
    }

    @Test
    public void unknownPersonExceptionHandlerTest() throws Exception {
	when(personInfoService.getPersonInfo("Joh", "Boyd")).thenThrow(new UnknownPersonException("Joh", "Boyd"));
	mockMvc.perform(get("/personInfo?firstName=Joh&lastName=Boyd")).andExpect(status().isNotFound());
    }

    @Test
    public void numberFormatExceptionHandlerTest() throws Exception {
	when(phoneAlertService.getData(2)).thenThrow(new NumberFormatException("test"));
	mockMvc.perform(get("/phoneAlert?firestation=2")).andExpect(status().isBadRequest());
    }

    @Test
    public void missingServletRequestParameterExceptionHandlerTest() throws Exception {
	mockMvc.perform(get("/communityEmail")).andExpect(status().isBadRequest());
    }

//    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handleMissingServletRequestParameterException(Exception exception) {
//	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//		.body(exception.getMessage() + "\n" + "Exception: " + exception.getClass().getName());
//    }

}
