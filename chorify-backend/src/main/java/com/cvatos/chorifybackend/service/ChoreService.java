package com.cvatos.chorifybackend.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cvatos.chorifybackend.exception.ChorifyException;
import com.cvatos.chorifybackend.model.Chore;
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.model.Chore.ChoreStatus;
import com.cvatos.chorifybackend.repository.ChoreRepository;
import com.cvatos.chorifybackend.repository.HouseManagerRepository;
import com.cvatos.chorifybackend.repository.HouseMemberRepository;
import com.cvatos.chorifybackend.repository.HouseRepository;

import jakarta.transaction.Transactional;

@Service
public class ChoreService {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private HouseManagerRepository houseManagerRepository;

    @Autowired
    private HouseMemberRepository houseMemberRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Transactional
    public Chore createChore(Chore choreToCreate, int choreAssignerID, int choreAssigneeID, int houseID) {

        HouseManager choreAssigner = houseManagerRepository.findById(choreAssignerID);
        HouseMember choreAssignee = houseMemberRepository.findById(choreAssigneeID);

        if(choreAssigner == null) {
            throw new ChorifyException("Manager not found.", HttpStatus.NOT_FOUND);
        }  

        if(choreAssignee == null) {
            throw new ChorifyException("Member not found.", HttpStatus.NOT_FOUND);
        }

        if(choreAssigner.getHouse().getId() != choreAssignee.getHouse().getId()) {
            throw new ChorifyException("Manager and member not of the same house.", HttpStatus.CONFLICT);
        }

        choreToCreate.setChoreAssigner(houseManagerRepository.findById(choreAssignerID));
        choreToCreate.setChoreAssignee(houseMemberRepository.findById(choreAssigneeID));
        choreToCreate.setHouse(houseRepository.findById(houseID));

        return choreRepository.save(choreToCreate); 
    }

