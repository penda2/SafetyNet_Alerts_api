package com.safetyalerts.api.service.Interfaces;

import java.util.Optional;

import com.safetyalerts.api.model.MedicalRecords;

public interface MedicalRecordsInterface {
	public MedicalRecords createMedicalRecords(MedicalRecords medicalRecords);
	public Iterable<MedicalRecords> getMedicalRecords();
	public Optional<MedicalRecords> getMedicalRecordsById(Integer id);
	public boolean deleteMedicalRecords(String firstName, String lastName);
}
