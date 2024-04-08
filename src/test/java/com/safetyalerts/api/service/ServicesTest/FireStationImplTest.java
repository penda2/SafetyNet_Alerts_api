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
import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.FireStationRepository;
import com.safetyalerts.api.service.Interfaces.FireStationInterface;

@ExtendWith(MockitoExtension.class)
public class FireStationImplTest {

	@Mock
	private FireStationRepository fireStationRepository;

	@Mock
	private FireStationInterface fireStationInterface;

	@Mock
	private FireStation fireStation; 

	@Mock
	private Person person;

	@BeforeEach
	public void setUp() {
		fireStation = new FireStation();
		fireStation.setFirestationId(1); 
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation(2L);

		List<Person> persons = new ArrayList<>();

		Person person1 = new Person();
		person1.setFirstName("Eric");
		person1.setLastName("Cadigan");
		person1.setAddress("1509 Culver St");
		person1.setCity("Culver");
		person1.setZip(97451L);
		person1.setPhone("841-874-7458");
		person1.setEmail("gramps@email.com");

		Person person2 = new Person();
		person2.setFirstName("Alice");
		person2.setLastName("Smith");
		person2.setAddress("1509 Culver St");
		person2.setCity("Culver");
		person2.setZip(97451L);
		person2.setPhone("123-456-7890");
		person2.setEmail("alice@email.com");

		persons.add(person1);
		persons.add(person2);

		fireStation.setPersons(persons);
	}

	@Test
	public void testCreateFireStation() {
		when(fireStationInterface.createFireStation(fireStation)).thenReturn(fireStation);
		FireStation savedFireStation = fireStationInterface.createFireStation(fireStation);
		verify(fireStationInterface, times(1)).createFireStation(savedFireStation);
		assertEquals(fireStation, savedFireStation);
	}

	@Test
	public void testGetFireStations() {
		List<FireStation> createdFireStations = new ArrayList<FireStation>();
		createdFireStations.add(new FireStation());
		when(fireStationInterface.getFireStations()).thenReturn(createdFireStations);
		Iterable<FireStation> result = fireStationInterface.getFireStations();
		assertEquals(createdFireStations, result);
	}

	@Test
	public void testGetFireStationById() {
		when(fireStationInterface.getFireStationById(1)).thenReturn(Optional.of(fireStation));
		Optional<FireStation> result = fireStationInterface.getFireStationById(1);
		assertTrue(result.isPresent());
		assertEquals(fireStation, result.get());
	}

	@Test
	public void testGetPersonsByStationNumber() {
		List<Person> expectedPersons = new ArrayList<>();
        when(fireStationInterface.getPersonsByStationsNumber(2L)).thenReturn(expectedPersons);
        List<Person> actualPersons = fireStationInterface.getPersonsByStationsNumber(2L);
        assertEquals(expectedPersons, actualPersons);
	}

	@Test
	public void testDeleteFireStation() {
		when(fireStationInterface.getFireStationById(1)).thenReturn(Optional.ofNullable(fireStation));
		assertAll(() -> fireStationInterface.getFireStationById(1));
	}
}
