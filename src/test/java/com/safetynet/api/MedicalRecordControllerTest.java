
package com.safetynet.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.MedicalRecordController;
import com.safetynet.api.dto.endpoints.MedicalRecordDto;
import com.safetynet.api.service.endpoint.MedicalRecordService;


@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MedicalRecordService medicalRecordService;

    private MedicalRecordDto dto;

    private String firstName = "Franck";

    private String lastName = "Test";

    private String birthDate = "20/02/2000";

    @BeforeEach
    public void beforeEach() {
	dto = new MedicalRecordDto();
	dto.setAllergies(new HashSet<String>());
	dto.setMedications(new HashSet<String>());
	dto.setFirstName(firstName);
	dto.setLastName(lastName);
	dto.setBirthdate(birthDate);
    }

    @Test
    public void postMedicalRecord() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isCreated());
    }

    @Test
    public void putMedicalRecord() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isOk());
    }

    @Test
    public void deleteMedicalRecord() throws Exception {
	mockMvc
	    .perform(MockMvcRequestBuilders
		.delete("/medicalRecord?firstName=" + firstName + ", lastName=" + lastName))
	    .andExpect(status().isOk());
    }

    @Test
    public void getAllFireStationsTest() throws Exception {
	mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk());
    }

}
