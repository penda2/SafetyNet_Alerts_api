package com.safetyalerts.api.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

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
import com.safetyalerts.api.controller.FireStationController;
import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.service.Interfaces.FireStationInterface;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FireStationControllerTest {

	@InjectMocks
	private FireStationController fireStationController;

	@Mock
	private FireStationInterface fireStationInterface;

	@Mock
	private PersonServiceInterface personServiceInterface;

	@Mock
	private MedicalRecordsInterface medicalRecordsInterface;

	@Mock
	private FireStation fireStation;

	@Mock
	private Person person;

	@Mock
	MedicalRecords medicalRecords;

	@Mock
	private Logger logger;

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
		person.setFireStation(fireStation);

		Person person2 = new Person();
		person2.setFirstName("John");
		person2.setLastName("Cadigan");
		person2.setAddress("951 LoneTree Rd");
		person2.setCity("Culver");
		person2.setZip(97451L);
		person2.setPhone("541-874-2019");
		person2.setEmail("jhons@email.com");
		person2.setMedicalRecords(medicalRecords2);
		person2.setFireStation(fireStation);

		persons = Arrays.asList(person, person2);
	}

	@Test
	public void testGetFireStations() {
		when(fireStationInterface.getFireStations()).thenReturn(fireStationList);

		Iterable<FireStation> result = fireStationController.getFireStations();

		assertEquals(fireStationList.size(), ((List<FireStation>) result).size());
	}

	@Test
	public void testCreateFirestation() {
		FireStation fireStation = new FireStation();
		fireStation.setFirestationId(1);
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation(2L);

		when(fireStationInterface.createFireStation(fireStation)).thenReturn(fireStation);

		FireStation result = fireStationController.createFirestation(fireStation);
		assertEquals(fireStation, result);
		verify(logger).info("Received firestation object: {}", fireStation);
	}

	@Test
	public void testUpdateFireStation() {
		int fireStationId = 1;
		FireStation existingFireStation = new FireStation();
		existingFireStation.setFirestationId(fireStationId);
		existingFireStation.setAddress("951 LoneTree Rd");
		existingFireStation.setStation(2L);

		FireStation updatedFireStation = new FireStation();
		updatedFireStation.setFirestationId(fireStationId);
		updatedFireStation.setAddress("29 15th St");
		updatedFireStation.setStation(2L);

		when(fireStationInterface.getFireStationById(fireStationId)).thenReturn(Optional.of(existingFireStation));
		when(fireStationInterface.createFireStation(updatedFireStation)).thenReturn(updatedFireStation);

		FireStation result = fireStationController.updateFireStation(fireStationId, updatedFireStation);

		assertEquals(updatedFireStation, result);
		verify(fireStationInterface).getFireStationById(fireStationId);
		verify(fireStationInterface).createFireStation(updatedFireStation);
	}

	@Test
	public void testFireStationToUpdateNotFound() {
		int fireStationId = 1;
		FireStation updatedFireStation = new FireStation();
		updatedFireStation.setFirestationId(fireStationId);
		updatedFireStation.setAddress("29 15th St");
		updatedFireStation.setStation(2L);

		when(fireStationInterface.getFireStationById(fireStationId)).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			fireStationController.updateFireStation(fireStationId, updatedFireStation);
		});

		verify(fireStationInterface).getFireStationById(fireStationId);
		verify(fireStationInterface, never()).createFireStation(updatedFireStation);
	}

	@Test
	public void testGetFireStationById() {
		int fireStationId = 1;
		FireStation existingFireStation = new FireStation();
		existingFireStation.setFirestationId(fireStationId);
		existingFireStation.setAddress("951 LoneTree Rd");
		existingFireStation.setStation(2L);

		when(fireStationInterface.getFireStationById(fireStationId)).thenReturn(Optional.of(existingFireStation));

		FireStation result = fireStationController.getFireStationById(fireStationId);

		assertEquals(existingFireStation, result);
		verify(fireStationInterface).getFireStationById(fireStationId);
	}

	@Test
	public void testGetFireStationByIdReturnNull() {
		int fireStationId = 1;
		when(fireStationInterface.getFireStationById(fireStationId)).thenReturn(Optional.empty());

		FireStation result = fireStationController.getFireStationById(fireStationId);

		assertNull(result);
		verify(fireStationInterface).getFireStationById(fireStationId);
	}

	@Test
	public void testDeleteFireStation() {
		int fireStationId = 1;

		fireStationController.deleteFireStation(fireStationId);

		verify(fireStationInterface).deleteFireStation(fireStationId);
	}

	@Test
	public void testGetPersonsByStationsNumber() {
		when(fireStationInterface.getPersonsByStationsNumber(2L)).thenReturn(persons);

		Map<String, Object> result = fireStationController.getPersonsByStationsNumber(2L);

		assertEquals(1, result.get("numberOfChildren"));
		assertEquals(1, result.get("numberOfAdults"));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> personsList = (List<Map<String, Object>>) result.get("persons");
		assertEquals(2, personsList.size());
		Map<String, Object> firstPerson = personsList.get(0);
		assertEquals("Eric", firstPerson.get("firstName"));
		assertEquals("Cadigan", firstPerson.get("lastName"));
		assertEquals("841-874-7458", firstPerson.get("phone"));
	}

	@Test
	public void testGetChildsWithAgeByAddress() {
		when(personServiceInterface.getPersons()).thenReturn(persons);

		Map<String, Object> result = fireStationController.getChildsWithAgeByAddress("951 LoneTree Rd");
		assertEquals(2, persons.size());
		assertEquals(1, result.get("numberOfChildren"));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> childrenList = (List<Map<String, Object>>) result.get("children");
		assertEquals(1, childrenList.size());
		Map<String, Object> firstChild = childrenList.get(0);
		assertEquals("John", firstChild.get("firstName"));
		assertEquals("Cadigan", firstChild.get("lastName"));
		assertEquals(13, firstChild.get("age"));
	}

	@Test
	public void testGetPhoneNumbersByStation() {
		when(personServiceInterface.getPersons()).thenReturn(persons);
		Map<String, Object> result = fireStationController.getPhoneNumbersByStation(2L);

		assertNotNull(result.get("phones"));
	}

	@Test
	public void testGetPersonsWithAgeByAddress() {
		when(personServiceInterface.getPersons()).thenReturn(persons);

		Map<String, Object> result = fireStationController.getPersonsWithAgeByAddress("951 LoneTree Rd");

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> personsByAddress = (List<Map<String, Object>>) result.get("persons");
		assertEquals(2, personsByAddress.size());

		Map<String, Object> personMap = personsByAddress.get(0);
		assertEquals("Eric", personMap.get("firstName"));
		assertEquals(43, personMap.get("age"));
	}

	@Test
	public void testGetPersonsInAddress() {
		when(personServiceInterface.getPersons()).thenReturn(persons);

		List<Map<String, Object>> result = fireStationController.getPersonsInAddress(2L);

		int personsInAddress = 2;

		assertEquals(personsInAddress, result.size());
	}

	@Test
	public void testGetPersonByFirstAndLastName() {
		when(personServiceInterface.getPersons()).thenReturn(persons);
		List<Map<String, Object>> responseList = fireStationController.getPersonByFirstAndLastName("Eric", "Cadigan");

		assertEquals(2, responseList.size());
		assertEquals("gramps@email.com", responseList.get(0).get("email"));
		assertEquals("jhons@email.com", responseList.get(1).get("email"));
	}

	@Test
	public void testGetEmailsByCity() {
		when(personServiceInterface.getPersons()).thenReturn(persons);
		
		List<Map<String, Object>> response = fireStationController.getEmailsByCity("Culver");

		assertEquals(2, response.size());
		assertEquals("gramps@email.com", response.get(0).get("email"));
		assertEquals("jhons@email.com", response.get(1).get("email"));
	}	
}
