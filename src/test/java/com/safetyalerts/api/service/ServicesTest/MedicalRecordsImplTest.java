package com.safetyalerts.api.service.ServicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.repository.MedicalRecordsRepository;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;
import com.safetyalerts.api.service.Services.MedicalRecordsImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MedicalRecordsImplTest {

	@Mock
	private MedicalRecordsRepository medicalRecordsRepository;

	@Mock
	private MedicalRecordsInterface medicalRecordsInterface;

	@Mock
	private MedicalRecords medicalRecords;

	@InjectMocks
	private MedicalRecordsImpl medicalRecordsImpl;

	public List<MedicalRecords> medicalRecordsList;

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

		MedicalRecords medicalRecords2 = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("John");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("02/09/2010");
		String[] medications2 = { "tradoxidine:200mg" };
		medicalRecords.setMedications(medications2);
		String[] allergies2 = {};
		medicalRecords.setAllergies(allergies2);

		medicalRecordsList = Arrays.asList(medicalRecords, medicalRecords2);
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

		when(medicalRecordsRepository.save(medicalRecords)).thenReturn(medicalRecords);
		MedicalRecords savedMedicalRecords = medicalRecordsRepository.save(medicalRecords);
		verify(medicalRecordsRepository, times(1)).save(savedMedicalRecords);
		assertEquals(savedMedicalRecords, medicalRecordsImpl.createMedicalRecords(savedMedicalRecords));
	}

	@Test
	public void testGetMedicalRecords() {
		when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordsList);
		Iterable<MedicalRecords> result = medicalRecordsImpl.getMedicalRecords();
		assertEquals(medicalRecordsList, result);
	}

	@Test
	public void testGetMedicalRecordsById() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		when(medicalRecordsRepository.findById(1)).thenReturn(Optional.of(medicalRecords));
		Optional<MedicalRecords> result = medicalRecordsImpl.getMedicalRecordsById(1);
		assertTrue(result.isPresent());
		assertEquals(medicalRecords, result.get());
	}

	@Test
	public void testDeleteMedicalRecords() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setId(1);
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		when(medicalRecordsRepository.findByFirstNameAndLastName("Eric", "Cadigan"))
				.thenReturn(Optional.ofNullable(medicalRecords));
		boolean result = medicalRecordsImpl.deleteMedicalRecords("Eric", "Cadigan");
		verify(medicalRecordsRepository, times(1)).findByFirstNameAndLastName("Eric", "Cadigan");
		verify(medicalRecordsRepository, times(1)).deleteById(1);
		assertTrue(result);
	}

	@Test
	public void testDeleteMedicalRecordsReturnFalse() {
		when(medicalRecordsRepository.findByFirstNameAndLastName("Eric", "Cadigan")).thenReturn(Optional.empty());
		boolean result = medicalRecordsImpl.deleteMedicalRecords("Eric", "Cadigan");
		verify(medicalRecordsRepository, times(1)).findByFirstNameAndLastName("Eric", "Cadigan");
		verify(medicalRecordsRepository, never()).deleteById(anyInt());
		assertFalse(result);
	}
}