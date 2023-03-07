package com.cvatos.chorifybackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseMember;

@Repository
public interface HouseMemberRepository extends CrudRepository<HouseMember, Integer>  {

    HouseMember findById(int id);
    HouseMember findByEmailAddress(String emailAddress);
    HouseMember findByName(String name);
    List<HouseMember> findByHouse(House house);
    
}
