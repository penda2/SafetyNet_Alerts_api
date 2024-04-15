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

import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.PersonRepository;
import com.safetyalerts.api.service.Services.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PersonServiceImplTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private Person person;

	@InjectMocks
	private PersonServiceImpl personServiceImpl;

	public List<Person> persons;

	public List<FireStation> fireStations;

	public List<MedicalRecords> medicalRecordsList;

	@BeforeEach
	public void setup() {
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

		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		FireStation fireStation2 = new FireStation();
		fireStation2.setAddress("1509 Culver St");
		fireStation2.setStation(3L);

		fireStations = Arrays.asList(fireStation, fireStation2);

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
		medicalRecords2.setLastName("Cadigan");
		medicalRecords.setBirthDate("02/09/2010");
		String[] medications2 = { "tradoxidine:200mg" };
		medicalRecords.setMedications(medications2);
		String[] allergies2 = {};
		medicalRecords.setAllergies(allergies2);

		medicalRecordsList = Arrays.asList(medicalRecords, medicalRecords2);
	}

	@Test
	public void testCreatePerson() {
		Person person = new Person();
		person.setId(1);
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		when(personRepository.save(person)).thenReturn(person);
		Person savedPerson = personRepository.save(person);
		verify(personRepository, times(1)).save(person);
		assertEquals(savedPerson, personServiceImpl.createPerson(savedPerson));
	}

	@Test
	public void testGetPersons() {
		when(personRepository.findAll()).thenReturn(persons);
		Iterable<Person> result = personServiceImpl.getPersons();
		assertEquals(persons, result);
	}

	@Test
	public void testGetPersonById() {
		Person person = new Person();
		person.setId(1);
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		when(personRepository.findById(1)).thenReturn(Optional.of(person));

		Optional<Person> result = personServiceImpl.getPersonById(1);

		assertTrue(result.isPresent());
		assertEquals(person, result.get());
	}

	@Test
	public void testDeletePerson() {
		Person person = new Person();
		person.setId(1);
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		when(personRepository.findByFirstNameAndLastName("Eric", "Cadigan")).thenReturn(Optional.ofNullable(person));

		boolean result = personServiceImpl.deletePerson("Eric", "Cadigan");
		verify(personRepository, times(1)).findByFirstNameAndLastName("Eric", "Cadigan");
		verify(personRepository, times(1)).deleteById(1);
		assertTrue(result);
	}

	@Test
	public void testDeletePersonReturnFalse() {
		when(personRepository.findByFirstNameAndLastName("Eric", "Cadigan")).thenReturn(Optional.empty());
		boolean result = personServiceImpl.deletePerson("Eric", "Cadigan");
		verify(personRepository, times(1)).findByFirstNameAndLastName("Eric", "Cadigan");
		verify(personRepository, never()).deleteById(anyInt());
		assertFalse(result);
	}

	@Test
	public void testGetChildrenByAddress() {
		when(personRepository.findByAddress("951 LoneTree Rd")).thenReturn(persons);
		Iterable<Person> result = personServiceImpl.getChildrenByAddress("951 LoneTree Rd");
		assertEquals(result, personRepository.findByAddress("951 LoneTree Rd"));
	}
}