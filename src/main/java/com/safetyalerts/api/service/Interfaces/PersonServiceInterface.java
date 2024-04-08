package com.safetyalerts.api.service.Interfaces;

import java.util.Optional;

import com.safetyalerts.api.model.Person;

public interface PersonServiceInterface {
	public Person createPerson(Person person);
	public Iterable<Person>getPersons();
	public Optional<Person>getPersonById(Integer id);
	boolean deletePerson(String firstName, String lastName);
	public Iterable<Person> getChildrenByAddress(String address);
}
