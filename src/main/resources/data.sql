CREATE DATABASE safetyalerts;
use safetyalerts;

CREATE TABLE person (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  firstName VARCHAR(100) NOT NULL,
  lastName VARCHAR(100) NOT NULL,
  address VARCHAR(100) NOT NULL,
  city VARCHAR(100) NOT NULL,
  zip INT NOT NULL,
  phone LONG NOT NULL,
  email VARCHAR(100) NOT NULL
);