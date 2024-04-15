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
import com.safetyalerts.api.controller.MedicalRecordsController;
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;

@WebMvcTest(MedicalRecordsController.class)
public class MedicalRecordsControllerSIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MedicalRecordsInterface medicalRecordsInterface;

	private List<MedicalRecords> medicalRecordsList;

	@BeforeEach
	public void setUp() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		MedicalRecords medicalRecords2 = new MedicalRecords();
		medicalRecords2.setFirstName("John");
		medicalRecords2.setLastName("Boyd");
		medicalRecords.setBirthDate("03/06/1984");
		String[] medications2 = { "aznol:350mg", "hydrapermazol:100mg" };
		medicalRecords.setMedications(medications2);
		String[] allergies2 = { "nillacilan" };
		medicalRecords.setAllergies(allergies2);

		medicalRecordsList = Arrays.asList(medicalRecords, medicalRecords2);
	}

	@Test
	public void testGetMedicalRecords() throws Exception {

		when(medicalRecordsInterface.getMedicalRecords()).thenReturn(medicalRecordsList);

		ObjectMapper objectMapper = new ObjectMapper();

		String medicalRecordsJson = objectMapper.writeValueAsString(medicalRecordsList);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecords")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordsJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testCreateMedicalRecords() throws Exception {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		when(medicalRecordsInterface.createMedicalRecords(medicalRecords)).thenReturn(medicalRecords);

		ObjectMapper objectMapper = new ObjectMapper();

		String medicalRecordsJson = objectMapper.writeValueAsString(medicalRecords);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordsJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testUpdateMedicalRecords() throws Exception {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		medicalRecordsInterface.createMedicalRecords(medicalRecords);

		medicalRecords.setBirthDate("08/06/1982");

		MedicalRecords updatedMedicalRecords = medicalRecordsInterface.createMedicalRecords(medicalRecords);

		when(medicalRecordsInterface.createMedicalRecords(updatedMedicalRecords)).thenReturn(updatedMedicalRecords);

		when(medicalRecordsInterface.getMedicalRecordsById(1)).thenReturn(Optional.of(medicalRecords));

		ObjectMapper objectMapper = new ObjectMapper();

		String medicalRecordsJson = objectMapper.writeValueAsString(medicalRecords);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecords/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordsJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testGetMedicalRecordsById() throws Exception {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		when(medicalRecordsInterface.getMedicalRecordsById(1)).thenReturn(Optional.of(medicalRecords));

		ObjectMapper objectMapper = new ObjectMapper();

		String medicalRecordsJson = objectMapper.writeValueAsString(medicalRecords);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecords/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordsJson)).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	public void testDeleteMedicalRecords() throws Exception {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		medicalRecordsInterface.createMedicalRecords(medicalRecords);

		when(medicalRecordsInterface.getMedicalRecordsById(1)).thenReturn(Optional.of(medicalRecords));
		mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecords/Eric/Cadigan"));

		verify(medicalRecordsInterface).deleteMedicalRecords("Eric", "Cadigan");
	}
}
