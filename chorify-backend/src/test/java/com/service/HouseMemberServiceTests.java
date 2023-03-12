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
import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.repository.HouseMemberRepository;
import com.cvatos.chorifybackend.repository.HouseRepository;
import com.cvatos.chorifybackend.service.HouseMemberService;

@ExtendWith(MockitoExtension.class)
public class HouseMemberServiceTests {
    
    @Mock
    private HouseMemberRepository houseMemberRepository;

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private HouseMemberService houseMemberService;

    @Test
    public void testCreateHouseMember() {

        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";

        HouseMember houseMemberToCreate = new HouseMember(name, phoneNumber, email);

        Mockito.when(houseMemberRepository.save(Mockito.any(HouseMember.class))).thenReturn(houseMemberToCreate);
    
        HouseMember createdHouseMember = houseMemberService.createHouseMember(houseMemberToCreate);

        assertNotNull(createdHouseMember);
        assertTrue(createdHouseMember.getName().equals(name));
        assertTrue(createdHouseMember.getPhoneNumber().equals(phoneNumber));
        assertTrue(createdHouseMember.getEmailAddress().equals(email));
    }

    @Test
    public void testCreateHouseMemberWithInvalidEmail() {

        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";

        HouseMember houseMemberToCreateThatAlreadyExists = new HouseMember(name, phoneNumber, email);

        Mockito.when(houseMemberRepository.findByEmailAddress(email)).thenReturn(houseMemberToCreateThatAlreadyExists);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.createHouseMember(houseMemberToCreateThatAlreadyExists));
        
