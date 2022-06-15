
package com.safetynet.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.api.entity.Email;


@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {

    public Optional<Email> findByEmailAddress(String emailAddress);

    public Iterable<Email> findAllByPersonsHomeCity(String city);

}
