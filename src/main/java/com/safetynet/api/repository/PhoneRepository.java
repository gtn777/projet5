
package com.safetynet.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.api.entity.Phone;


@Repository
public interface PhoneRepository extends CrudRepository<Phone, Integer> {

    public Optional<Phone> findByPhoneNumber(String phoneNumber);

//    Iterable<String> findAllPhonePhoneNumberByPersonHomeStation(int station);
}
