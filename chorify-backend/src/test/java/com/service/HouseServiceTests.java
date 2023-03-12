package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.cvatos.chorifybackend.exception.ChorifyException;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.repository.HouseRepository;
import com.cvatos.chorifybackend.service.HouseService;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTests {

    @Mock
    HouseRepository houseRepository;

    @InjectMocks
    HouseService houseService;

    @Test
    public void testGetHouseByValidId() {
        
        // Setting up the fields to create the house object with
        int id = 1;
        String houseName = "Test House";
        int numberOfMembers = 5;

        // Creating the house object to be returned when the repository method to find by the specified is called
        House savedHouse = new House(houseName, numberOfMembers);
        
        // Mocking the repository method to return the previously created house and to isolate the service method
        Mockito.when(houseRepository.findById(id)).thenReturn(savedHouse);

        // Calling the service method
        House retrievedHouse = houseService.getHouseById(id);

        // Asserting the retrievedHouse fields match what's expected
        assertNotNull(retrievedHouse);
        assertTrue(retrievedHouse.getHouseName().equals(houseName));
        assertTrue(retrievedHouse.getNumberOfMembers() == numberOfMembers);
    }

    @Test
    public void testGetHouseByInvalidId() {
        
        int invalidId = 78;
               
        Mockito.when(houseRepository.findById(invalidId)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseService.getHouseById(invalidId));

        assertTrue(exception.getMessage().equals("House with ID of " + invalidId + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetHouseByValidHouseName() {
        
        String houseName = "Test House";
        int numberOfMembers = 5;

        House savedHouse = new House(houseName, numberOfMembers);
        
        Mockito.when(houseRepository.findByHouseName(houseName)).thenReturn(savedHouse);

        House retrievedHouse = houseService.getHouseByHouseName(houseName);

        assertNotNull(retrievedHouse);
        assertTrue(retrievedHouse.getHouseName().equals(houseName));
        assertTrue(retrievedHouse.getNumberOfMembers() == numberOfMembers);
    }

    @Test 
    public void testGetHouseByInvalidHouseName() {

        String invalidName = "Invalid House Name";

        Mockito.when(houseRepository.findByHouseName(invalidName)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseService.getHouseByHouseName(invalidName));

        assertTrue(exception.getMessage().equals("House with name " + invalidName + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateHouseWithValidHouseName() {
        
        String houseName = "Valid House Name";
        int numberOfMembers = 6;

        House savedHouse = new House(houseName, numberOfMembers);

        Mockito.when(houseRepository.save(Mockito.any(House.class))).thenReturn(savedHouse);

        House createdHouse = houseService.createHouse(savedHouse);

        assertNotNull(createdHouse);
        assertTrue(createdHouse.getHouseName().equals(houseName));
        assertTrue(createdHouse.getNumberOfMembers() == numberOfMembers);
    }

    @Test
    public void testCreateHouseWithInvalidHouseName() {
        
        String invalidHouseName = "Invalid House Name";
        int numberOfMembers = 6;

        House existingHouse = new House(invalidHouseName, numberOfMembers);

        Mockito.when(houseRepository.findByHouseName(invalidHouseName)).thenReturn(existingHouse);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseService.createHouse(existingHouse));

        assertTrue(exception.getMessage().equals("House with name " + existingHouse.getHouseName() + " already exists."));
        assertTrue(exception.getStatus() == HttpStatus.CONFLICT);
    }

    @Test
    public void updateHouseWithValidNewHouseNameAndValidId() {

        int id = 2;
        String newHouseName = "New House Name";
        String oldHouseName = "Old House Name";
        int numberOfMembers = 6;

        House oldHouse = new House(oldHouseName, numberOfMembers);
        House oldHouseNewName = oldHouse;
        oldHouseNewName.setId(id);

        Mockito.when(houseRepository.findById(id)).thenReturn(oldHouse);
        Mockito.when(houseRepository.save(Mockito.any(House.class))).thenReturn(oldHouseNewName);

        House newHouse = houseService.updateHouseName(id , newHouseName);

        assertNotNull(newHouse);
        assertTrue(newHouse.getId() == id);
        assertTrue(newHouse.getHouseName().equals(newHouseName));
        assertTrue(newHouse.getNumberOfMembers() == numberOfMembers);
    }

    @Test
    public void updateHouseWithValidNewHouseNameAndInvalidId() {

        int id = 2;
        String newHouseName = "New House Name";

        Mockito.when(houseRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseService.updateHouseName(id, newHouseName));

        assertTrue(exception.getMessage().equals("House with ID of " + id + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateHouseWithInvalidNewHouseNameAndValidId() {

        int id = 2;
        String oldHouseName = "Old House Name";
        String newHouseName = "New House Name";
        int numberOfMembers = 6;

        House oldHouse = new House(oldHouseName, numberOfMembers);
        House housWithAlreadyExistingName = new House(newHouseName, numberOfMembers);

        Mockito.when(houseRepository.findById(id)).thenReturn(oldHouse);
        Mockito.when(houseRepository.findByHouseName(newHouseName)).thenReturn(housWithAlreadyExistingName);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseService.updateHouseName(id, newHouseName));

        assertTrue(exception.getMessage().equals("House with name " + newHouseName + " already exists."));
        assertTrue(exception.getStatus() == HttpStatus.BAD_REQUEST);
    }

    @Test 
    public void testIncrementNumberOfHouseMembersWithValidHouseId() {
        
        int id = 2;
        String houseName = "New House Name";
        int numberOfMembers = 6;

        House houseToUpdate = new House(houseName, numberOfMembers);
        House updatedHouseToReturn = houseToUpdate;
        updatedHouseToReturn.setNumberOfMembers(houseToUpdate.getNumberOfMembers());
        updatedHouseToReturn.setId(id);

        Mockito.when(houseRepository.findById(id)).thenReturn(houseToUpdate);
        Mockito.when(houseRepository.save(Mockito.any(House.class))).thenReturn(updatedHouseToReturn);

        House  updatedHouse = houseService.incrementNumberOfHouseMembers(id);

        assertNotNull(updatedHouse);
        assertTrue(updatedHouse.getId() == id);
        assertTrue(updatedHouse.getHouseName().equals(houseName));
        assertEquals(numberOfMembers + 1, updatedHouse.getNumberOfMembers());
    }

    @Test
    public void testIncrementNumberOfHouseMembersWithInvalidId() {

        int id = 2;

        Mockito.when(houseRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseService.incrementNumberOfHouseMembers(id));

        assertTrue(exception.getMessage().equals("House with ID of " + id + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteByValidId() {

        int id = 2;
        String houseName = "New House Name";
        int numberOfMembers = 6;

        House houseToDelete = new House(houseName, numberOfMembers);

        Mockito.when(houseRepository.findById(id)).thenReturn(houseToDelete);

        houseService.deleteHouse(id);

        Mockito.verify(houseRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteByInvalidId() {

        int id = 2;

        Mockito.when(houseRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseService.deleteHouse(id));

        assertTrue(exception.getMessage().equals("House with ID of " + id + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }
}
