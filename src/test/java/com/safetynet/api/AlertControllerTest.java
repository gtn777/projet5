package com.safetynet.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.api.controller.AlertController;
import com.safetynet.api.repository.PersonRepository;
import com.safetynet.api.service.ChildAlertService;
import com.safetynet.api.service.CommunityEmailService;
import com.safetynet.api.service.FireAlertService;
import com.safetynet.api.service.FireStationCoverageService;
import com.safetynet.api.service.FloodAlertService;
import com.safetynet.api.service.PersonInfoService;
import com.safetynet.api.service.PhoneAlertService;

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
    public void testGetStationCoverageData() throws Exception {
	mockMvc.perform(get("/firestation?station=3")).andExpect(status().isOk());
    }
    @Test
    public void testGetStationCoverageDataNotFound() throws Exception {
	mockMvc.perform(get("/firestation?station=99")).andExpect(status().isNotFound());
    }

    @Test
    public void testGetChildAlert() throws Exception {
	mockMvc.perform(get("/childAlert?address=\"892 Downing Ct\"")).andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneAlert() throws Exception {
	mockMvc.perform(get("/phoneAlert?firestation=2")).andExpect(status().isOk());
    }

    @Test
    public void testGetCommunityEmail() throws Exception {
	mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfo() throws Exception {
	mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd")).andExpect(status().isOk());
    }

    @Test
    public void testGetFloodAlert() throws Exception {
	mockMvc.perform(get("/flood?stations=1,2,3,4")).andExpect(status().isOk());
    }

    @Test
    public void testGetFireAlert() throws Exception {
	mockMvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk());
    }

}
