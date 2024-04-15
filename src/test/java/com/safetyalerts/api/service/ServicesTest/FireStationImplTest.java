package com.safetyalerts.api.service.ServicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.FireStationRepository;
import com.safetyalerts.api.service.Interfaces.FireStationInterface;
import com.safetyalerts.api.service.Services.FireStationImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FireStationImplTest {

	@Mock
	private FireStationRepository fireStationRepository;

	@InjectMocks
	private FireStationImpl fireStationImpl;

	@Mock
	private FireStationInterface fireStationInterface;

	@Mock
	private FireStation fireStation;

	public List<FireStation> fireStations;

	public List<Person> persons;

	@BeforeEach
	public void setUp() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation(3L);

		FireStation fireStation2 = new FireStation();
		fireStation2.setAddress("951 LoneTree Rd");
		fireStation2.setStation(2L);

		fireStations = Arrays.asList(fireStation, fireStation2);

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

		persons = Arrays.asList(person1, person2);
	}

	@Test
	public void testCreateFireStation() {
		FireStation fireStation = new FireStation();
		fireStation.setFirestationId(1);
		fireStation.setAddress("947 E. Rose Dr");
		fireStation.setStation(1L);

		when(fireStationRepository.save(fireStation)).thenReturn(fireStation);
		FireStation savedFireStation = fireStationRepository.save(fireStation);
		verify(fireStationRepository, times(1)).save(fireStation);
		assertEquals(savedFireStation, fireStationImpl.createFireStation(savedFireStation));
	}

	@Test
	public void testGetFireStations() {
		when(fireStationRepository.findAll()).thenReturn(fireStations);
		Iterable<FireStation> result = fireStationImpl.getFireStations();
		assertEquals(fireStations, result);
	}

	@Test
	public void testGetFireStationById() {
		FireStation fireStation = new FireStation();
		fireStation.setFirestationId(1);
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation(3L);

		when(fireStationRepository.findById(1)).thenReturn(Optional.of(fireStation));
		Optional<FireStation> result = fireStationImpl.getFireStationById(1);
		assertTrue(result.isPresent());
		assertEquals(fireStation, result.get());
	}

	@Test
	public void testDeleteFireStation() {
		FireStation fireStation = new FireStation();
		fireStation.setFirestationId(1);
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation(3L);

		when(fireStationRepository.existsById(1)).thenReturn(true);

		boolean result = fireStationImpl.deleteFireStation(1);

		assertTrue(result);
		verify(fireStationRepository).deleteById(1);
	}

	@Test
	public void testFireStationToDeleteNotFound() {
		when(fireStationRepository.existsById(1)).thenReturn(false);

		boolean result = fireStationImpl.deleteFireStation(1);

		assertFalse(result);
		verify(fireStationRepository, never()).deleteById(anyInt());
	}

	@Test
	public void testGetPersonsByStationNumber() {
		Long stationNumber = 3L;
		FireStation fireStation1 = new FireStation();
		fireStation1.setStation(3L);

		fireStation1.setPersons(persons);

		when(fireStationRepository.findAll()).thenReturn(Arrays.asList(fireStation1));

		List<Person> persons = fireStationImpl.getPersonsByStationsNumber(stationNumber);

		assertEquals(2, persons.size());
	}

	@Test
	public void testGetFireStationByStationNumber() {
		Long stationNumber = 3L;
		FireStation fireStation = new FireStation();
		fireStation.setFirestationId(1);
		fireStation.setAddress("1509 Culver St");
		fireStation.setStation(3L);

		when(fireStationRepository.findByStation(stationNumber)).thenReturn(Optional.of(fireStation));
		FireStation result = fireStationImpl.getFireStationByStationNumber(stationNumber);
		assertNotNull(result);
		assertEquals(fireStation, result);
	}
}