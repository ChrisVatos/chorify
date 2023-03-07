package com.cvatos.chorifybackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cvatos.chorifybackend.ChorifyBackendApplication;
import com.cvatos.chorifybackend.model.House;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ChorifyBackendApplication.class)
public class HouseRepositoryTests {

    // To set up single house instance that will get created as part of the "Arrage" portion of each test
    public House createdHouse;

    @Autowired
    private HouseRepository houseRepository;

    @BeforeEach
    public void setUp() {
        // Arrange
         createdHouse = new House();
         createdHouse.setHouseName("Crazy Fun House");
         createdHouse.setNumberOfMembers(5);
         createdHouse = houseRepository.save(createdHouse);
    }

    @AfterEach
    public void clearDatabase() {
        houseRepository.deleteAll();
        createdHouse = null;
    }
   
    @Test
    public void testFindByID() {
    
        // Act 
        int createdHouseID = createdHouse.getId();
        createdHouse = null;
        createdHouse = houseRepository.findById(createdHouseID);

        // Assert 
        assertEquals(createdHouseID, createdHouse.getId());
        assertEquals("Crazy Fun House", createdHouse.getHouseName());
        assertEquals(5, createdHouse.getNumberOfMembers()); 
    }

    @Test
    public void testFindByHouseName() {
    
        // Act 
        int createdHouseID = createdHouse.getId();
        String createdHouseName = createdHouse.getHouseName();
        createdHouse = null;
        createdHouse = houseRepository.findByHouseName(createdHouseName);

        // Assert 
        assertEquals(createdHouseID, createdHouse.getId());
        assertEquals(createdHouseName, createdHouse.getHouseName());
        assertEquals(5, createdHouse.getNumberOfMembers()); 
    }
}
