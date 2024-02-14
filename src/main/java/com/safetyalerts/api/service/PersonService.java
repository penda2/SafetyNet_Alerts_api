package com.safetyalerts.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyalerts.api.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	

}
