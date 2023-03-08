package com.cvatos.chorifybackend.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cvatos.chorifybackend.model.Chore;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.model.Chore.ChoreStatus;

@Repository
public interface ChoreRepository extends CrudRepository<Chore, Integer> {
   
    Chore findById(int id);
    Chore findByChoreName(String choreName);
    List<Chore> findAllByChoreStatus(ChoreStatus choreStatus);
    List<Chore> findAllByDifficultyEstimator(int difficultyEstimator);
    List<Chore> findAllByDueDateBefore(Date dueDate);       // Does not retrieve entries on that due date; only before
    List<Chore> findAllByDueDate(Date dueDate);
    List<Chore> findByChoreAssignee(HouseMember houseMember);
    List<Chore> findByChoreAssigner(HouseManager houseManager);
    List<Chore> findByHouse(House house);
    void deleteById(int id);
} 
