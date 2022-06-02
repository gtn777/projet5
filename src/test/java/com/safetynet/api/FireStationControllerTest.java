
package com.safetynet.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.FireStationController;
import com.safetynet.api.dto.endpoints.FireStationDto;
import com.safetynet.api.service.endpoint.FireStationService;


@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FireStationService fireStationService;

    @Test
    public void postFireStation() throws Exception {
	FireStationDto body = new FireStationDto("rue de la paix", 5);
	mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
		.contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(body))).andExpect(status().isCreated());
    }

    @Test
    public void putFireStation() throws Exception {
	FireStationDto body = new FireStationDto("rue de la paix", 5);
	mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
		.contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    public void deleteFireStation() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.delete("/firestation?address=exemple")).andExpect(status().isOk());
	mockMvc.perform(MockMvcRequestBuilders.delete("/firestation?station=50")).andExpect(status().isOk());
    }

    @Test
    public void deleteFireStationBadParameter() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.delete("/firestation?adress=exemple")).andExpect(status().isBadRequest());
	mockMvc.perform(MockMvcRequestBuilders.delete("/firestation?staion=1")).andExpect(status().isBadRequest());
	mockMvc.perform(MockMvcRequestBuilders.delete("/firestation?station=erreur")).andExpect(status().isBadRequest());
	mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")).andExpect(status().isBadRequest());
	mockMvc.perform(MockMvcRequestBuilders.delete("/firestation?")).andExpect(status().isBadRequest());
    }

    @Test
    public void getAllFireStationsTest() throws Exception { mockMvc.perform(get("/firestationAll")).andExpect(status().isOk()); }

}
