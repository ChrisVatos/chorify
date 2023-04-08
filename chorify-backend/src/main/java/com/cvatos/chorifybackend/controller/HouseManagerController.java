package com.cvatos.chorifybackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.service.HouseManagerService;
import com.dto.HouseManagerRequestDto;
import com.dto.HouseManagerResponseDto;
import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
public class HouseManagerController {
    
    @Autowired
    private HouseManagerService houseManagerService;

    @PostMapping("/managers/newManager")
    public ResponseEntity<HouseManagerResponseDto> createManager(@RequestBody HouseManagerRequestDto houseManagerRequest) {
        
        HouseManager managerToCreate = new HouseManager(
            houseManagerRequest.getName(), 
            houseManagerRequest.getPhoneNumber(), 
            houseManagerRequest.getEmailAddress(), 
            houseManagerRequest.getHouseManagerType()
        );

        HouseManager createdManager = houseManagerService.createHouseManager(managerToCreate, houseManagerRequest.getHouseID());

        HouseManagerResponseDto createdManagerResponse = new HouseManagerResponseDto(
            createdManager.getId(),
            createdManager.getName(),
            createdManager.getPhoneNumber(),
            createdManager.getEmailAddress(),
            createdManager.getHouseManagerType(),
            createdManager.getChores(),
            createdManager.getHouse().getId()
        );

        return new ResponseEntity<HouseManagerResponseDto>(createdManagerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/managers/id/{id}")
    public ResponseEntity<HouseManagerResponseDto> getManagerById(@PathVariable int id) {

        HouseManager retrievedManager = houseManagerService.getHouseManagerById(id);

        HouseManagerResponseDto retrievedManagerResponse = new HouseManagerResponseDto(
            retrievedManager.getId(),
            retrievedManager.getName(),
            retrievedManager.getPhoneNumber(),
            retrievedManager.getEmailAddress(),
            retrievedManager.getHouseManagerType(),
            retrievedManager.getChores(),
            retrievedManager.getHouse().getId()
        );

        return new ResponseEntity<HouseManagerResponseDto>(retrievedManagerResponse, HttpStatus.OK);
    }

    @GetMapping("/managers/name/{name}")
    public ResponseEntity<HouseManagerResponseDto> getManagerByName(@PathVariable String name) {

        HouseManager retrievedManager = houseManagerService.getHouseManagerByName(name);

        HouseManagerResponseDto retrievedManagerResponse = new HouseManagerResponseDto(
            retrievedManager.getId(),
            retrievedManager.getName(),
            retrievedManager.getPhoneNumber(),
            retrievedManager.getEmailAddress(),
            retrievedManager.getHouseManagerType(),
            retrievedManager.getChores(),
            retrievedManager.getHouse().getId()
        );

        return new ResponseEntity<HouseManagerResponseDto>(retrievedManagerResponse, HttpStatus.OK);
    }

    @GetMapping("/managers/house/{id}")
    public ResponseEntity<List<HouseManagerResponseDto>> getManagersByHouse(@PathVariable int id) {
        
        List<HouseManager> houseManagersByHouse = houseManagerService.getHouseManagersByHouse(id);
        List<HouseManagerResponseDto> houseManagersByHouseResponse = new ArrayList<HouseManagerResponseDto>();

        for(HouseManager manager: houseManagersByHouse) {
            HouseManagerResponseDto currentManagerResponse = new HouseManagerResponseDto(
                manager.getId(),
                manager.getName(),
                manager.getPhoneNumber(),
                manager.getEmailAddress(),
                manager.getHouseManagerType(),
                manager.getChores(),
                manager.getHouse().getId()
            );

            houseManagersByHouseResponse.add(currentManagerResponse);
        }

        return new ResponseEntity<List<HouseManagerResponseDto>>(houseManagersByHouseResponse, HttpStatus.OK);
    }

    @GetMapping("/managers/{houseID}/{type}")
    public ResponseEntity<List<HouseManagerResponseDto>> getManagersByType(@PathVariable HouseManagerType type, @PathVariable int houseID) {
        
        List<HouseManager> houseManagersByHouse = houseManagerService.getHouseManagersByType(type, houseID);
        List<HouseManagerResponseDto> houseManagersByHouseResponse = new ArrayList<HouseManagerResponseDto>();

        for(HouseManager manager: houseManagersByHouse) {
            HouseManagerResponseDto currentManagerResponse = new HouseManagerResponseDto(
                manager.getId(),
                manager.getName(),
                manager.getPhoneNumber(),
                manager.getEmailAddress(),
                manager.getHouseManagerType(),
                manager.getChores(),
                manager.getHouse().getId()
            );

            houseManagersByHouseResponse.add(currentManagerResponse);
        }

        return new ResponseEntity<List<HouseManagerResponseDto>>(houseManagersByHouseResponse, HttpStatus.OK);
    }

    @PutMapping("/managers/updateEmail/{id}")
    public ResponseEntity<HouseManagerResponseDto> updateEmail(@PathVariable int id, @RequestBody HouseManagerRequestDto houseManagerRequest) {
        
        HouseManager updatedManager = houseManagerService.updateHouseManagerEmail(id, houseManagerRequest.getEmailAddress());

        HouseManagerResponseDto updatedManagerResponse = new HouseManagerResponseDto(
            updatedManager.getId(),
            updatedManager.getName(),
            updatedManager.getPhoneNumber(),
            updatedManager.getEmailAddress(),
            updatedManager.getHouseManagerType(),
            updatedManager.getChores(),
            updatedManager.getHouse().getId()
            
        );

        return new ResponseEntity<HouseManagerResponseDto>(updatedManagerResponse, HttpStatus.OK);
    }

    @PutMapping("/managers/updateNumber/{id}")
    public ResponseEntity<HouseManagerResponseDto> updatePhoneNumber(@PathVariable int id, @RequestBody HouseManagerRequestDto houseManagerRequest) {
        
        HouseManager updatedManager = houseManagerService.updateHouseManagerPhoneNumber(id, houseManagerRequest.getPhoneNumber());

        HouseManagerResponseDto updatedManagerResponse = new HouseManagerResponseDto(
            updatedManager.getId(),
            updatedManager.getName(),
            updatedManager.getPhoneNumber(),
            updatedManager.getEmailAddress(),
            updatedManager.getHouseManagerType(),
            updatedManager.getChores(),
            updatedManager.getHouse().getId()
        );

        return new ResponseEntity<HouseManagerResponseDto>(updatedManagerResponse, HttpStatus.OK);
    }

    @PutMapping("/managers/updateName/{id}")
    public ResponseEntity<HouseManagerResponseDto> updateName(@PathVariable int id, @RequestBody HouseManagerRequestDto houseManagerRequest) {
        
        HouseManager updatedManager = houseManagerService.updateHouseManagerName(id, houseManagerRequest.getName());

        HouseManagerResponseDto updatedManagerResponse = new HouseManagerResponseDto(
            updatedManager.getId(),
            updatedManager.getName(),
            updatedManager.getPhoneNumber(),
            updatedManager.getEmailAddress(),
            updatedManager.getHouseManagerType(),
            updatedManager.getChores(),
            updatedManager.getHouse().getId()
        );

        return new ResponseEntity<HouseManagerResponseDto>(updatedManagerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/managers/delete/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable int id) {
        
        houseManagerService.deleteHouseManager(id);

        return new ResponseEntity<String>("House Manager with id " + id + " has been deleted.", HttpStatus.OK);
    }
}
