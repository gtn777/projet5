package com.safetynet.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.api.entity.Email;
import com.safetynet.api.entity.Home;
import com.safetynet.api.entity.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Iterable<Person> findByEmailEmailAddress(String emailAddress);

    Iterable<Person> findByEmail(Email email);

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

    void deleteByFirstNameAndLastName(String firstName, String lastName);
    
    int findIdByFirstNameAndLastName(String firstName, String lastName);
    
    Iterable<Person> findAllByHomeStation(int stationNumber);

    Iterable<Person> findAllByFirstNameAndLastName(String firstName, String lastName);

    Iterable<Person> findByHomeStation(int station);
    
    
}