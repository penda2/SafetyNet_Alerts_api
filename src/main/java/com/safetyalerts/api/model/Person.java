package com.safetyalerts.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "persons")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Column(name = "zip")
	private Long zip;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;

	@JsonBackReference(value = "person-firestation")
	@ManyToOne
	@JoinColumn(name = "address", referencedColumnName = "address", insertable = false, updatable = false)
	private FireStation fireStation;

	@JsonBackReference(value = "person-medicalrecords")
	@ManyToOne
	@JoinColumn(name = "firstName", referencedColumnName = "firstName", insertable = false, updatable = false)
	private MedicalRecords medicalRecords;
}
