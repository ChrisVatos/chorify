package com.cvatos.chorifybackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ChorifyBackendApplication.class)
public class HouseManagerRepositoryTests {

    // House and HouseManager object that will get instantiated before every test and can be referenced
    private House defaultHouse;
    private  HouseManager createdHouseManager;

    @Autowired
    private HouseManagerRepository houseManagerRepository;

    @Autowired
    private HouseRepository houseRepository;

    /*
     *  setUp() method creates a default house that can be referenced by the other tests. 
     *  Avoids code duplication in each test. 
     */
    @BeforeEach
    public void setUp() {

        // Arrange

        // Default House entry
        defaultHouse = new House();
        defaultHouse.setHouseName("House created for testing");
        defaultHouse.setNumberOfMembers(5);
        houseRepository.save(defaultHouse);

        // Default HouseManager entry
        createdHouseManager = new HouseManager();
        createdHouseManager.setEmailAddress("fdawson@gmail.com");
        createdHouseManager.setName("Frank Dawson");
        createdHouseManager.setPhoneNumber("515-899-9898");
        createdHouseManager.setHouseManagerType(HouseManagerType.parent);
        createdHouseManager.setHouse(defaultHouse);
        createdHouseManager = houseManagerRepository.save(createdHouseManager);
    }

    /*
     *  Delete all entries in the database that were made for the tests 
     *  Important to delete houseManager entries first since they have a foerign key constraint with house entries
     */
    @AfterEach
    public void clearDatabase() {
        
        createdHouseManager = null;
        houseManagerRepository.deleteAll();
        houseRepository.deleteAll();
    }

    @Test
    public void testFindByID() {
        
        // Act
        int createdHouseManagerID = createdHouseManager.getId();
        createdHouseManager = null;
        createdHouseManager = houseManagerRepository.findById(createdHouseManagerID);

        // Assert
        assertEquals(createdHouseManagerID, createdHouseManager.getId());
        assertEquals("Frank Dawson", createdHouseManager.getName());
        assertEquals("fdawson@gmail.com", createdHouseManager.getEmailAddress());
        assertEquals("515-899-9898", createdHouseManager.getPhoneNumber());
        assertEquals(HouseManagerType.parent, createdHouseManager.getHouseManagerType());
        assertEquals(defaultHouse.getId(), createdHouseManager.getHouse().getId());
    }

    @Test 
    public void testFindByName() {

        // Act
        int createdHouseManagerID = createdHouseManager.getId();
        String createdHouseManagerName = createdHouseManager.getName();
        createdHouseManager = null;
        createdHouseManager = houseManagerRepository.findByName(createdHouseManagerName);

        // Assert
        assertEquals(createdHouseManagerID, createdHouseManager.getId());
        assertEquals(createdHouseManagerName, createdHouseManager.getName());
        assertEquals("fdawson@gmail.com", createdHouseManager.getEmailAddress());
        assertEquals("515-899-9898", createdHouseManager.getPhoneNumber());
        assertEquals(HouseManagerType.parent, createdHouseManager.getHouseManagerType());
        assertEquals(defaultHouse.getId(), createdHouseManager.getHouse().getId());
    }

    @Test 
    public void FindByEmailAddress() {

        // Act
        int createdHouseManagerID = createdHouseManager.getId();
        String createdHouseManagerEmailAddress = createdHouseManager.getEmailAddress();
        createdHouseManager = null;
        createdHouseManager = houseManagerRepository.findByEmailAddress(createdHouseManagerEmailAddress);

        // Assert
        assertEquals(createdHouseManagerID, createdHouseManager.getId());
        assertEquals("Frank Dawson", createdHouseManager.getName());
        assertEquals(createdHouseManagerEmailAddress, createdHouseManager.getEmailAddress());
        assertEquals("515-899-9898", createdHouseManager.getPhoneNumber());
        assertEquals(HouseManagerType.parent, createdHouseManager.getHouseManagerType());
        assertEquals(defaultHouse.getId(), createdHouseManager.getHouse().getId());
    }

    @Test
    public void testFindAllByHouseManagerType() {

        // Arrange
        HouseManager newHouseManager = new HouseManager();
        newHouseManager.setEmailAddress("gcostanza@gmail.com");
        newHouseManager.setName("George Costanza");
        newHouseManager.setPhoneNumber("515-789-0987");
        newHouseManager.setHouseManagerType(HouseManagerType.parent);
        newHouseManager.setHouse(defaultHouse);
        newHouseManager = houseManagerRepository.save(newHouseManager);

        // Act 
        List<HouseManager> houseManagers = houseManagerRepository.findAllByHouseManagerType(HouseManagerType.parent);

        // Assert 
        assertEquals(2, houseManagers.size());
    }

    @Test
    public void findAllByHouse() {

        // Arrange
        HouseManager newHouseManager = new HouseManager();
        newHouseManager.setEmailAddress("gcostanza@gmail.com");
        newHouseManager.setName("George Costanza");
        newHouseManager.setPhoneNumber("515-789-0987");
        newHouseManager.setHouseManagerType(HouseManagerType.parent);
        newHouseManager.setHouse(defaultHouse);
        newHouseManager = houseManagerRepository.save(newHouseManager);

        // Act
        List<HouseManager> houseManagers = houseManagerRepository.findByHouse(defaultHouse);

        // Assert 
        assertEquals(2, houseManagers.size());
    }

    @Test
    public void testDeleteByID() {

        // Act
        int houseManagerIDToDelete = createdHouseManager.getId();
        houseManagerRepository.deleteById(houseManagerIDToDelete);

        HouseManager retrievedDeletedHouseMember = houseManagerRepository.findById(houseManagerIDToDelete);

        // Assert
        assertNull(retrievedDeletedHouseMember);
    }
}
