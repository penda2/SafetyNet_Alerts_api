package com.safetyalerts.api.controller;

import java.util.Optional;

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
import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

@RestController
public class PersonController {
	Logger logger = LogManager.getLogger(PersonController.class);

	private PersonServiceInterface personServiceInterface;

	@Autowired
	public PersonController(PersonServiceInterface thePersonServiceInterface) {
		personServiceInterface = thePersonServiceInterface;
	}

	// Get person 
	@GetMapping("/person")
	public Iterable<Person> getPersons() {
		return personServiceInterface.getPersons();
	}

	// Get person by Id
	@GetMapping("/person/{id}")
	public Person getPersonById(@PathVariable("id") Integer id) {
		Optional<Person> person = personServiceInterface.getPersonById(id);
		if (person.isPresent()) {
			return person.get();
		} else {
			return null;
		}
	}

	// Create a person 
	@PostMapping("/person")
	public Person createPerson(@RequestBody Person person) {
		logger.info("Received person object: {}", person);
		return personServiceInterface.createPerson(person);
	}

	// Update a person by Id
	@PutMapping("/person/{id}")
	public Person updatePerson(@PathVariable Integer id, @RequestBody Person person) {
		Optional<Person> optionalPerson = personServiceInterface.getPersonById(id);
		if (!optionalPerson.isPresent()) {
			throw new RuntimeException("The Person to update doesn't exist");
		}
		Person existingPerson = optionalPerson.get();
		existingPerson.setAddress(person.getAddress());
		existingPerson.setCity(person.getCity());
		existingPerson.setZip(person.getZip());
		existingPerson.setPhone(person.getPhone());
		existingPerson.setEmail(person.getEmail());

		return personServiceInterface.createPerson(existingPerson);
	}

	// Delete a person method
	@DeleteMapping("/person/{firstName}/{lastName}")
	public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
		boolean deleted = personServiceInterface.deletePerson(firstName, lastName);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
