package com.safetyalerts.api.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import java.util.ArrayList;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
		

		Person person2 = new Person();
		person2.setFirstName("John");
		person2.setLastName("Cadigan");
		person2.setAddress("951 LoneTree Rd");
		person2.setCity("Culver");
		person2.setZip(97451L);
		person2.setPhone("841-874-7468");
		person2.setEmail("jhons@email.com");
		person2.setMedicalRecords(medicalRecords2);

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
		fireStation = new FireStation();
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
	public void testDeleteFireStation() {
		int fireStationId = 1;

		fireStationController.deleteFireStation(fireStationId);

		verify(fireStationInterface).deleteFireStation(fireStationId);
	}

	@Test
	public void testGetPersonsWithMedicalRecords() {

		 when(fireStationInterface.getPersonsByStationsNumber(2L)).thenReturn(persons);

	        // Execute
	        ResponseEntity<Map<String, Object>> responseEntity = fireStationController.getPersonsWithMedicalRecords(2L);

	        // Verify
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
	        int numberOfAdults = (int) responseBody.get("numberOfAdults");
	        int numberOfChildren = (int) responseBody.get("numberOfChildren");

	        assertEquals(1, numberOfAdults); // Check the number of adults
	        assertEquals(1, numberOfChildren); // Check the number of children

	        verify(fireStationInterface).getPersonsByStationsNumber(2L);
	}
	
	 @Test
	    public void testGetChildsWithAgeByAddress(){ 
	
}
	 @Test
	    public void testGetPhoneNumbersByStation(){
	

	 }
	 
	 @Test
	    public void testGetPersonsWithAgeByAddress(){}
	 
	 @Test
	    public void testGetPersonsInAddress(){
	      	  
	 }
	 
	 @Test
	    public void testGetPersonByFirstAndLastName(){}
	 
	 
	 @Test
	    public void testGetEmailsByCity() {
	        List<Person> personsList = new ArrayList<>();
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
			
			personsList = Arrays.asList(person, person2);
	        
	        when(personServiceInterface.getPersons()).thenReturn(personsList);
	        
	        List<Map<String, Object>> response = fireStationController.getEmailsByCity("Culver");
	        
	        assertEquals(2, response.size());
	        assertEquals("gramps@email.com", response.get(0).get("email"));
	        assertEquals("jhons@email.com", response.get(1).get("email"));
	 }

}
