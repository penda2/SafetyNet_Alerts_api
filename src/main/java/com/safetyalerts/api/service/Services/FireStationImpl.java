package com.safetyalerts.api.service.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.FireStationRepository;
import com.safetyalerts.api.service.Interfaces.FireStationInterface;
import lombok.Data;

@Data
@Service
public class FireStationImpl implements FireStationInterface {

	@Autowired
	private FireStationRepository fireStationRepository;

	@Override
	public FireStation createFireStation(FireStation theFireStation) {
		FireStation savedFireStation = fireStationRepository.save(theFireStation);
		return savedFireStation;
	}

	@Override
	public Iterable<FireStation> getFireStations() {
		return fireStationRepository.findAll();
	}

	@Override
	public Optional<FireStation> getFireStationById(Integer theId) {
		return fireStationRepository.findById(theId);
	}

	@Override
	public boolean deleteFireStation(Integer theId) {
		if (fireStationRepository.existsById(theId)) {
			fireStationRepository.deleteById(theId);
			return true;
		}
		return false;
	}

	@Override
	public FireStation getFireStationByStationNumber(Long station) {
		return fireStationRepository.findByStation(station)
				.orElseThrow(() -> new IllegalArgumentException("station not found for station number" + station));
	}

	@Override
	public List<Person> getPersonsByStationsNumber(Long station) {
		Iterable<FireStation> allFireStations = fireStationRepository.findAll();
		List<Person> persons = new ArrayList<>();

		for (FireStation fireStation : allFireStations) {
			if (fireStation.getStation() == station && fireStation.getPersons() != null) {
				persons.addAll(fireStation.getPersons());
			}
		}
		return persons;
	}
}