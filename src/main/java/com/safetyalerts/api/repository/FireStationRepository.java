package com.safetyalerts.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetyalerts.api.model.FireStation;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Integer>{
	Optional<FireStation> findByStation(Long station);


}
