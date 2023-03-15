package com.cvatos.chorifybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cvatos.chorifybackend.exception.ChorifyException;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.repository.HouseRepository;

import jakarta.transaction.Transactional;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseReposiory;

    @Transactional
    public House createHouse(House house) {
        
        if(houseReposiory.findByHouseName(house.getHouseName()) != null) {
            throw new ChorifyException("House with name " + house.getHouseName() + " already exists." , HttpStatus.CONFLICT);
        } 

        return houseReposiory.save(house);
    }

    @Transactional
    public House getHouseById(int id) {

        House retrievedHouse = houseReposiory.findById(id);

        if(retrievedHouse == null) {
            throw new ChorifyException("House with ID of " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }

        return retrievedHouse;
    }

    @Transactional
    public House getHouseByHouseName(String name) {

        House retrievedHouse = houseReposiory.findByHouseName(name);

        if(retrievedHouse == null) {
            throw new ChorifyException("House with name " + name + " does not exist.", HttpStatus.NOT_FOUND);
        }

        return retrievedHouse;
    }

    @Transactional
    public House updateHouseName(int id, String newName) {
        
        House houseToUpdate = houseReposiory.findById(id);
        House houseWithNewNameExists = houseReposiory.findByHouseName(newName);

        if(houseToUpdate == null) {
            throw new ChorifyException("House with ID of " + id + " does not exist.", HttpStatus.NOT_FOUND);
        } else if(houseWithNewNameExists != null) {
            throw new ChorifyException("House with name " + newName + " already exists.", HttpStatus.BAD_REQUEST);
        }

        houseToUpdate.setHouseName(newName);
        return houseReposiory.save(houseToUpdate);
    }

    @Transactional
    public House incrementNumberOfHouseMembers(int id) {
        
        House houseToUpdate = houseReposiory.findById(id);

        if(houseToUpdate == null) {
            throw new ChorifyException("House with ID of " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }

        int newNumberOfMembers = houseToUpdate.getNumberOfMembers() + 1;
        houseToUpdate.setNumberOfMembers(newNumberOfMembers);
        return houseReposiory.save(houseToUpdate);
    }

    @Transactional
    public House decrementNumberOfHouseMembers(int id) {
        
        House houseToUpdate = houseReposiory.findById(id);

        if(houseToUpdate == null) {
            throw new ChorifyException("House with ID of " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }

        int newNumberOfMembers = houseToUpdate.getNumberOfMembers() - 1;
        houseToUpdate.setNumberOfMembers(newNumberOfMembers);
        return houseReposiory.save(houseToUpdate);
    }

    @Transactional
    public void deleteHouse(int id) {

        House houseToDelete = houseReposiory.findById(id);

        if(houseToDelete == null) {
            throw new ChorifyException("House with ID of " + id + " does not exist.", HttpStatus.NOT_FOUND);
        } 

        houseReposiory.deleteById(id);
    }
}