    @Transactional
    public Chore getChoreById(int id) {

        Chore choreToRetrieve = choreRepository.findById(id);

        if(choreToRetrieve == null) {
            throw new ChorifyException("Chore with id " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }

        return choreToRetrieve;
    }

    @Transactional
    public Chore getChoreByName(String name) {

        Chore choreToRetrieve = choreRepository.findByChoreName(name);

        if(choreToRetrieve == null) {
            throw new ChorifyException("Chore with id " + name + " does not exist.", HttpStatus.NOT_FOUND);
        }

        return choreToRetrieve;
    }

    @Transactional
    public List<Chore> getChoresByStatus(ChoreStatus status, int houseID) {
        
        List<Chore> choresByStatus = choreRepository.findAllByChoreStatus(status);
        List<Chore> choresByStatusAndHouse = choresByStatus.stream().filter(chore -> chore.getHouse().getId() == houseID).collect(Collectors.toList());

        if(choresByStatusAndHouse.size() == 0) {
            throw new ChorifyException("No chores exist for house with id " + houseID + " that are of the specified status.", HttpStatus.NOT_FOUND);
        }

        return choresByStatusAndHouse;
    }

    @Transactional
    public List<Chore> getChoresByDifficulty(int difficultyEstimator, int houseID) {
        
        List<Chore> choresByDifficulty = choreRepository.findAllByDifficultyEstimator(difficultyEstimator);
        List<Chore> choresByDifficultyAndHouse = choresByDifficulty.stream().filter(chore -> chore.getHouse().getId() == houseID).collect(Collectors.toList());

        if(choresByDifficultyAndHouse.size() == 0) {
            throw new ChorifyException("No chores exist for house with id " + houseID + " that are of the specified difficulty.", HttpStatus.NOT_FOUND);
        }

        return choresByDifficultyAndHouse;
    }

    @Transactional
    public List<Chore> getChoresDueOnSpecificDate(Date date, int houseID) {
        
        List<Chore> choresByDate = choreRepository.findAllByDueDate(date);
        List<Chore> choresByDateAndHouse = choresByDate.stream().filter(chore -> chore.getHouse().getId() == houseID).collect(Collectors.toList());

        if(choresByDateAndHouse.size() == 0) {
            throw new ChorifyException("No chores exist for house with id " + houseID + " that are of the specified due date.", HttpStatus.NOT_FOUND);
        }

        return choresByDateAndHouse;
    }

    @Transactional
    public List<Chore> getChoresDueOnOrBeforeSpecificDate(Date date, int houseID) {
        
        List<Chore> choresDueBeforeDate = choreRepository.findAllByDueDateBefore(date);
        List<Chore> choresDueOnSpecificDate = choreRepository.findAllByDueDate(date);
        List<Chore> choresDueOnOrBeforeDate = new ArrayList<Chore>(choresDueBeforeDate);
        choresDueOnOrBeforeDate.addAll(choresDueOnSpecificDate);

        List<Chore> choresByDateAndHouse = choresDueOnOrBeforeDate.stream().filter(chore -> chore.getHouse().getId() == houseID).collect(Collectors.toList());

        if(choresByDateAndHouse.size() == 0) {
            throw new ChorifyException("No chores exist for house with id " + houseID + " that are of the specified due date.", HttpStatus.NOT_FOUND);
        }

        return choresByDateAndHouse;
    }

    @Transactional
    public List<Chore> getChoresByAssigner(int choreAssignerID) {

        HouseManager choreAssigner = houseManagerRepository.findById(choreAssignerID);

        if(choreAssigner == null) {
            throw new ChorifyException("House manager does not exist.", HttpStatus.NOT_FOUND);
        }

        List<Chore> choresByAssigner = choreRepository.findByChoreAssigner(choreAssigner);

        if(choresByAssigner.size() == 0) {
            throw new ChorifyException("No chores exist that are assigned by " + houseManagerRepository.findById(choreAssignerID).getName(), HttpStatus.NOT_FOUND);
        }

        return choresByAssigner;
    }

    @Transactional
    public List<Chore> getChoresByAssignee(int choreAssigneeID) {

        HouseMember choreAssignee = houseMemberRepository.findById(choreAssigneeID);

        if(choreAssignee == null) {
            throw new ChorifyException("House member does not exist.", HttpStatus.NOT_FOUND);
        }

        List<Chore> choresByAssignee = choreRepository.findByChoreAssignee(houseMemberRepository.findById(choreAssigneeID));

        if(choresByAssignee.size() == 0) {
            throw new ChorifyException("No chores exist that are assigned to " + houseMemberRepository.findById(choreAssigneeID).getName(), HttpStatus.NOT_FOUND);
        }

        return choresByAssignee;
    }

    @Transactional
    public List<Chore> getChoresByHouse(int houseID) {

        List<Chore> choresByHouse = choreRepository.findByHouse(houseRepository.findById(houseID));

        if(choresByHouse.size() == 0) {
            throw new ChorifyException("No chores exist for this house.", HttpStatus.NOT_FOUND);
        }

        return choresByHouse;
    }

    @Transactional
    public Chore updateChoreName(int choreID, String newName) {

        Chore choreToUpdate = choreRepository.findById(choreID);

        if(choreToUpdate == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        choreToUpdate.setChoreName(newName);
        return choreRepository.save(choreToUpdate);
    }

    @Transactional
    public Chore updateChoreDescription(int choreID, String newDescription) {

        Chore choreToUpdate = choreRepository.findById(choreID);

        if(choreToUpdate == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        choreToUpdate.setChoreDescription(newDescription);
        return choreRepository.save(choreToUpdate);
    }

    @Transactional
    public Chore updateChoreStatus(int choreID, ChoreStatus newStatus) {

        Chore choreToUpdate = choreRepository.findById(choreID);

        if(choreToUpdate == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        choreToUpdate.setChoreStatus(newStatus);
        return choreRepository.save(choreToUpdate);
    }

    @Transactional
    public Chore updateDifficulty(int choreID, int newDifficulty) {

        Chore choreToUpdate = choreRepository.findById(choreID);

        if(choreToUpdate == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        choreToUpdate.setDifficultyEstimator(newDifficulty);
        return choreRepository.save(choreToUpdate);
    }

    @Transactional
    public Chore updateDueDate(int choreID, Date newDate) {

        Chore choreToUpdate = choreRepository.findById(choreID);

        if(choreToUpdate == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        choreToUpdate.setDueDate(newDate);
        return choreRepository.save(choreToUpdate);
    }

    @Transactional
    public Chore updateChoreAssigner(int choreID, int choreAssignerID) {

        HouseManager choreAssigner = houseManagerRepository.findById(choreAssignerID);

        if(choreAssigner == null) {
            throw new ChorifyException("House manager does not exist.", HttpStatus.NOT_FOUND);
        }

        Chore choreToUpdate = choreRepository.findById(choreID);

        if(choreToUpdate == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        if(choreAssigner.getHouse().getId() != choreToUpdate.getHouse().getId()) {
            throw new ChorifyException("Manager and member not of the same house.", HttpStatus.CONFLICT);
        }

        choreToUpdate.setChoreAssigner(houseManagerRepository.findById(choreAssignerID));
        return choreRepository.save(choreToUpdate);
    }

    @Transactional
    public Chore updateChoreAssignee(int choreID, int choreAssigneeID) {

        HouseMember choreAssignee = houseMemberRepository.findById(choreAssigneeID);

        if(choreAssignee == null) {
            throw new ChorifyException("House member does not exist.", HttpStatus.NOT_FOUND);
        }

        Chore choreToUpdate = choreRepository.findById(choreID);

        if(choreToUpdate == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        if(choreAssignee.getHouse().getId() != choreToUpdate.getHouse().getId()) {
            throw new ChorifyException("Manager and member not of the same house.", HttpStatus.CONFLICT);
        }

        choreToUpdate.setChoreAssignee(houseMemberRepository.findById(choreAssigneeID));
        return choreRepository.save(choreToUpdate);
    }

    @Transactional 
    public void deleteChore(int id) {

        Chore choreToDelete = choreRepository.findById(id);

        if(choreToDelete == null) {
            throw new ChorifyException("Chore does not exist.", HttpStatus.NOT_FOUND);
        }

        choreRepository.deleteById(id);
    }


}
