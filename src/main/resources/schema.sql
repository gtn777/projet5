DROP TABLE IF EXISTS email;
CREATE TABLE email
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   email_address VARCHAR (250) NOT NULL,
   CONSTRAINT UNIQUE_email_address UNIQUE (email_address)
);
DROP TABLE IF EXISTS phone;
CREATE TABLE phone
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   phone_number VARCHAR (250) NOT NULL,
   CONSTRAINT UNIQUE_phone_number UNIQUE (phone_number)
);
DROP TABLE IF EXISTS home;
CREATE TABLE home
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   address VARCHAR (250) NOT NULL,
   city VARCHAR (250) NOT NULL,
   zip VARCHAR (250) NOT NULL,
   fire_station INT,
   CONSTRAINT UNIQUE_home UNIQUE
   (
      address,
      city,
      zip
   )
);
DROP TABLE IF EXISTS allergie;
CREATE TABLE allergie
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   allergie_name VARCHAR (250) NOT NULL,
   CONSTRAINT UNIQUE_allergie UNIQUE (allergie_name)
);
DROP TABLE IF EXISTS medication;
CREATE TABLE medication
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   medication_name VARCHAR (250) NOT NULL,
   CONSTRAINT UNIQUE_medication UNIQUE (medication_name)
);
DROP TABLE IF EXISTS person;
CREATE TABLE person
(
   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   first_name VARCHAR (250) NOT NULL,
   last_name VARCHAR (250) NOT NULL,
   email_id INT NOT NULL,
   phone_id INT NOT NULL,
   home_id INT NOT NULL,
   FOREIGN KEY (email_id) REFERENCES email (id),
   FOREIGN KEY (phone_id) REFERENCES phone (id),
   FOREIGN KEY (home_id) REFERENCES home (id)
);
DROP TABLE IF EXISTS medical_record;
CREATE TABLE medical_record
(
   person_id INT PRIMARY KEY NOT NULL,
   birthdate DATE NOT NULL,
   FOREIGN KEY (person_id) REFERENCES person (id)
);
DROP TABLE IF EXISTS record_allergie;
CREATE TABLE record_allergie
(
   record_id INT NOT NULL,
   allergie_id INT NOT NULL,
   PRIMARY KEY
   (
      record_id,
      allergie_id
   ),
   FOREIGN KEY (record_id) REFERENCES medical_record (person_id),
   FOREIGN KEY (allergie_id) REFERENCES allergie (id)
);
DROP TABLE IF EXISTS record_medication;
CREATE TABLE record_medication
(
   record_id INT NOT NULL,
   medication_id INT NOT NULL,
   PRIMARY KEY
   (
      record_id,
      medication_id
   ),
   FOREIGN KEY (record_id) REFERENCES medical_record (person_id),
   FOREIGN KEY (medication_id) REFERENCES medication (id)
);