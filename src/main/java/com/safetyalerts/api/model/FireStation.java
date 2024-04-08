package com.safetyalerts.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "firestation")
public class FireStation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "firestation_id")
	private Integer firestationId;
	@Column(name = "address")
	private String address;
	@Column(name = "station")
	private Long station;

	@JsonManagedReference(value = "person-firestation")
	@OneToMany(mappedBy = "fireStation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Person> persons;
}
