package com.safetyalerts.api.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.repository.MedicalRecordsRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MedicalRecordsRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	MedicalRecordsRepository medicalRecordsRepository;

	@Test
	public void testSaveMedicalRecords() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		MedicalRecords savedMedicalRecords = medicalRecordsRepository.save(medicalRecords);

		assertNotNull(savedMedicalRecords.getId());
		assertEquals(medicalRecords.getFirstName(), savedMedicalRecords.getFirstName());

		MedicalRecords findedmedicalRecords = entityManager.find(MedicalRecords.class, savedMedicalRecords.getId());
		assertEquals(savedMedicalRecords, findedmedicalRecords);
	}

	@Test
	public void testFindAllMedicalRecords() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		MedicalRecords medicalRecords2 = new MedicalRecords();
		medicalRecords2.setFirstName("Eric");
		medicalRecords2.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications2 = { "tradoxidine:200mg" };
		medicalRecords.setMedications(medications2);
		String[] allergies2 = {};
		medicalRecords.setAllergies(allergies2);

		medicalRecordsRepository.save(medicalRecords);

		medicalRecordsRepository.save(medicalRecords2);

		Iterable<MedicalRecords> medicalRecordsList = medicalRecordsRepository.findAll();

		assertThat(medicalRecordsList).isNotNull();
		assertThat(medicalRecordsList).size().isEqualTo(2);
	}

	@Test
	public void testFindMedicalRecordsById() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);

		medicalRecordsRepository.save(medicalRecords);

		Optional<MedicalRecords> medicalRecordsById = medicalRecordsRepository.findById(medicalRecords.getId());

		assertThat(medicalRecordsById).isNotNull();
		assertEquals(medicalRecords, medicalRecordsById.get());
	}

	@Test
	public void testFindPersonByFirstNameAndLastName() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);
		medicalRecordsRepository.save(medicalRecords);

		Optional<MedicalRecords> medicalRecordsFirstAndLastName = medicalRecordsRepository
				.findByFirstNameAndLastName(medicalRecords.getFirstName(), medicalRecords.getLastName());

		assertThat(medicalRecordsFirstAndLastName).isNotNull();
	}

	@Test
	public void testUpdateMedicalRecords() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan");
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);
		medicalRecordsRepository.save(medicalRecords);

		medicalRecords.setFirstName("John");

		MedicalRecords updatedMedicalRecords = medicalRecordsRepository.save(medicalRecords);

		assertThat(updatedMedicalRecords.getFirstName()).isNotNull();
	}

	@Test
	public void testDeleteMedicalRecords() {
		MedicalRecords medicalRecords = new MedicalRecords();
		medicalRecords.setFirstName("Eric");
		medicalRecords.setLastName("Cadigan"); 
		medicalRecords.setBirthDate("08/06/1945");
		String[] medications = { "tradoxidine:400mg" };
		medicalRecords.setMedications(medications);
		String[] allergies = {};
		medicalRecords.setAllergies(allergies);
		medicalRecordsRepository.save(medicalRecords);

		medicalRecordsRepository.deleteById(medicalRecords.getId());
		;

		Optional<MedicalRecords> deletedMedicalRecords = medicalRecordsRepository.findById(medicalRecords.getId());

		assertThat(deletedMedicalRecords).isEmpty();
	}

}
