
package com.safetynet.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.PersonController;
import com.safetynet.api.dto.endpoints.PersonDto;
import com.safetynet.api.entity.Email;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.Person;
import com.safetynet.api.entity.Phone;
import com.safetynet.api.service.endpoint.PersonService;


@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    private PersonDto dto;

    private Person person;

    private Home home;

    private String firstName = "Franck";

    private String lastName = "Test";

    private String address = "15 rue de la paix";

    private String city = "Paris";

    private String zip = "75-654";

    private String phone = "655-6545";

    private String email = "exemple]mail.com";

    @BeforeEach
    public void beforeEach() {
	person = new Person(firstName, lastName);
	home = new Home(address, city, zip);
	person.setHome(home);
	person.setEmail(new Email(email));
	person.setPhone(new Phone(phone));
	dto = new PersonDto(person);
    }

    @Test
    public void postPersonTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.post("/person")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isCreated());
    }

    @Test
    public void putPersonTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.put("/person")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(new ObjectMapper().writeValueAsString(dto))).andExpect(status().isOk());
    }

    @Test
    public void deletePersonTest() throws Exception {
	mockMvc
	    .perform(
		MockMvcRequestBuilders.delete("/person?firstName=" + firstName + ", lastName=" + lastName))
	    .andExpect(status().isOk());
    }

    @Test
    public void getAllPersonTest() throws Exception {
	mockMvc.perform(get("/person")).andExpect(status().isOk());
    }

}
