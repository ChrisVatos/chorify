package com.cvatos.chorifybackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;

@Repository
public interface HouseManagerRepository extends CrudRepository<HouseManager, Integer>  {

    HouseManager findById(int id);
    HouseManager findByEmailAddress(String emailAddress);
    HouseManager findByName(String name);
    List<HouseManager> findAllByHouseManagerType(HouseManagerType houseManagerType);
    List<HouseManager> findByHouse(House house);
}
