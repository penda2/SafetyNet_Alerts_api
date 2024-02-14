package com.safetyalerts.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetyalerts.api.model.Person;
import com.safetyalerts.api.repository.PersonRepository;

@RestController
public class PersonController {
	
	@Autowired
	 PersonRepository personRepository;
	
	@GetMapping("/person")
	public Iterable<Person> getPerson(){
		return personRepository.findAll();
	}
	
	@PostMapping("/person")
	public Person createPerson (@RequestBody Person person) {
		return personRepository.save(person);
	}

}
