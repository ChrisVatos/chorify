package com.cvatos.chorifybackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cvatos.chorifybackend.exception.ChorifyException;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;
import com.cvatos.chorifybackend.repository.HouseManagerRepository;
import com.cvatos.chorifybackend.repository.HouseMemberRepository;
import com.cvatos.chorifybackend.repository.HouseRepository;

import jakarta.transaction.Transactional;

@Service
public class HouseManagerService {

    @Autowired
    private HouseManagerRepository houseManagerRepository;

    @Autowired
    private HouseMemberRepository houseMemberRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseService houseService;

    @Transactional
    public HouseManager createHouseManager(HouseManager houseManagerToCreate, int houseID) {

        if(houseManagerRepository.findByEmailAddress(houseManagerToCreate.getEmailAddress()) != null) {
            throw new ChorifyException("House Manager with email address " + houseManagerToCreate.getEmailAddress() + " already exists." , HttpStatus.CONFLICT);
        } else if(houseManagerRepository.findByPhoneNumber(houseManagerToCreate.getPhoneNumber()) != null) {
            throw new ChorifyException("House Manager with phone number " + houseManagerToCreate.getPhoneNumber() + " already exists." , HttpStatus.CONFLICT);
        } else if(houseRepository.findById(houseID) == null) {
            throw new ChorifyException("House with id " + houseID + " does not exist." , HttpStatus.BAD_REQUEST);
        }

        int numberOfAllowedMembers = houseRepository.findById(houseID).getNumberOfMembers();
        int numberOfExistingHouseMembers = houseMemberRepository.findByHouse(houseRepository.findById(houseID)).size();
        int numberOfExistingHouseManagers = houseManagerRepository.findByHouse(houseRepository.findById(houseID)).size();
        int totalNumberOfMembers = numberOfExistingHouseMembers + numberOfExistingHouseManagers;

        if(totalNumberOfMembers == numberOfAllowedMembers) {
            houseService.incrementNumberOfHouseMembers(houseID);
        }
    
        houseManagerToCreate.setHouse(houseRepository.findById(houseID));
        return houseManagerRepository.save(houseManagerToCreate);
    }

    @Transactional
    public HouseManager getHouseManagerById(int id) {

        if(houseManagerRepository.findById(id) == null) {
            throw new ChorifyException("House Manager with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        } 

        return houseManagerRepository.findById(id);
    }

    @Transactional
    public HouseManager getHouseManagerByName(String name) {

        if(houseManagerRepository.findByName(name) == null) {
            throw new ChorifyException("House Manager with name " + name + " does not exist." , HttpStatus.NOT_FOUND);
        } 

        return houseManagerRepository.findByName(name);
    }

    @Transactional
    public List<HouseManager> getHouseManagersByHouse(int id) {

        House houseToFindHouseManagersFor = houseRepository.findById(id);
        List<HouseManager> houseManagers = houseManagerRepository.findByHouse(houseToFindHouseManagersFor);

        if(houseManagers.size() == 0) {
            throw new ChorifyException("No house managers exist for house with id " + id + "." , HttpStatus.NOT_FOUND);
        }

        return houseManagers;
    }

    @Transactional
    public List<HouseManager> getHouseManagersByType(HouseManagerType houseManagerType, int houseID) {

        List<HouseManager> houseManagers = houseManagerRepository.findAllByHouseManagerType(houseManagerType);
        List<HouseManager> houseManagersByHouse = houseManagers.stream().filter(manager -> manager.getId() == houseID).collect(Collectors.toList());

        if(houseManagersByHouse.size() == 0) {
            throw new ChorifyException("No house managers exist for house with id " + houseID + " that are of the specified type." , HttpStatus.NOT_FOUND);
        }

        return houseManagersByHouse;
    }

    @Transactional 
    public HouseManager updateHouseManagerName(int id, String newName) {
        
        HouseManager houseManagerToUpdate = houseManagerRepository.findById(id);
        HouseManager houseManagerWithNewNameExists = houseManagerRepository.findByName(newName);

        if(houseManagerToUpdate == null) {
            throw new ChorifyException("House Manager with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        } else if(houseManagerWithNewNameExists != null) {
            throw new ChorifyException("House Manager with name " + newName + " already exsists." , HttpStatus.CONFLICT);
        }

        houseManagerToUpdate.setName(newName);
        return houseManagerRepository.save(houseManagerToUpdate);
    }

    @Transactional 
    public HouseManager updateHouseManagerEmail(int id, String newEmail) {
        
        HouseManager houseManagerToUpdate = houseManagerRepository.findById(id);
        HouseManager houseManagerWithNewEmailExists = houseManagerRepository.findByEmailAddress(newEmail);

        if(houseManagerToUpdate == null) {
            throw new ChorifyException("House Manager with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        } else if(houseManagerWithNewEmailExists != null) {
            throw new ChorifyException("House Manager with name " + newEmail + " already exsists." , HttpStatus.CONFLICT);
        }

        houseManagerToUpdate.setEmailAddress(newEmail);
        return houseManagerRepository.save(houseManagerToUpdate);
    }

    @Transactional 
    public HouseManager updateHouseManagerPhoneNumber(int id, String newNumber) {
        
        HouseManager houseManagerToUpdate = houseManagerRepository.findById(id);
        HouseManager houseManagerWithNewNumberExists = houseManagerRepository.findByPhoneNumber(newNumber);

        if(houseManagerToUpdate == null) {
            throw new ChorifyException("House Manager with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        } else if(houseManagerWithNewNumberExists != null) {
            throw new ChorifyException("House Manager with name " + newNumber + " already exsists." , HttpStatus.CONFLICT);
        }

        houseManagerToUpdate.setPhoneNumber(newNumber);
        return houseManagerRepository.save(houseManagerToUpdate);
    }

    @Transactional 
    public void deleteHouseManager(int id) {

        HouseManager houseManagerToDelete = houseManagerRepository.findById(id);

        if(houseManagerToDelete == null) {
            throw new ChorifyException("House Manager with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        }

        int houseId = houseManagerToDelete.getHouse().getId();
        houseService.decrementNumberOfHouseMembers(houseId);
        houseManagerRepository.deleteById(id);
    }




    
}
