package com.safetynet.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.api.entity.Home;

@Repository
public interface HomeRepository extends CrudRepository<Home, Integer> {

    public Optional<Home> findByAddressAndCity(String address, String city);

    public Optional<Home> findByAddress(String address);

    public Iterable<Home> findAllByStation(int station);

}
