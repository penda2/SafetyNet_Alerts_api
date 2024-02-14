package com.safetyalerts.api.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private Integer zip;
	private Long phone;
	private String email;
}
