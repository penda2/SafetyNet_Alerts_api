package com.safetyalerts.api.controllerSIT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyalerts.api.controller.PersonController;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

@WebMvcTest(PersonController.class)
public class PersonControllerSIT {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonServiceInterface personServiceInterface;

	private List<Person> persons;

	@BeforeEach
	public void setUp() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		Person person2 = new Person();
		person2.setFirstName("John");
		person2.setLastName("Cadigan");
		person2.setAddress("951 LoneTree Rd");
		person2.setCity("Culver");
		person2.setZip(97451L);
		person2.setPhone("841-874-7468");
		person2.setEmail("jhons@email.com");

		persons = Arrays.asList(person, person2);
	}

	@Test
	public void testGetPersons() throws Exception {

		when(personServiceInterface.getPersons()).thenReturn(persons);

		ObjectMapper objectMapper = new ObjectMapper();

		String personJson = objectMapper.writeValueAsString(persons);
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/person").contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
				.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testCreatePerson() throws Exception {
		Person person = new Person();
		person.setFirstName("Felicia");
		person.setLastName("Boyd");
		person.setAddress("1509 Culver St");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-6544");
		person.setEmail("jaboyd@email.com");
		
		when(personServiceInterface.createPerson(person)).thenReturn(person);

		ObjectMapper objectMapper = new ObjectMapper();

		String personJson = objectMapper.writeValueAsString(person);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testUpdatePerson() throws Exception {
		Person person = new Person();
		person.setFirstName("Felicia");
		person.setLastName("Boyd");
		person.setAddress("1509 Culver St");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-6544");
		person.setEmail("jaboyd@email.com");

		personServiceInterface.createPerson(person);

		person.setAddress("29 15th St");

		Person updatedPerson = personServiceInterface.createPerson(person);

		when(personServiceInterface.createPerson(updatedPerson)).thenReturn(updatedPerson);

		when(personServiceInterface.getPersonById(1)).thenReturn(Optional.of(person));

		ObjectMapper objectMapper = new ObjectMapper();

		String personJson = objectMapper.writeValueAsString(person);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/person/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testGetPersonById() throws Exception {
		Person person = new Person();
		person.setFirstName("Felicia");
		person.setLastName("Boyd");
		person.setAddress("1509 Culver St");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-6544");
		person.setEmail("jaboyd@email.com");

		when(personServiceInterface.getPersonById(1)).thenReturn(Optional.of(person));

		ObjectMapper objectMapper = new ObjectMapper();

		String personJson = objectMapper.writeValueAsString(person);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/person/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testDeletePerson() throws Exception {
		Person person = new Person();
		person.setFirstName("Felicia");
		person.setLastName("Boyd");
		person.setAddress("1509 Culver St");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-6544");
		person.setEmail("jaboyd@email.com");
		personServiceInterface.createPerson(person);

		when(personServiceInterface.getPersonById(1)).thenReturn(Optional.of(person));

		mockMvc.perform(MockMvcRequestBuilders.delete("/person/Felicia/Boyd"));

		verify(personServiceInterface).deletePerson("Felicia", "Boyd");
	}
}
