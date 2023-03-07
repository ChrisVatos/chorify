package com.cvatos.chorifybackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cvatos.chorifybackend.ChorifyBackendApplication;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseMember;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ChorifyBackendApplication.class)
public class HouseMemberRepositoryTests {

    // House and HouseMember object that will get instantiated before every test and can be referenced
    private House defaultHouse;
    private HouseMember createdHouseMember;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMemberRepository houseMemberRepository;

    @BeforeEach
    public void setUp() {

        // Arrange

        // Default House entry
        defaultHouse = new House();
        defaultHouse.setHouseName("House created for testing");
        defaultHouse.setNumberOfMembers(5);
        houseRepository.save(defaultHouse);

        // Default HouseMember entry
        createdHouseMember = new HouseMember();
        createdHouseMember.setEmailAddress("kid1@testing.com");
        createdHouseMember.setName("Kid One");
        createdHouseMember.setPhoneNumber("515-456-8762");
        createdHouseMember.setHouse(defaultHouse);
        createdHouseMember = houseMemberRepository.save(createdHouseMember);
    }

    @AfterEach
    public void clearDatabase() {
        houseMemberRepository.deleteAll();
        houseRepository.deleteAll();
    }

    @Test
    public void testFindByID() {

        // Act
        int createdHouseMemberID = createdHouseMember.getId();
        createdHouseMember = null;
        createdHouseMember = houseMemberRepository.findById(createdHouseMemberID);

        // Assert
        assertEquals(createdHouseMemberID, createdHouseMember.getId());
        assertEquals("Kid One", createdHouseMember.getName());
        assertEquals("kid1@testing.com", createdHouseMember.getEmailAddress());
        assertEquals("515-456-8762", createdHouseMember.getPhoneNumber());
        assertEquals(defaultHouse.getId(), createdHouseMember.getHouse().getId());
    }

    @Test
    public void testFindByName() {

        // Act
        String createdHouseMemberName = createdHouseMember.getName();   
        int createdHouseMemberID = createdHouseMember.getId();
        createdHouseMember = null;
        createdHouseMember = houseMemberRepository.findByName(createdHouseMemberName);

        // Assert
        assertEquals(createdHouseMemberID, createdHouseMember.getId());
        assertEquals(createdHouseMemberName, createdHouseMember.getName());
        assertEquals("kid1@testing.com", createdHouseMember.getEmailAddress());
        assertEquals("515-456-8762", createdHouseMember.getPhoneNumber());
        assertEquals(defaultHouse.getId(), createdHouseMember.getHouse().getId());
    }

    @Test
    public void testFindByEmailAddress() {

        // Act
        String createdHouseMemberEmailAddress = createdHouseMember.getEmailAddress();   
        int createdHouseMemberID = createdHouseMember.getId();
        createdHouseMember = null;
        createdHouseMember = houseMemberRepository.findByEmailAddress(createdHouseMemberEmailAddress);

        // Assert
        assertEquals(createdHouseMemberID, createdHouseMember.getId());
        assertEquals("Kid One", createdHouseMember.getName());
        assertEquals(createdHouseMemberEmailAddress, createdHouseMember.getEmailAddress());
        assertEquals("515-456-8762", createdHouseMember.getPhoneNumber());
        assertEquals(defaultHouse.getId(), createdHouseMember.getHouse().getId());
    }

    @Test 
    public void findAllByHouse() {

        // Arrange 
        HouseMember newHouseMember = new HouseMember();
        newHouseMember.setEmailAddress("kid2@testing.com");
        newHouseMember.setName("Kid Two");
        newHouseMember.setPhoneNumber("515-123-5432");
        newHouseMember.setHouse(defaultHouse);
        newHouseMember = houseMemberRepository.save(newHouseMember);

        // Act
        List<HouseMember> houseMembers = houseMemberRepository.findByHouse(defaultHouse);

        // Assert
        assertEquals(2, houseMembers.size());
    }
}
