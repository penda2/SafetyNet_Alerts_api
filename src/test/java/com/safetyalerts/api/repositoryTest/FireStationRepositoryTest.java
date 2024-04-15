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

import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.repository.FireStationRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FireStationRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private FireStationRepository fireStationRepository;

	@Test
	public void testSaveFireStation() {
		FireStation fireStation = new FireStation();
		fireStation.setFirestationId(1);
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		FireStation savedFireStation = fireStationRepository.save(fireStation);

		assertNotNull(savedFireStation.getFirestationId());
		assertEquals(fireStation.getStation(), savedFireStation.getStation());

		FireStation findedFireStation = entityManager.find(FireStation.class, savedFireStation.getFirestationId());
		assertEquals(savedFireStation, findedFireStation);
	}

	@Test
	public void testFindAllFireStations() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		FireStation fireStation2 = new FireStation();
		fireStation2.setAddress("1509 Culver St");
		fireStation2.setStation(3L);

		fireStationRepository.save(fireStation);
		fireStationRepository.save(fireStation2);

		Iterable<FireStation> fireStationList = fireStationRepository.findAll();

		assertThat(fireStationList).isNotNull();
		assertThat(fireStationList).size().isEqualTo(2);
	}

	@Test
	public void testFindFireStationById() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		fireStationRepository.save(fireStation);

		Optional<FireStation> fireStationById = fireStationRepository.findById(fireStation.getFirestationId());

		assertThat(fireStationById).isNotNull();
		assertEquals(fireStation, fireStationById.get());
	}

	@Test
	public void testFindFireStationByStationNumber() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		fireStationRepository.save(fireStation);

		Optional<FireStation> stationNumber = fireStationRepository.findByStation(fireStation.getStation());

		assertThat(stationNumber).isNotNull();
	}

	@Test
	public void testUpdateFireStation() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);

		fireStationRepository.save(fireStation);

		fireStation.setAddress("29 15th St");

		FireStation updatedFireStation = fireStationRepository.save(fireStation);

		assertThat(updatedFireStation.getAddress()).isNotNull();
	}

	@Test
	public void testDeleteFireStation() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("951 LoneTree Rd");
		fireStation.setStation(2L);
		fireStationRepository.save(fireStation);

		fireStationRepository.deleteById(fireStation.getFirestationId());

		Optional<FireStation> deletedFireStation = fireStationRepository.findById(fireStation.getFirestationId());

		assertThat(deletedFireStation).isEmpty();
	}
}
