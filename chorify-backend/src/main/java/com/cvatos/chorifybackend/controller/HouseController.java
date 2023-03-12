package com.cvatos.chorifybackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.service.HouseService;
import com.dto.HouseRequestDto;
import com.dto.HouseResponseDto;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @PostMapping("/houses/newHouse")
    public ResponseEntity<HouseResponseDto> createNewHouse(@Validated @RequestBody HouseRequestDto houseRequest) {

        // Create house object using data from the HouseRequestDto
        House houseToCreate = new House(houseRequest.getHouseName(), houseRequest.getNumberOfMembers());

        // Call the createHouse() method from the HouseService to create the house and get back the created house object
        House createdHouse = houseService.createHouse(houseToCreate);

        // Create HouseResponseDto using data from the created object
        HouseResponseDto createdHouseResponse = new HouseResponseDto(
            createdHouse.getId(),
            createdHouse.getHouseName(), 
            createdHouse.getNumberOfMembers()
        );

        return new ResponseEntity<HouseResponseDto>(createdHouseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/houses/id/{id}")
    public ResponseEntity<HouseResponseDto> getHouseById(@Validated @PathVariable int id) {

        // Retrieve house with desired ID
        House retrievedHouse = houseService.getHouseById(id);

        // Create HouseResponseDto using data from the fetched object
        HouseResponseDto fetchedHouseResponse = new HouseResponseDto(
            retrievedHouse.getId(),
            retrievedHouse.getHouseName(), 
            retrievedHouse.getNumberOfMembers()
        );

        return new ResponseEntity<HouseResponseDto>(fetchedHouseResponse, HttpStatus.OK);
    }

    @GetMapping("houses/houseName/{houseName}")
    public ResponseEntity<HouseResponseDto> getHouseByHouseName(@Validated @PathVariable String houseName) {

        // Retrieve house with desired houseName
        House retrievedHouse = houseService.getHouseByHouseName(houseName);

        // Create HouseResponseDto using data from the fetched object
        HouseResponseDto fetchedHouseResponse = new HouseResponseDto(
            retrievedHouse.getId(),
            retrievedHouse.getHouseName(),
            retrievedHouse.getNumberOfMembers()
        );

        return new ResponseEntity<HouseResponseDto>(fetchedHouseResponse, HttpStatus.OK);
    }

    @PutMapping("houses/update/{id}")
    public ResponseEntity<HouseResponseDto> updateHouseName(@PathVariable int id, @RequestBody HouseRequestDto houseRequest) {

        // Update house using the service method
        House updatedHouse = houseService.updateHouseName(id, houseRequest.getHouseName());

        // Create HouseResponseDto using data from the updated object
        HouseResponseDto updatedHouseResponse = new HouseResponseDto(
            updatedHouse.getId(),
            updatedHouse.getHouseName(),
            updatedHouse.getNumberOfMembers()
        );

        return new ResponseEntity<HouseResponseDto>(updatedHouseResponse, HttpStatus.OK);
    }

    @DeleteMapping("houses/delete/{id}")
    public ResponseEntity<String> deleteHouseById(@PathVariable int id) {

        houseService.deleteHouse(id);

        return new ResponseEntity<String>("House with id " + id + " was deleted", HttpStatus.OK);
    }
}
