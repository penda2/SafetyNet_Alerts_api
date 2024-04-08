CREATE DATABASE safetyalerts;
use safetyalerts;

CREATE TABLE person (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  firstName VARCHAR(255) NOT NULL,
  lastName VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  zip INT NOT NULL,
  phone LONG NOT NULL,
  email VARCHAR(255) NOT NULL,
  firestationid INT,
  FOREIGN KEY (firestaionid) REFERENCES firestaion(id),
  medicalrecordsid INT,
  FOREIGN KEY (medicalrecordsid) REFERENCES medicalrecords(id)
);

CREATE TABLE firestation (
  id INT AUTO_INCREMENT PRIMARY KEY,
  address VARCHAR(100) NOT NULL,
  station VARCHAR(100) NOT NULL
);

CREATE TABLE medicalrecords (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  firstName VARCHAR(255),
  lastName VARCHAR(255),
  birthdate DATE,
  medications TEXT,
  allergies TEXT
);
