package com.safetyalerts.api.service.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.PersonRepository;
import com.safetyalerts.api.service.Interfaces.PersonServiceInterface;

import lombok.Data;

@Data
@Service
public class PersonServiceImpl implements PersonServiceInterface {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public Person createPerson(Person person) {
		Person savedPerson = personRepository.save(person);
		return savedPerson;
	}

	@Override
	public Iterable<Person> getPersons() {
		return personRepository.findAll();
	}

	@Override
	public Optional<Person>getPersonById(Integer id) {
		return personRepository.findById(id);
	}

	@Override
	public boolean deletePerson(String firstName, String lastName) {
		Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(firstName,lastName);
		if (optionalPerson.isPresent()) {
			personRepository.deleteById(optionalPerson.get().getId());
			return true;
		}
		return false;
	}

	@Override
	public  Iterable<Person> getChildrenByAddress(String address) {
		List<Person> persons = personRepository.findByAddress(address);
		if (!persons.isEmpty()) {
			 personRepository.findByAddress(address);

		}
		return persons;
		 
	}
}
