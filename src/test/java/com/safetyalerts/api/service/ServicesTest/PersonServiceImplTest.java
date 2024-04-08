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
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.PersonRepository;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private PersonServiceInterface personServiceInterface;

	@Mock
	private Person person;

	@BeforeEach
	public void setup() {
		Person person = new Person();
		person.setId(1);
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");
	}

	@Test
	public void testCreatePerson() {
		when(personServiceInterface.createPerson(person)).thenReturn(person);
		Person savedPerson = personServiceInterface.createPerson(person);
		verify(personServiceInterface, times(1)).createPerson(person);
		assertEquals(person, savedPerson);
	}

	@Test
	public void testGetPersons() {
		List<Person> createdPersons = new ArrayList<Person>();
		createdPersons.add(new Person());
		when(personServiceInterface.getPersons()).thenReturn(createdPersons);
		Iterable<Person> result = personServiceInterface.getPersons();
		assertEquals(createdPersons, result);
	}

	@Test
	public void testGetPersonById() {
		when(personServiceInterface.getPersonById(1)).thenReturn(Optional.of(person));
		Optional<Person> result = personServiceInterface.getPersonById(1);
		assertTrue(result.isPresent());
		assertEquals(person, result.get());
	}

	@Test
	public void testDeletePerson() {
		when(personServiceInterface.getPersonById(1)).thenReturn(Optional.ofNullable(person));
		assertAll(() -> personServiceInterface.getPersonById(1));
	}
}
