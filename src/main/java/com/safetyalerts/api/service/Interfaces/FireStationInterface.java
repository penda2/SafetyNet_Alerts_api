package com.safetyalerts.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.model.Person;

public interface FireStationInterface {
	public FireStation createFireStation(FireStation theFireStation );
	public Iterable<FireStation> getFireStations();
	public Optional<FireStation> getFireStationById(int theId);
	public boolean deleteFireStation(int theId);
	public FireStation getFireStationByStationNumber(Long station);
	List<Person> getPersonsByStationsNumber(Long station);
}
