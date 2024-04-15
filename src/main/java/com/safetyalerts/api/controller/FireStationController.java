package com.safetyalerts.api.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.safetyalerts.api.model.FireStation;
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.service.Interfaces.FireStationInterface;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

@RestController
public class FireStationController {

	Logger logger = LogManager.getLogger(MedicalRecordsController.class);

	private FireStationInterface fireStationInterface;

	@Autowired
	PersonServiceInterface personServiceInterface;

	@Autowired
	public FireStationController(FireStationInterface theFireStationInterface) {
		fireStationInterface = theFireStationInterface;
	}

	// Get all fire stations
	@GetMapping("/firestation/read")
	public Iterable<FireStation> getFireStations() {
		return fireStationInterface.getFireStations();
	}

	// Create fire station
	@PostMapping("/firestation/create")
	public FireStation createFirestation(@RequestBody FireStation firestation) {
		logger.info("Received firestation object: {}", firestation);
		return fireStationInterface.createFireStation(firestation);
	}

	// Update fire station
	@PutMapping("/firestation/{theId}")
	public FireStation updateFireStation(@PathVariable Integer theId, @RequestBody FireStation theFireStation) {
		Optional<FireStation> optionalFireStation = fireStationInterface.getFireStationById(theId);
		if (!optionalFireStation.isPresent()) {
			throw new RuntimeException("The fire station to update doesn't exist");
		}
		FireStation existingFireStation = optionalFireStation.get();
		existingFireStation.setAddress(theFireStation.getAddress());
		existingFireStation.setStation(theFireStation.getStation());
		return fireStationInterface.createFireStation(existingFireStation);
	}

	// Get fire station by id
	@GetMapping("/firestation/{id}")
	public FireStation getFireStationById(@PathVariable("id") Integer id) {
		Optional<FireStation> fireStation = fireStationInterface.getFireStationById(id);
		if (fireStation.isPresent()) {
			return fireStation.get();
		} else {
			return null;
		}
	}

