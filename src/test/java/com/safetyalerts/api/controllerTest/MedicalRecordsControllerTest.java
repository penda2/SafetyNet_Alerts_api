package com.safetyalerts.api.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetyalerts.api.controller.MedicalRecordsController;
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MedicalRecordsControllerTest {

	@Mock
	private MedicalRecordsInterface medicalRecordsInterface;

	@InjectMocks
	private MedicalRecordsController medicalRecordsController;

	@Mock
	private Logger logger;

	private List<MedicalRecords> medicalRecordsList;

	@BeforeEach
	public void seUp() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = {"tradoxidine:400mg"};
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		MedicalRecords medicalRecords2 = new MedicalRecords();
		medicalRecords.setId(2);
		medicalRecords.setFirstName("John");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("02/09/2010");
		String[] medications2 = {"tradoxidine:200mg"};
		medicalRecords.setMedications(medications2);
		String[] allergies2 = {};
		medicalRecords.setAllergies(allergies2);

		medicalRecordsList = Arrays.asList(medicalRecords, medicalRecords2);
	}

	@Test
	public void testGetMedicalRecords() {
		when(medicalRecordsInterface.getMedicalRecords()).thenReturn(medicalRecordsList);
		Iterable<MedicalRecords> result = medicalRecordsController.getMedicalRecords();
		assertEquals(medicalRecordsList.size(), ((List<MedicalRecords>) result).size());
	}

	@Test
	public void testGetMedicalRecordsById() {
		int medicalRecordsId = 1;
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = {"tradoxidine:400mg"};
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		when(medicalRecordsInterface.getMedicalRecordsById(medicalRecordsId)).thenReturn(Optional.of(medicalRecords));
		MedicalRecords result = medicalRecordsController.getMedicalRecordsById(medicalRecordsId);

		assertEquals(medicalRecords, result);
		verify(medicalRecordsInterface).getMedicalRecordsById(medicalRecordsId);
	}

	@Test
	public void testGetMedicalRecordsByIdReturnNull() {
		int medicalRecordsId = 1;
		when(medicalRecordsInterface.getMedicalRecordsById(medicalRecordsId)).thenReturn(Optional.empty());

		MedicalRecords result = medicalRecordsController.getMedicalRecordsById(medicalRecordsId);

		assertNull(result);
		verify(medicalRecordsInterface).getMedicalRecordsById(medicalRecordsId);
	}

	@Test
	public void testCreateMedicalRecords() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		when(medicalRecordsInterface.createMedicalRecords(medicalRecords)).thenReturn(medicalRecords);

		MedicalRecords result = medicalRecordsController.createMedicalRecords(medicalRecords);
		assertEquals(medicalRecords, result);
		verify(logger).info("Received MedicalRecords object: {}", medicalRecords);
	}

	@Test
	public void testUpdateMedicalRecords() {
		int medicalRecordsId = 1;
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = {"tradoxidine:400mg"};
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		MedicalRecords updatedmedicalRecords = new MedicalRecords();
		updatedmedicalRecords.setId(1);
		updatedmedicalRecords.setFirstName("Eric");
		updatedmedicalRecords.setLastName("Cadigan");
		updatedmedicalRecords.setBirthDate("08/06/1945");
		String[] medications1 = {"tradoxidine:400mg"};
		updatedmedicalRecords.setMedications(medications1);
		String[] allergies1 = {};
		updatedmedicalRecords.setAllergies(allergies1);

		when(medicalRecordsInterface.getMedicalRecordsById(medicalRecordsId)).thenReturn(Optional.of(medicalRecords));
		when(medicalRecordsInterface.createMedicalRecords(updatedmedicalRecords)).thenReturn(updatedmedicalRecords);
		MedicalRecords result = medicalRecordsController.updateMedicalRecords(medicalRecordsId, updatedmedicalRecords);

		assertEquals(updatedmedicalRecords, result);
		verify(medicalRecordsInterface).getMedicalRecordsById(medicalRecordsId);
		verify(medicalRecordsInterface).createMedicalRecords(updatedmedicalRecords);
	}

	@Test
	public void testMedicalRecordsToUpdateNotFound() {
		int medicalRecordsId = 1;
		MedicalRecords updatedmedicalRecords = new MedicalRecords();
		updatedmedicalRecords.setId(1);
		updatedmedicalRecords.setFirstName("Eric");
		updatedmedicalRecords.setLastName("Cadigan");
		updatedmedicalRecords.setBirthDate("08/06/1945");
		String[] medications1 = {"tradoxidine:400mg"};
		updatedmedicalRecords.setMedications(medications1);
		String[] allergies1 = {};
		updatedmedicalRecords.setAllergies(allergies1);

		when(medicalRecordsInterface.getMedicalRecordsById(medicalRecordsId)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			medicalRecordsController.updateMedicalRecords(medicalRecordsId, updatedmedicalRecords);
		});

		verify(medicalRecordsInterface).getMedicalRecordsById(medicalRecordsId);

		verify(medicalRecordsInterface, never()).createMedicalRecords(any());
	}

	@Test
	public void testDeleteMedicalRecords() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = {"tradoxidine:400mg"};
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		medicalRecordsController.deleteMedicalrecords("Eric", "Cadigan");
		verify(medicalRecordsInterface, times(1)).deleteMedicalRecords("Eric", "Cadigan");
	}
}
