package com.safetyalerts.api.service.ServicesTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.repository.MedicalRecordsRepository;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordsImplTest {

	@Mock
	private MedicalRecordsRepository medicalRecordsRepository;

	@Mock
	private MedicalRecordsInterface medicalRecordsInterface;

	@Mock
	private MedicalRecords medicalRecords;


	@BeforeEach
	public void setUp() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);
	}

	@Test
	public void testCreateMedicalRecords() {
		when(medicalRecordsInterface.createMedicalRecords(medicalRecords)).thenReturn(medicalRecords);
		MedicalRecords savedMedicalRecords = medicalRecordsInterface.createMedicalRecords(medicalRecords);
		verify(medicalRecordsInterface, times(1)).createMedicalRecords(savedMedicalRecords);
		assertEquals(medicalRecords, savedMedicalRecords);
	}

	@Test
	public void testGetMedicalRecords() {
		List<MedicalRecords> createdMedicalRecords = new ArrayList<MedicalRecords>();
		createdMedicalRecords.add(new MedicalRecords());
		when(medicalRecordsInterface.getMedicalRecords()).thenReturn(createdMedicalRecords);
		Iterable<MedicalRecords> result = medicalRecordsInterface.getMedicalRecords();
		assertEquals(createdMedicalRecords, result);
	}

	@Test
	public void testGetMedicalRecordsById() {
		when(medicalRecordsInterface.getMedicalRecordsById(1)).thenReturn(Optional.of(medicalRecords));
		Optional<MedicalRecords> result = medicalRecordsInterface.getMedicalRecordsById(1);
		assertTrue(result.isPresent());
		assertEquals(medicalRecords, result.get());
	}

	@Test
	public void testDeleteMedicalRecords() {
		when(medicalRecordsInterface.getMedicalRecordsById(1)).thenReturn(Optional.ofNullable(medicalRecords));
		assertAll(() -> medicalRecordsInterface.getMedicalRecordsById(1));
	}
}