	// Delete fire station
	@DeleteMapping("/firestation/{id}")
	public ResponseEntity<Void> deleteFireStation(@PathVariable Integer id) {
		boolean deleted = fireStationInterface.deleteFireStation(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Get list of persons by station number + count of number of children and
	// adults
	@GetMapping("/firestationNumber/{station}")
	public Map<String, Object> getPersonsByStationsNumber(@PathVariable Long station) {
		List<Person> persons = fireStationInterface.getPersonsByStationsNumber(station);
		List<Map<String, Object>> personsList = new ArrayList<>();
		int numberOfAdults = 0;
		int numberOfChildren = 0;

		for (Person person : persons) {
			MedicalRecords medicalRecords = person.getMedicalRecords();
			if (medicalRecords != null) {
				LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthDate(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				Period age = Period.between(birthdate, LocalDate.now());
				if (age.getYears() >= 18) {
					numberOfAdults++;
				} else {
					numberOfChildren++;
				}
				Map<String, Object> personMap = new HashMap<>();
				personMap.put("firstName", person.getFirstName());
				personMap.put("lastName", person.getLastName());
				personMap.put("address", person.getAddress());
				personMap.put("phone", person.getPhone());
				personsList.add(personMap);
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("persons", personsList);
		response.put("numberOfAdults", numberOfAdults);
		response.put("numberOfChildren", numberOfChildren);
		return response;
	}

	// Get list of children by address
	@GetMapping("/childAlert/{address}")
	public Map<String, Object> getChildsWithAgeByAddress(@PathVariable String address) {
		Iterable<Person> persons = personServiceInterface.getPersons();
		List<Map<String, Object>> childrenList = new ArrayList<>();
		int numberOfChildren = 0;

		for (Person person : persons) {
			FireStation fireStation = person.getFireStation();
			if (fireStation != null && fireStation.getAddress().equals(address)) {
				MedicalRecords medicalRecords = person.getMedicalRecords();
				if (medicalRecords != null) {
					LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthDate(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					Period age = Period.between(birthdate, LocalDate.now());

					if (age.getYears() < 18) {
						Map<String, Object> childMap = new LinkedHashMap<>();
						childMap.put("firstName", person.getFirstName());
						childMap.put("lastName", person.getLastName());
						childMap.put("age", age.getYears());
						childrenList.add(childMap);
						numberOfChildren++;
					}
				}
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("children", childrenList);
		response.put("numberOfChildren", numberOfChildren);
		return response;
	}

	@GetMapping("/phoneAlert/{station}")
	public Map<String, Object> getPhoneNumbersByStation(@PathVariable Long station) {
		Iterable<Person> persons = personServiceInterface.getPersons();
		Map<Long, List<String>> phoneNumbersByStation = new HashMap<>();
		for (Person person : persons) {
			FireStation fireStation = person.getFireStation();
			if (fireStation != null && fireStation.getStation().equals(station)) {
				String phoneNumber = person.getPhone();
				List<String> phoneNumbers = phoneNumbersByStation.getOrDefault(station, new ArrayList<>());
				phoneNumbers.add(phoneNumber);
				phoneNumbersByStation.put(station, phoneNumbers);
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("phones", phoneNumbersByStation);
		return response;
	}

	// Get the list of persons from an address and the number of their corresponding
	// station

	@GetMapping("/fire/{address}")
	public Map<String, Object> getPersonsWithAgeByAddress(@PathVariable String address) {
		Iterable<Person> persons = personServiceInterface.getPersons();
		List<Map<String, Object>> personsByAddress = new ArrayList<>();

		for (Person person : persons) {
			FireStation fireStation = person.getFireStation();
			if (fireStation != null && fireStation.getAddress().equals(address)) {
				MedicalRecords medicalRecords = person.getMedicalRecords();
				if (medicalRecords != null) {
					LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthDate(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					Period age = Period.between(birthdate, LocalDate.now());

					Map<String, Object> personMap = new LinkedHashMap<>();
					personMap.put("Station", fireStation.getStation());
					personMap.put("firstName", person.getFirstName());
					personMap.put("lastName", person.getLastName());
					personMap.put("phone", person.getPhone());
					personMap.put("age", age.getYears());
					personMap.put("medications", medicalRecords.getMedications());
					personMap.put("allergies", medicalRecords.getAllergies());
					personsByAddress.add(personMap);
				}
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("persons", personsByAddress);
		return response;
	}

	@GetMapping("/flood/stations/{station}")
	public List<Map<String, Object>> getPersonsInAddress(@PathVariable Long station) {
		Iterable<Person> persons = personServiceInterface.getPersons();
		Map<String, List<Map<String, Object>>> personsByAddress = new HashMap<>();

		for (Person person : persons) {
			FireStation fireStation = person.getFireStation();

			if (fireStation != null && fireStation.getStation().equals(station)) {
				MedicalRecords medicalRecords = person.getMedicalRecords();

				if (medicalRecords != null) {
					LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthDate(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					Period age = Period.between(birthdate, LocalDate.now());

					StringBuilder personInfoBuilder = new StringBuilder();
					personInfoBuilder.append(person.getFirstName()).append(" ").append(person.getLastName());
					personInfoBuilder.append(" - Medications: ")
							.append(String.join(", ", medicalRecords.getMedications()));
					personInfoBuilder.append(", Allergies: ").append(String.join(", ", medicalRecords.getAllergies()));
					String personInfo = personInfoBuilder.toString();

					Map<String, Object> personMap = new LinkedHashMap<>();
					personMap.put("personInfo", personInfo);
					personMap.put("phone", person.getPhone());
					personMap.put("age", age.getYears());

					String address = person.getAddress();
					personsByAddress.computeIfAbsent(address, k -> new ArrayList<>()).add(personMap);
				}
			}
		}

		List<Map<String, Object>> personsList = new ArrayList<>();
		personsByAddress.forEach((address, personList) -> personsList.addAll(personList));
		return personsList;
	}

	// get list of persons by firstName + lastName
	@GetMapping("/personInfo/{firstName}/{lastName}")
	public List<Map<String, Object>> getPersonByFirstAndLastName(@PathVariable String firstName,
			@PathVariable String lastName) {
		Iterable<Person> persons = personServiceInterface.getPersons();
		List<Map<String, Object>> personsByName = new ArrayList<>();
		for (Person person : persons) {
			if (person.getLastName().equalsIgnoreCase(lastName)) {
				MedicalRecords medicalRecords = person.getMedicalRecords();
				if (medicalRecords != null) {
					LocalDate birthdate = LocalDate.parse(medicalRecords.getBirthDate(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					Period age = Period.between(birthdate, LocalDate.now());
					Map<String, Object> personMap = new LinkedHashMap<>();
					personMap.put("firstName", person.getFirstName());
					personMap.put("lastName", person.getLastName());
					personMap.put("address", person.getAddress());
					personMap.put("age", age.getYears());
					personMap.put("email", person.getEmail());
					personMap.put("medications", medicalRecords.getMedications());
					personMap.put("allergies", medicalRecords.getAllergies());
					personsByName.add(personMap);
				}
			}
		}
		return personsByName;
	}

	// get filtered/unique persons email by city
	@GetMapping("/communityEmail/{city}")
	public List<Map<String, Object>> getEmailsByCity(@PathVariable String city) {
		Iterable<Person> persons = personServiceInterface.getPersons();
		Set<String> uniqueEmails = new HashSet<>();
		List<Map<String, Object>> personsEmailByCity = new ArrayList<>();

		for (Person person : persons) {
			if (person.getCity().equals(city) && !uniqueEmails.contains(person.getEmail())) {
				Map<String, Object> emailMap = new LinkedHashMap<>();
				emailMap.put("email", person.getEmail());
				personsEmailByCity.add(emailMap);
				uniqueEmails.add(person.getEmail());
			}
		}
		return personsEmailByCity;
	}
}
