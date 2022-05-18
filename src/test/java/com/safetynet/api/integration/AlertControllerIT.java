package com.safetynet.api.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class AlertControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetStationCoverageData() throws Exception {
	mockMvc.perform(get("/firestation?station=3"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.persons[0].firstName", is("Roger")))
		.andExpect(jsonPath("$.persons[7].phone", is("841-874-6512")));
    }

    @Test
    public void testGetChildAlert() throws Exception {
	mockMvc.perform(get("/childAlert?address=892 Downing Ct"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.children[0].firstName", is("Zach")))
		.andExpect(jsonPath("$.children[0].age", is(5)));
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
