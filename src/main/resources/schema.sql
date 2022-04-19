DROP TABLE IF EXISTS emails;
CREATE TABLE emails
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   email VARCHAR (250) NOT NULL
);
DROP TABLE IF EXISTS allergies;
CREATE TABLE allergies
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   allergie VARCHAR (250) NOT NULL
);
DROP TABLE IF EXISTS persons;
CREATE TABLE persons
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   first_name VARCHAR (250) NOT NULL,
   last_name VARCHAR (250) NOT NULL,
   birth_date Date NOT NULL,
   email_id INT,
   FOREIGN KEY (email_id) REFERENCES emails (id)
);
DROP TABLE IF EXISTS medications;
CREATE TABLE medications
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   medication VARCHAR (250) NOT NULL
);
DROP TABLE IF EXISTS posologies;
CREATE TABLE posologies
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   posology VARCHAR (250) NOT NULL
);
DROP TABLE IF EXISTS treatments;
CREATE TABLE treatments
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   medication_id INT NOT NULL,
   posology_id INT NOT NULL,
   FOREIGN KEY (medication_id) REFERENCES medications (id),
   FOREIGN KEY (posology_id) REFERENCES posologies (id)
);
DROP TABLE IF EXISTS person_treatment;
CREATE TABLE person_treatment
(
   person_id INT NOT NULL,
   treatment_id INT NOT NULL,
   FOREIGN KEY (person_id) REFERENCES persons (id),
   FOREIGN KEY (treatment_id) REFERENCES treatments (id),
   PRIMARY KEY
   (
      person_id,
      treatment_id
   )
);
DROP TABLE IF EXISTS homes;
CREATE TABLE homes
(
   id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   address VARCHAR (250) NOT NULL,
   city VARCHAR (250) NOT NULL,
   zip VARCHAR (250) NOT NULL
);
DROP TABLE IF EXISTS fire_stations;
CREATE TABLE fire_stations
(
   id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   station VARCHAR (250) NOT NULL
);