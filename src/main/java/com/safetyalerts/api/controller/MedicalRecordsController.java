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
import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;

@RestController
public class MedicalRecordsController {

	Logger logger = LogManager.getLogger(MedicalRecordsController.class);

	private MedicalRecordsInterface medicalRecordsInterface;

	@Autowired
	public MedicalRecordsController(MedicalRecordsInterface theMedicalRecordsInterface) {
		medicalRecordsInterface = theMedicalRecordsInterface;
	} 

	@GetMapping("/medicalrecords")
	public Iterable<MedicalRecords> getMedicalRecords() {
		return medicalRecordsInterface.getMedicalRecords();
	}

	@GetMapping("/medicalrecords/{id}")
	public MedicalRecords getMedicalRecords(@PathVariable("id") Integer id) {
		Optional<MedicalRecords> optionalMedicalRecords = medicalRecordsInterface.getMedicalRecordsById(id);
		if (optionalMedicalRecords.isPresent()) {
			return optionalMedicalRecords.get();
		} else {
			return null;
		}
	}

	@PostMapping("/medicalrecords/create")
	public MedicalRecords createMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
		logger.info("Received MedicalRecords object: {}", medicalRecords);
		return medicalRecordsInterface.createMedicalRecords(medicalRecords);
	}

	@PutMapping("/medicalrecords/{id}")
	public MedicalRecords updateMedicalRecords(@PathVariable Integer id, @RequestBody MedicalRecords theMedicalRecord) {
		Optional<MedicalRecords> optionalMedicalRecord = medicalRecordsInterface.getMedicalRecordsById(id);
		if (!optionalMedicalRecord.isPresent()) {
			throw new RuntimeException("The optionalMedicalRecord to update doesn't exist");
		}
		MedicalRecords existingMedicalRecords = optionalMedicalRecord.get();
		existingMedicalRecords.setBirthDate(theMedicalRecord.getBirthDate());
		existingMedicalRecords.setMedications(theMedicalRecord.getMedications());
		existingMedicalRecords.setAllergies(theMedicalRecord.getAllergies());

		return medicalRecordsInterface.createMedicalRecords(existingMedicalRecords);
	}

	@DeleteMapping("/medicalrecords/{firstName}/{lastName}")
	public ResponseEntity<Void> deleteMedicalrecords(@PathVariable String firstName, @PathVariable String lastName) {
		boolean deleted = medicalRecordsInterface.deleteMedicalRecords(firstName, lastName);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build(); 
		}
	}
}
