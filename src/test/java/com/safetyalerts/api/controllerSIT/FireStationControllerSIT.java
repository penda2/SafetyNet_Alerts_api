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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyalerts.api.controller.FireStationController;
import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.service.Interfaces.FireStationInterface;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

@WebMvcTest(FireStationController.class)

public class FireStationControllerSIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FireStationInterface fireStationInterface;

	@MockBean
	private PersonServiceInterface personServiceInterface;

	@MockBean
	private MedicalRecordsInterface medicalRecordsInterface;

	private List<Person> persons;

	private List<FireStation> fireStationList;

	@BeforeEach
	public void seUp() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		FireStation fireStation2 = new FireStation();
		fireStation2.setAddress("1509 Culver St");
		fireStation2.setStation(3L);

		fireStationList = Arrays.asList(fireStation, fireStation2);

		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1980");
		String[] medications = { "Autre:300mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		MedicalRecords medicalRecords2 = new MedicalRecords();
		medicalRecords2.setFirstName("John");
		medicalRecords2.setLastName("Cadigan");
		medicalRecords2.setBirthDate("08/06/2010");
		String[] medication = { "tradoxidine:400mg" };
		medicalRecords2.setMedications(medication);
		String[] allergie = {};
		medicalRecords2.setAllergies(allergie);

		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");
		person.setMedicalRecords(medicalRecords);

		Person person2 = new Person();
		person2.setFirstName("John");
		person2.setLastName("Cadigan");
		person2.setAddress("951 LoneTree Rd");
		person2.setCity("Culver");
		person2.setZip(97451L);
		person2.setPhone("841-874-7468");
		person2.setEmail("jhons@email.com");
		person2.setMedicalRecords(medicalRecords2);

		persons = Arrays.asList(person, person2);
	}

	@Test
	public void testGetFireStations() throws Exception {

		when(fireStationInterface.getFireStations()).thenReturn(fireStationList);

		ObjectMapper objectMapper = new ObjectMapper();

		String fireStationJson = objectMapper.writeValueAsString(fireStationList);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/firestation/read")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(fireStationJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testCreateFireStations() throws Exception {
		FireStation fireStation = new FireStation();
		fireStation.setFirestationId(1);
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation(3L);

		when(fireStationInterface.createFireStation(fireStation)).thenReturn(fireStation);

		ObjectMapper objectMapper = new ObjectMapper();

		String fireStationJson = objectMapper.writeValueAsString(fireStation);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/firestation/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(fireStationJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testUpdateFireStation() throws Exception {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		fireStationInterface.createFireStation(fireStation);

		fireStation.setAddress("29 15th St");

		FireStation updatedFireStation = fireStationInterface.createFireStation(fireStation);

		when(fireStationInterface.createFireStation(updatedFireStation)).thenReturn(updatedFireStation);

		when(fireStationInterface.getFireStationById(1)).thenReturn(Optional.of(fireStation));

		ObjectMapper objectMapper = new ObjectMapper();

		String fireStationJson = objectMapper.writeValueAsString(fireStation);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/firestation/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(fireStationJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testGetFireStationById() throws Exception {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		when(fireStationInterface.getFireStationById(1)).thenReturn(Optional.of(fireStation));

		ObjectMapper objectMapper = new ObjectMapper();

		String fireStationJson = objectMapper.writeValueAsString(fireStation);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/firestation/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(fireStationJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testDeleteFireStation() throws Exception {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);
		fireStationInterface.createFireStation(fireStation);

		when(fireStationInterface.getFireStationById(1)).thenReturn(Optional.of(fireStation));

		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/1"));

		verify(fireStationInterface).deleteFireStation(1);
	}

	@Test
	public void testGetPersonsWithMedicalRecords() throws Exception {

		when(fireStationInterface.getPersonsByStationsNumber(2L)).thenReturn(persons);

		mockMvc.perform(MockMvcRequestBuilders.get("/firestationNumber/2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.persons", hasSize(2))).andExpect(jsonPath("$.numberOfAdults", is(1)))
				.andExpect(jsonPath("$.numberOfChildren", is(1)))
				.andExpect(jsonPath("$.persons[0].firstName", is("Eric")))
				.andExpect(jsonPath("$.persons[0].lastName", is("Cadigan")));

		verify(fireStationInterface).getPersonsByStationsNumber(2L);
	}
}
