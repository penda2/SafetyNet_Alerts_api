package com.safetyalerts.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetyalerts.api.model.MedicalRecords;

@Repository
public interface MedicalRecordsRepository extends CrudRepository<MedicalRecords, Integer>{
	Optional<MedicalRecords> findByFirstNameAndLastName(String firstName, String lastName);

}
