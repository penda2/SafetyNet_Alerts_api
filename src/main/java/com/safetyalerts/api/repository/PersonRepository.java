package com.safetyalerts.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetyalerts.api.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer>{

}