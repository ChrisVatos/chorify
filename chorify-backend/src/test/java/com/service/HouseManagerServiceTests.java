package com.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.cvatos.chorifybackend.exception.ChorifyException;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;
import com.cvatos.chorifybackend.repository.HouseManagerRepository;
import com.cvatos.chorifybackend.repository.HouseMemberRepository;
import com.cvatos.chorifybackend.repository.HouseRepository;
import com.cvatos.chorifybackend.service.HouseManagerService;
import com.cvatos.chorifybackend.service.HouseService;

@ExtendWith(MockitoExtension.class)
public class HouseManagerServiceTests {
    
    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseService houseService;

    @Mock
    private HouseManagerRepository houseManagerRepository;

    @Mock
    private HouseMemberRepository houseMemberRepository;

    @InjectMocks
    private HouseManagerService houseManagerService;

    @Test
    public void testCreateValidHouseMember() {

        int houseID = 5;
        String name = "Big Boss";
        String email = "email@gmail.com";
        String phoneNumber = "514-873-0987";
        HouseManagerType type = HouseManagerType.guardian;

        HouseManager newHouseManager = new HouseManager(name, phoneNumber, email, type);

        String houseName = "Crazy house";
        int numberOfMembers = 4;
        House existingHouse = new House(houseName, numberOfMembers);

        List<HouseMember> existingMembers = new ArrayList<HouseMember>();
        List<HouseManager> existingManagers = new ArrayList<HouseManager>();

        Mockito.when(houseRepository.findById(houseID)).thenReturn(existingHouse);
        Mockito.when(houseMemberRepository.findByHouse(Mockito.any(House.class))).thenReturn(existingMembers);
        Mockito.when(houseManagerRepository.findByHouse(Mockito.any(House.class))).thenReturn(existingManagers);
        Mockito.when(houseManagerRepository.save(Mockito.any(HouseManager.class))).thenReturn(newHouseManager);

        HouseManager createdManager = houseManagerService.createHouseManager(newHouseManager, houseID);

        assertTrue(name.equals(createdManager.getName()));
        assertTrue(email.equals(createdManager.getEmailAddress()));
        assertTrue(phoneNumber.equals(createdManager.getPhoneNumber()));
        assertTrue(type.equals(createdManager.getHouseManagerType()));
    }

    @Test
    public void testCreateInvalidHouseMemberWithExistingEmail() {

        int houseID = 5;
        String name = "Big Boss";
        String email = "email@gmail.com";
        String phoneNumber = "514-873-0987";
        HouseManagerType type = HouseManagerType.guardian;
        HouseManager newHouseManager = new HouseManager(name, phoneNumber, email, type);

        HouseManager existingManager = new HouseManager("Chris", "513-473-3920", email, type);

        Mockito.when(houseManagerRepository.findByEmailAddress(email)).thenReturn(existingManager);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseManagerService.createHouseManager(newHouseManager, houseID));

