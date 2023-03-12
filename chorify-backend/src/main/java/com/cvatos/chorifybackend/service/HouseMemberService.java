package com.cvatos.chorifybackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cvatos.chorifybackend.exception.ChorifyException;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.repository.HouseMemberRepository;
import com.cvatos.chorifybackend.repository.HouseRepository;

import jakarta.transaction.Transactional;

@Service
public class HouseMemberService {

    @Autowired
    private HouseMemberRepository houseMemberRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Transactional
    public HouseMember createHouseMember(HouseMember houseMemberToCreate, int houseID) {

        if(houseMemberRepository.findByEmailAddress(houseMemberToCreate.getEmailAddress()) != null) {
            throw new ChorifyException("House Member with email address " + houseMemberToCreate.getEmailAddress() + " already exists." , HttpStatus.CONFLICT);
        } else if(houseMemberRepository.findByPhoneNumber(houseMemberToCreate.getPhoneNumber()) != null) {
            throw new ChorifyException("House Member with phone number " + houseMemberToCreate.getPhoneNumber() + " already exists." , HttpStatus.CONFLICT);
        } else if(houseRepository.findById(houseID) == null) {
            throw new ChorifyException("House with id " + houseID + " does not exist." , HttpStatus.BAD_REQUEST);
        }
    
        houseMemberToCreate.setHouse(houseRepository.findById(houseID));
        return houseMemberRepository.save(houseMemberToCreate);
    }

    @Transactional
    public HouseMember getHouseMemberById(int id) {

        if(houseMemberRepository.findById(id) == null) {
            throw new ChorifyException("House Member with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        } 

        return houseMemberRepository.findById(id);
    }

    @Transactional
    public HouseMember getHouseMemberByName(String name) {

        if(houseMemberRepository.findByName(name) == null) {
            throw new ChorifyException("House Member with name " + name + " does not exist." , HttpStatus.NOT_FOUND);
        } 

        return houseMemberRepository.findByName(name);
    }

    @Transactional
    public List<HouseMember> getHouseMembersByHouse(int id) {

        House houseToFindHouseMembersFor = houseRepository.findById(id);
        List<HouseMember> houseMembers = houseMemberRepository.findByHouse(houseToFindHouseMembersFor);

        if(houseMembers.size() == 0) {
            throw new ChorifyException("No house members exist for this house." , HttpStatus.NOT_FOUND);
        }

        return houseMembers;
    }

    @Transactional 
    public HouseMember updateHouseMemberName(int id, String newName) {
        
        HouseMember houseMemberToUpdate = houseMemberRepository.findById(id);
        HouseMember houseMemberWithNewNameExists = houseMemberRepository.findByName(newName);

        if(houseMemberToUpdate == null) {
            throw new ChorifyException("House Member with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        } else if(houseMemberWithNewNameExists != null) {
            throw new ChorifyException("House Member with name " + newName + " already exsists." , HttpStatus.CONFLICT);
        }

        houseMemberToUpdate.setName(newName);
        return houseMemberRepository.save(houseMemberToUpdate);
    }

    @Transactional 
    public HouseMember updateHouseMemberEmail(int id, String newEmail) {
        
        HouseMember houseMemberToUpdate = houseMemberRepository.findById(id);
        HouseMember houseMemberWithNewEmailExists = houseMemberRepository.findByEmailAddress(newEmail);

        if(houseMemberToUpdate == null) {
            throw new ChorifyException("House Member with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        }  else if(houseMemberWithNewEmailExists != null) {
            throw new ChorifyException("House Member with email " + newEmail + " already exsists." , HttpStatus.CONFLICT);
        }

        houseMemberToUpdate.setEmailAddress(newEmail);
        return houseMemberRepository.save(houseMemberToUpdate);
    }

    @Transactional 
    public HouseMember updateHouseMemberPhoneNumber(int id, String newPhoneNumber) {
        
        HouseMember houseMemberToUpdate = houseMemberRepository.findById(id);
        HouseMember houseMemberWithNewEmailExists = houseMemberRepository.findByPhoneNumber(newPhoneNumber);

        if(houseMemberToUpdate == null) {
            throw new ChorifyException("House Member with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        }  else if(houseMemberWithNewEmailExists != null) {
            throw new ChorifyException("House Member with phone number " + newPhoneNumber + " already exsists." , HttpStatus.CONFLICT);
        }

        houseMemberToUpdate.setPhoneNumber(newPhoneNumber);
        return houseMemberRepository.save(houseMemberToUpdate);
    }

    @Transactional 
    public void deleteHouseMember(int id) {

        HouseMember houseMemberToDelete = houseMemberRepository.findById(id);

        if(houseMemberToDelete == null) {
            throw new ChorifyException("House Member with id " + id + " does not exist." , HttpStatus.NOT_FOUND);
        }

        houseMemberRepository.deleteById(id);
    }
}
