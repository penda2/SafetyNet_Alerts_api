package com.safetyalerts.api.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.safetyalerts.api.controller.PersonController;
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PersonControllerTest {

	@Mock
	private PersonServiceInterface personServiceInterface;

	@InjectMocks
	private PersonController personController;

	@Mock
	private Logger logger;

	private List<Person> persons;

	@BeforeEach
	public void seUp() {
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
	}

	@Test
	public void testGetPersons() {
		when(personServiceInterface.getPersons()).thenReturn(persons);

		Iterable<Person> result = personController.getPersons();

		assertEquals(persons.size(), ((List<Person>) result).size());
	}

	@Test
	public void testGetPersonById() {
		int personId = 1;
		Person person = new Person();
		person.setId(personId);
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		when(personServiceInterface.getPersonById(personId)).thenReturn(Optional.of(person));
		Person result = personController.getPersonById(personId);

		assertEquals(person, result);

		verify(personServiceInterface).getPersonById(personId);
	}

	@Test
	public void testGetPersonByIdReturnNull() {
		int personId = 1;
		when(personServiceInterface.getPersonById(personId)).thenReturn(Optional.empty());
		Person result = personController.getPersonById(personId);
		assertNull(result);
		verify(personServiceInterface).getPersonById(personId);
	}

	@Test
	public void testCreatePerson() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		when(personServiceInterface.createPerson(person)).thenReturn(person);

		Person result = personController.createPerson(person);
		assertEquals(person, result);
		verify(logger).info("Received person object: {}", person);
	}

	@Test
	public void testUpdatePerson() {
		int personId = 1;
		Person person = new Person();
		person.setId(personId);
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		Person updatedPerson = new Person();
		updatedPerson.setId(personId);
		updatedPerson.setFirstName("Eric");
		updatedPerson.setLastName("Cadigan");
		updatedPerson.setAddress("29 15th St");
		updatedPerson.setCity("Culver");
		updatedPerson.setZip(97451L);
		updatedPerson.setPhone("841-874-7468");
		updatedPerson.setEmail("gramps@email.com");

		when(personServiceInterface.getPersonById(personId)).thenReturn(Optional.of(person));
		when(personServiceInterface.createPerson(updatedPerson)).thenReturn(updatedPerson);
		Person result = personController.updatePerson(personId, updatedPerson);

		assertEquals(updatedPerson, result);
		verify(personServiceInterface).getPersonById(personId);
		verify(personServiceInterface).createPerson(updatedPerson);
	}

	@Test
	public void testPersonToUpdateNotFound() {
		int personId = 1;

		Person updatedPerson = new Person();
		updatedPerson.setId(personId);
		updatedPerson.setFirstName("Eric");
		updatedPerson.setLastName("Cadigan");
		updatedPerson.setAddress("29 15th St");
		updatedPerson.setCity("Culver");
		updatedPerson.setZip(97451L);
		updatedPerson.setPhone("841-874-7468");
		updatedPerson.setEmail("gramps@email.com");

		when(personServiceInterface.getPersonById(personId)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			personController.updatePerson(personId, updatedPerson);
		});

		verify(personServiceInterface).getPersonById(personId);

		verify(personServiceInterface, never()).createPerson(updatedPerson);
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

		personController.deletePerson("Eric", "Cadigan");
		verify(personServiceInterface, times(1)).deletePerson("Eric", "Cadigan");
	}
}
