package com.safetyalerts.api.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.PersonRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void testSavePerson() {
		Person person = new Person();
		// person.setId(1);
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		Person savedPerson = personRepository.save(person);

		assertNotNull(savedPerson.getId());
		assertEquals(person.getFirstName(), savedPerson.getFirstName());

		Person findedPerson = entityManager.find(Person.class, savedPerson.getId());
		assertEquals(savedPerson, findedPerson);
	}

	@Test
	public void testFindAllPersons() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		Person person2 = new Person();
		person2.setFirstName("Clive");
		person2.setLastName("Ferguson");
		person2.setAddress("951 LoneTree Rd");
		person2.setCity("Culver");
		person2.setZip(97451L);
		person2.setPhone("841-874-6741");
		person2.setEmail("clivfd@ymail.com");

		personRepository.save(person);

		personRepository.save(person2);

		Iterable<Person> personList = personRepository.findAll();

		assertThat(personList).isNotNull();
		assertThat(personList).size().isEqualTo(2);
	}

	@Test
	public void testFindPersonById() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		personRepository.save(person);

		Optional<Person> personById = personRepository.findById(person.getId());

		assertThat(personById).isNotNull();
		assertEquals(person, personById.get());
	}

	@Test
	public void testFindPersonByAddress() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		personRepository.save(person);

		List<Person> personByAdress = personRepository.findByAddress(person.getAddress());

		assertThat(personByAdress).isNotNull();
	}

	@Test
	public void testFindPersonByFirstNameAndLastName() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		personRepository.save(person);

		Optional<Person> personFirstAndLastName = personRepository.findByFirstNameAndLastName(person.getFirstName(),
				person.getLastName());

		assertThat(personFirstAndLastName).isNotNull();
	}

	@Test
	public void testUpdatePerson() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		personRepository.save(person);

		person.setAddress("748 Townings Dr");

		Person updatedPerson = personRepository.save(person);

		assertThat(updatedPerson.getAddress()).isNotNull();
	}

	@Test
	public void testDeletePerson() {
		Person person = new Person();
		person.setFirstName("Eric");
		person.setLastName("Cadigan");
		person.setAddress("951 LoneTree Rd");
		person.setCity("Culver");
		person.setZip(97451L);
		person.setPhone("841-874-7458");
		person.setEmail("gramps@email.com");

		personRepository.save(person);

		personRepository.deleteById(person.getId());
		;

		Optional<Person> deletedPerson = personRepository.findById(person.getId());

		assertThat(deletedPerson).isEmpty();
	}
}
