package com.safetyalerts.api.service.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetyalerts.api.model.MedicalRecords;
import com.safetyalerts.api.repository.MedicalRecordsRepository;
import com.safetyalerts.api.service.Interfaces.MedicalRecordsInterface;

import lombok.Data;

@Data
@Service
public class MedicalRecordsImpl implements MedicalRecordsInterface {

	@Autowired
	MedicalRecordsRepository medicalRecordsRepository;

	@Override
	public MedicalRecords createMedicalRecords(MedicalRecords medicalRecords) {
		MedicalRecords savedMedicalRecord = medicalRecordsRepository.save(medicalRecords);
		return savedMedicalRecord;
	}

	@Override
	public Iterable<MedicalRecords> getMedicalRecords() {
		return medicalRecordsRepository.findAll();
	}

	@Override
	public Optional<MedicalRecords> getMedicalRecordsById(Integer id) {
		return medicalRecordsRepository.findById(id);
	}

	// Delete medicalRecord using firstName and lastName
	@Override
	public boolean deleteMedicalRecords(String firstName, String lastName) {
		Optional<MedicalRecords> optionalMedicalrecord = medicalRecordsRepository.findByFirstNameAndLastName(firstName,
				lastName);
		if (optionalMedicalrecord.isPresent()) {
			medicalRecordsRepository.deleteById(optionalMedicalrecord.get().getId());
			return true;

		} else
			return false;
	}

}