        assertTrue(exception.getMessage().equals("House Member with email address " + email + " already exists."));
        assertTrue(exception.getStatus() == HttpStatus.CONFLICT);
    }

    @Test
    public void testCreateHouseMemberWithInvalidPhoneNumber() {

        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";

        HouseMember houseMemberToCreateThatAlreadyExists = new HouseMember(name, phoneNumber, email);

        Mockito.when(houseMemberRepository.findByPhoneNumber(phoneNumber)).thenReturn(houseMemberToCreateThatAlreadyExists);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.createHouseMember(houseMemberToCreateThatAlreadyExists));
        
        assertTrue(exception.getMessage().equals("House Member with phone number " + phoneNumber + " already exists."));
        assertTrue(exception.getStatus() == HttpStatus.CONFLICT);
    }

    @Test
    public void testGetHouseMemberByValidId() {
        
        int id = 3;
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";

        HouseMember existingMember = new HouseMember(name, phoneNumber, email);

        Mockito.when(houseMemberRepository.findById(id)).thenReturn(existingMember);

        HouseMember retrievedMember = houseMemberService.getHouseMemberById(id);

        assertNotNull(retrievedMember);
        assertTrue(retrievedMember.getName().equals(name));
        assertTrue(retrievedMember.getPhoneNumber().equals(phoneNumber));
        assertTrue(retrievedMember.getEmailAddress().equals(email));
    }

    @Test
    public void testGetHouseMemberByInvalidId() {
        
        int id = 3;

        Mockito.when(houseMemberRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.getHouseMemberById(id));

        assertTrue(exception.getMessage().equals("House Member with id " + id + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetHouseMemberByValidName() {
        
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";

        HouseMember existingMember = new HouseMember(name, phoneNumber, email);

        Mockito.when(houseMemberRepository.findByName(name)).thenReturn(existingMember);

        HouseMember retrievedMember = houseMemberService.getHouseMemberByName(name);

        assertNotNull(retrievedMember);
        assertTrue(retrievedMember.getName().equals(name));
        assertTrue(retrievedMember.getPhoneNumber().equals(phoneNumber));
        assertTrue(retrievedMember.getEmailAddress().equals(email));
    }

    @Test
    public void testGetHouseMemberByInvalidName() {
        
        String name = "Invalid name";

        Mockito.when(houseMemberRepository.findByName(name)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.getHouseMemberByName(name));

        assertTrue(exception.getMessage().equals("House Member with name " + name + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testgetAllMembersByHouse() {
        
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

        HouseMember houseMember = new HouseMember(name, phoneNumber, email);

        String name2 = "George Vatos";
        String email2 = "gvatos@gmail.com";
        String phoneNumber2 = "514-654-9873";

        HouseMember houseMember2 = new HouseMember(name2, phoneNumber2, email2);

        // Creating list of house members
        List<HouseMember> houseMembers = new ArrayList<HouseMember>();
        houseMembers.add(houseMember);
        houseMembers.add(houseMember2);

        Mockito.when(houseRepository.findById(id)).thenReturn(savedHouse);
        Mockito.when(houseMemberRepository.findByHouse(savedHouse)).thenReturn(houseMembers);

        List<HouseMember> retrievedHouseMembers = houseMemberService.getHouseMembersByHouse(id);

        assertTrue(retrievedHouseMembers.size() == 2);
    }

    @Test
    public void testgetAllMembersByHouseWith0Members() {
        
        // Setting up the fields to create the house object with
        int id = 1;
        String houseName = "Test House";
        int numberOfMembers = 5;

        // Creating the house object to be returned when the repository method to find by the specified is called
        House savedHouse = new House(houseName, numberOfMembers);

        // Creating list of house members
        List<HouseMember> houseMembers = new ArrayList<HouseMember>();

        Mockito.when(houseRepository.findById(id)).thenReturn(savedHouse);
        Mockito.when(houseMemberRepository.findByHouse(savedHouse)).thenReturn(houseMembers);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.getHouseMembersByHouse(id));

        assertTrue(exception.getMessage().equals("No house members exist for this house."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateHouseMemberWithValidNewNameAndValidId() {

        int id = 2;
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";
        String newName = "Bob";
     
        HouseMember oldMember = new HouseMember(name, phoneNumber, email);
        HouseMember oldMemberNewName = oldMember;
        oldMemberNewName.setId(id);

        Mockito.when(houseMemberRepository.findById(id)).thenReturn(oldMember);
        Mockito.when(houseMemberRepository.save(Mockito.any(HouseMember.class))).thenReturn(oldMemberNewName);

        HouseMember newMember = houseMemberService.updateHouseMemberName(id , newName);

        assertNotNull(newMember);
        assertTrue(newMember.getId() == id);
        assertTrue(newMember.getEmailAddress().equals(email));
        assertTrue(newMember.getPhoneNumber().equals(phoneNumber));
    }

    @Test
    public void testUupdateHouseMemberWithInvalidNewNameAndValidId() {

        int id = 2;
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";
        String newName = "Bob";
     
        HouseMember oldMember = new HouseMember(name, phoneNumber, email);
        HouseMember memberWithExistingNewName = new HouseMember(newName, phoneNumber, email);
      

        Mockito.when(houseMemberRepository.findById(id)).thenReturn(oldMember);
        Mockito.when(houseMemberRepository.findByName(newName)).thenReturn(memberWithExistingNewName);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.updateHouseMemberName(id, newName));

        assertTrue(exception.getMessage().equals("House Member with name " + newName + " already exsists."));
        assertTrue(exception.getStatus() == HttpStatus.CONFLICT);
    }

    @Test
    public void testUpdateHouseMemberWithValidNewNameAndInvalidId() {

        int id = 2;
        String newName = "Bob";
     
        Mockito.when(houseMemberRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.updateHouseMemberName(id, newName));

        assertTrue(exception.getMessage().equals("House Member with id " + id + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    // Analogous tests for updating email and phone number

    @Test
    public void testDeleteHouseMemberByValidId() {

        int id = 2;
        String name = "Chris Vatos";
        String email = "cvatos@gmail.com";
        String phoneNumber = "514-654-9873";

        HouseMember exisingMember = new HouseMember(name, phoneNumber, email);

        Mockito.when(houseMemberRepository.findById(id)).thenReturn(exisingMember);

        houseMemberService.deleteHouseMember(id);

        Mockito.verify(houseMemberRepository, times(1)).deleteById(id);
    }


    @Test
    public void testDeleteHouseMemberByInvalidId() {

        int id = 2;

        Mockito.when(houseMemberRepository.findById(id)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> houseMemberService.deleteHouseMember(id));

        assertTrue(exception.getMessage().equals("House Member with id " + id + " does not exist."));
        assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND);
    }

    




}
