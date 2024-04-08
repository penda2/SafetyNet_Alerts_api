package com.safetyalerts.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetyalerts.api.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer>{

	Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

	List<Person> findByAddress(String address);
}