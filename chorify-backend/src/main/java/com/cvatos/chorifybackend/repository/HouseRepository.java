package com.cvatos.chorifybackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cvatos.chorifybackend.model.House;

@Repository
public interface HouseRepository extends CrudRepository<House, Integer> {

    House findById(int id);
    House findByHouseName(String houseName);
    void deleteById(int id);
}
