
package com.safetynet.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.entity.Allergie;


public interface AllergieRepository extends CrudRepository<Allergie, Integer> {

    Optional<Allergie> findByAllergieString(String allergieString);

}