        assertTrue(exception.getMessage().equals("House Manager with email address " + email + " already exists."));
        assertTrue(exception.getStatus().equals(HttpStatus.CONFLICT));
    }

    @Test
    public void testCreateInvalidHouseMemberWithExistingPhoneNumber() {

        int houseID = 5;
        String name = "Big Boss";
        String email = "email@gmail.com";
        String phoneNumber = "514-873-0987";
        HouseManagerType type = HouseManagerType.guardian;
        HouseManager newHouseManager = new HouseManager(name, phoneNumber, email, type);

        HouseManager existingManager = new HouseManager("Chris", phoneNumber, "hello@gmail.com", type);

        Mockito.when(houseManagerRepository.findByPhoneNumber(phoneNumber)).thenReturn(existingManager);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseManagerService.createHouseManager(newHouseManager, houseID));

        assertTrue(exception.getMessage().equals("House Manager with phone number " + phoneNumber + " already exists."));
        assertTrue(exception.getStatus().equals(HttpStatus.CONFLICT));
    }

    @Test
    public void testCreateInvalidHouseMemberWithInvalidHouseID() {

        int houseID = 5;
        String name = "Big Boss";
        String email = "email@gmail.com";
        String phoneNumber = "514-873-0987";
        HouseManagerType type = HouseManagerType.guardian;
        HouseManager newHouseManager = new HouseManager(name, phoneNumber, email, type);

        Mockito.when(houseRepository.findById(houseID)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseManagerService.createHouseManager(newHouseManager, houseID));

        assertTrue(exception.getMessage().equals("House with id " + houseID + " does not exist."));
        assertTrue(exception.getStatus().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testGetMangerByValidID() {

        int id = 5;
        String name = "Big Boss";
        String email = "email@gmail.com";
        String phoneNumber = "514-873-0987";
        HouseManagerType type = HouseManagerType.guardian;
        HouseManager existingHouseManager = new HouseManager(name, phoneNumber, email, type);
        existingHouseManager.setId(id);

        Mockito.when(houseManagerRepository.findById(id)).thenReturn(existingHouseManager);

        HouseManager retrievedManager = houseManagerService.getHouseManagerById(id);

        assertTrue(id == retrievedManager.getId());
        assertTrue(name.equals(retrievedManager.getName()));
        assertTrue(email.equals(retrievedManager.getEmailAddress()));
        assertTrue(phoneNumber.equals(retrievedManager.getPhoneNumber()));
        assertTrue(type.equals(retrievedManager.getHouseManagerType()));
    }

    @Test
    public void testGetMangerByInvalidID() {

        int id = 5;

        Mockito.when(houseManagerRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseManagerService.getHouseManagerById(id));

        assertTrue(exception.getMessage().equals("House Manager with id " + id + " does not exist."));
        assertTrue(exception.getStatus().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testGetMangerByValidName() {

        int id = 5;
        String name = "Big Boss";
        String email = "email@gmail.com";
        String phoneNumber = "514-873-0987";
        HouseManagerType type = HouseManagerType.guardian;
        HouseManager existingHouseManager = new HouseManager(name, phoneNumber, email, type);
        existingHouseManager.setId(id);

        Mockito.when(houseManagerRepository.findByName(name)).thenReturn(existingHouseManager);

        HouseManager retrievedManager = houseManagerService.getHouseManagerByName(name);

        assertTrue(id == retrievedManager.getId());
        assertTrue(name.equals(retrievedManager.getName()));
        assertTrue(email.equals(retrievedManager.getEmailAddress()));
        assertTrue(phoneNumber.equals(retrievedManager.getPhoneNumber()));
        assertTrue(type.equals(retrievedManager.getHouseManagerType()));
    }

    @Test
    public void testGetMangerByInvalidName() {

        String name = "Big Boss";

        Mockito.when(houseManagerRepository.findByName(name)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseManagerService.getHouseManagerByName(name));

        assertTrue(exception.getMessage().equals("House Manager with name " + name + " does not exist."));
        assertTrue(exception.getStatus().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testGetAllManagersByHouse() {
        
        // Setting up the fields to create the house object with
        int id = 1;
        String houseName = "Test House";
        int numberOfMembers = 5;

        // Creating the house object to be returned when the repository method to find by the specified is called
        House savedHouse = new House(houseName, numberOfMembers);

        // Creating house members
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";
        HouseManagerType type = HouseManagerType.guardian;

        HouseManager houseManager = new HouseManager(name, phoneNumber, email, type);

        String name2 = "George Vatos";
        String email2 = "gvatos@gmail.com";
        String phoneNumber2 = "514-654-9873";
        HouseManagerType type2 = HouseManagerType.parent;

        HouseManager houseManager2 = new HouseManager(name2, phoneNumber2, email2, type2);

        // Creating list of house members
        List<HouseManager> houseManagers = new ArrayList<HouseManager>();
        houseManagers.add(houseManager);
        houseManagers.add(houseManager2);

        Mockito.when(houseRepository.findById(id)).thenReturn(savedHouse);
        Mockito.when(houseManagerRepository.findByHouse(savedHouse)).thenReturn(houseManagers);

        List<HouseManager> retrievedHouseManagers = houseManagerService.getHouseManagersByHouse(id);

        assertTrue(retrievedHouseManagers.size() == 2);
    }

    @Test
    public void testGetAllManagersByHouseAndType() {
        
        // Setting up the fields to create the house object with
        int id = 1;
        String houseName = "Test House";
        int numberOfMembers = 5;

        // Creating the house object to be returned when the repository method to find by the specified is called
        House savedHouse = new House(houseName, numberOfMembers);
        savedHouse.setId(id);

        // Setting up the fields to create the house object with
        int idOfOtherHouse = 2;
        String houseNameOfOtherHouse = "Test House";
        int numberOfMembersOfOtherHouse = 5;

        // Creating the house object to be returned when the repository method to find by the specified is called
        House savedHouse2 = new House(houseNameOfOtherHouse, numberOfMembersOfOtherHouse);
        savedHouse2.setId(idOfOtherHouse);

        // Creating house members
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";
        HouseManagerType type = HouseManagerType.parent;

        HouseManager houseManager = new HouseManager(name, phoneNumber, email, type);
        houseManager.setHouse(savedHouse);

        String name2 = "George Vatos";
        String email2 = "gvatos@gmail.com";
        String phoneNumber2 = "514-654-9873";
        HouseManagerType type2 = HouseManagerType.parent;

        HouseManager houseManager2 = new HouseManager(name2, phoneNumber2, email2, type2);
        houseManager2.setHouse(savedHouse2);

        String name3 = "Lambro Vatos";
        String email3 = "lvatos@gmail.com";
        String phoneNumber3 = "514-666-9873";
        HouseManagerType type3 = HouseManagerType.parent;

        HouseManager houseManager3 = new HouseManager(name3, phoneNumber3, email3, type3);
        houseManager3.setHouse(savedHouse2);

        // Creating list of house members
        List<HouseManager> houseManagers = new ArrayList<HouseManager>();
        houseManagers.add(houseManager);
        houseManagers.add(houseManager2);
        houseManagers.add(houseManager3);

        Mockito.when(houseManagerRepository.findAllByHouseManagerType(HouseManagerType.parent)).thenReturn(houseManagers);

        List<HouseManager> retrievedHouseManagersFromHouse1 = houseManagerService.getHouseManagersByType(HouseManagerType.parent, id);
        List<HouseManager> retrievedHouseManagersFromHouse2 = houseManagerService.getHouseManagersByType(HouseManagerType.parent, idOfOtherHouse);

        assertTrue(retrievedHouseManagersFromHouse1.size() == 1);
        assertTrue(retrievedHouseManagersFromHouse2.size() == 2);
    }

    @Test
    public void testUpdateHouseManagerWithValidNewNameAndValidId() {

        int id = 2;
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";
        HouseManagerType type = HouseManagerType.parent;
        String newName = "Bob";
     
        HouseManager managerToUpdate = new HouseManager(name, phoneNumber, email, type);
        HouseManager updatedManager = managerToUpdate;
        updatedManager.setId(id);

        Mockito.when(houseManagerRepository.findById(id)).thenReturn(managerToUpdate);
        Mockito.when(houseManagerRepository.save(Mockito.any(HouseManager.class))).thenReturn(updatedManager);

        HouseManager newManager = houseManagerService.updateHouseManagerName(id , newName);

        assertNotNull(newManager);
        assertTrue(newManager.getName().equals(newName));
        assertTrue(newManager.getId() == id);
        assertTrue(newManager.getEmailAddress().equals(email));
        assertTrue(newManager.getPhoneNumber().equals(phoneNumber));
    }

    @Test
    public void testUpdateHouseManagerWithValidNewNameAndInvalidId() {

        int id = 2;
        String newName = "Bob";
     
        Mockito.when(houseManagerRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseManagerService.updateHouseManagerName(id, newName));

        assertTrue(exception.getMessage().equals("House Manager with id " + id + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    // Analogous tests for updating email and phone number

    @Test
    public void testDeleteHouseManager() {

        // Setting up the fields to create the house object with
        int id = 1;
        String houseName = "Test House";
        int numberOfMembers = 5;

        // Creating the house object to be returned when the repository method to find by the specified is called
        House savedHouse = new House(houseName, numberOfMembers);
        savedHouse.setId(id);
        House savedHouseAfterDeletingMember = savedHouse;
        savedHouseAfterDeletingMember.setNumberOfMembers(numberOfMembers - 1);

        int managerID = 4;
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";
        HouseManagerType type = HouseManagerType.parent;
        HouseManager managerToDelete = new HouseManager(name, phoneNumber, email, type);
        managerToDelete.setHouse(savedHouse);
        managerToDelete.setId(managerID);

        Mockito.when(houseManagerRepository.findById(managerID)).thenReturn(managerToDelete);
        Mockito.when(houseService.decrementNumberOfHouseMembers(id)).thenReturn(savedHouseAfterDeletingMember);

        houseManagerService.deleteHouseManager(managerID);

        Mockito.verify(houseManagerRepository, times(1)).deleteById(managerID);
        assertTrue(savedHouseAfterDeletingMember.getNumberOfMembers() == 4);
    }
}
