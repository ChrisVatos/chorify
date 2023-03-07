package com.cvatos.chorifybackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cvatos.chorifybackend.model.Chore;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.model.Chore.ChoreStatus;
import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class choreRepositoryTests {

    // Delcaring house, houseManager and houseMember objects that will be used for all tests
    House defaultHouse;
    HouseManager firstHouseManager;
    HouseMember firstHouseMember;
    Chore newChore;

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseManagerRepository houseManagerRepository;

    @Autowired
    private HouseMemberRepository houseMemberRepository;

    /*
     * Instantiates all the objects we need to create a chore instance and save it to the database
     */
    @BeforeEach
    public void setUp() {

        // Arrange

        // Setting up house object
        defaultHouse = new House();
        defaultHouse.setHouseName("Crazy Fun House");
        defaultHouse.setNumberOfMembers(5);
        defaultHouse = houseRepository.save(defaultHouse);

        // Setting up houseManager object
        firstHouseManager = new HouseManager();
        firstHouseManager.setEmailAddress("fdawson@gmail.com");
        firstHouseManager.setName("Frank Dawson");
        firstHouseManager.setPhoneNumber("515-899-9898");
        firstHouseManager.setHouseManagerType(HouseManagerType.parent);
        firstHouseManager.setHouse(defaultHouse);
        firstHouseManager = houseManagerRepository.save(firstHouseManager);

        // Setting up houseMember object
        firstHouseMember = new HouseMember();
        firstHouseMember.setEmailAddress("kid1@testing.com");
        firstHouseMember.setName("Kid One");
        firstHouseMember.setPhoneNumber("515-456-8762");
        firstHouseMember.setHouse(defaultHouse);
        firstHouseMember = houseMemberRepository.save(firstHouseMember);

        // Setting up chore object
        newChore = new Chore();
        newChore.setChoreName("Clean mop");
        newChore.setChoreDescription("Mop bucket is dirty. Take it out!");
        newChore.setChoreStatus(ChoreStatus.inProgress);
        newChore.setDifficultyEstimator(2);
        newChore.setDueDate(Date.valueOf("2023-12-12"));
        newChore.setChoreAssignee(firstHouseMember);
        newChore.setChoreAssigner(firstHouseManager);
        newChore.setHouse(defaultHouse);
        newChore = choreRepository.save(newChore);
    }

    @AfterEach
    public void clearDatabase() {

        defaultHouse = null;
        newChore = null;
        firstHouseManager = null;
        firstHouseMember = null;

        choreRepository.deleteAll();
        houseManagerRepository.deleteAll();
        houseMemberRepository.deleteAll();
        houseRepository.deleteAll();
    }

    @Test
    public void testfindByID() {

        // Act
        int newChoreID = newChore.getId();
        newChore = null;
        newChore = choreRepository.findById(newChoreID);

        // Assert
        assertEquals(newChoreID, newChore.getId());
        assertEquals("Clean mop", newChore.getChoreName());
        assertEquals("Mop bucket is dirty. Take it out!", newChore.getChoreDescription());
        assertEquals(2, newChore.getDifficultyEstimator());
        assertEquals(ChoreStatus.inProgress, newChore.getChoreStatus());
        assertEquals(Date.valueOf("2023-12-12"), newChore.getDueDate());
        assertEquals(firstHouseMember.getId(), newChore.getChoreAssignee().getId());
        assertEquals(firstHouseManager.getId(), newChore.getChoreAssigner().getId());
        assertEquals(defaultHouse.getId(), newChore.getHouse().getId());
    }

    @Test
    public void testFindByChoreName() {

        // Act
        String choreName = newChore.getChoreName();
        int newChoreID = newChore.getId();
        newChore = null;
        newChore = choreRepository.findByChoreName(choreName);

        // Assert
        assertEquals(newChoreID, newChore.getId());
        assertEquals(choreName, newChore.getChoreName());
        assertEquals("Mop bucket is dirty. Take it out!", newChore.getChoreDescription());
        assertEquals(2, newChore.getDifficultyEstimator());
        assertEquals(ChoreStatus.inProgress, newChore.getChoreStatus());
        assertEquals(Date.valueOf("2023-12-12"), newChore.getDueDate());
        assertEquals(firstHouseMember.getId(), newChore.getChoreAssignee().getId());
        assertEquals(firstHouseManager.getId(), newChore.getChoreAssigner().getId());
        assertEquals(defaultHouse.getId(), newChore.getHouse().getId());
    }

    @Test
    public void testAllByChoreStatus() {

        // Arrange
        Chore newChore2 = new Chore();
        newChore2.setChoreName("Clean mop");
        newChore2.setChoreDescription("Mop bucket is dirty. Take it out!");
        newChore2.setDifficultyEstimator(2);
        newChore2.setDueDate(Date.valueOf("2023-12-12"));
        newChore2.setChoreAssignee(firstHouseMember);
        newChore2.setChoreAssigner(firstHouseManager);
        newChore2.setHouse(defaultHouse);
        newChore2.setChoreStatus(ChoreStatus.inProgress);
        choreRepository.save(newChore2);

        Chore newChore3 = new Chore();
        newChore3.setChoreName("Clean mop");
        newChore3.setChoreDescription("Mop bucket is dirty. Take it out!");
        newChore3.setDifficultyEstimator(2);
        newChore3.setDueDate(Date.valueOf("2023-12-12"));
        newChore3.setChoreAssignee(firstHouseMember);
        newChore3.setChoreAssigner(firstHouseManager);
        newChore3.setHouse(defaultHouse);
        newChore3.setChoreStatus(ChoreStatus.inProgress);
        choreRepository.save(newChore3);

        Chore newChore4 = new Chore();
        newChore4.setChoreName("Clean mop");
        newChore4.setChoreDescription("Mop bucket is dirty. Take it out!");
        newChore4.setDifficultyEstimator(2);
        newChore4.setDueDate(Date.valueOf("2023-12-12"));
        newChore4.setChoreAssignee(firstHouseMember);
        newChore4.setChoreAssigner(firstHouseManager);
        newChore4.setHouse(defaultHouse);
        newChore4.setChoreStatus(ChoreStatus.complete);
        choreRepository.save(newChore4);

        Chore newChore5 = new Chore();
        newChore5.setChoreName("Clean mop");
        newChore5.setChoreDescription("Mop bucket is dirty. Take it out!");
        newChore5.setDifficultyEstimator(2);
        newChore5.setDueDate(Date.valueOf("2023-12-12"));
        newChore5.setChoreAssignee(firstHouseMember);
        newChore5.setChoreAssigner(firstHouseManager);
        newChore5.setHouse(defaultHouse);
        newChore5.setChoreStatus(ChoreStatus.complete);
        choreRepository.save(newChore5);

        // Act
       List<Chore> choresInProgress = choreRepository.findAllByChoreStatus(ChoreStatus.inProgress);
       List<Chore> choresComplete = choreRepository.findAllByChoreStatus(ChoreStatus.complete);

        // Assert
        assertEquals(3, choresInProgress.size());
        assertEquals(2, choresComplete.size());
    }

    @Test
    public void testFindAllByDifficultyEstimator() {

        // Arrange 
        for(int i = 0; i < 5; i++) {
            Chore newCreatedChore = new Chore();
            newCreatedChore.setChoreName("Clean mop");
            newCreatedChore.setChoreDescription("Mop bucket is dirty. Take it out!");
            newCreatedChore.setDifficultyEstimator(9);
            newCreatedChore.setDueDate(Date.valueOf("2023-12-12"));
            newCreatedChore.setChoreAssignee(firstHouseMember);
            newCreatedChore.setChoreAssigner(firstHouseManager);
            newCreatedChore.setHouse(defaultHouse);
            newCreatedChore.setChoreStatus(ChoreStatus.inProgress);
            choreRepository.save(newCreatedChore);
        }

        // Act
        List<Chore> choresWithDifficulty2 = choreRepository.findAllByDifficultyEstimator(2);
        List<Chore> choresWithDifficulty9 = choreRepository.findAllByDifficultyEstimator(9);

        // Assert 
        assertTrue(choresWithDifficulty2.size() == 1);
        assertTrue(choresWithDifficulty9.size() == 5);
    }

    @Test
    public void testFindAllByDueDate() {

         // Arrange 
         for(int i = 0; i < 5; i++) {
            Chore newCreatedChore = new Chore();
            newCreatedChore.setChoreName("Clean mop");
            newCreatedChore.setChoreDescription("Mop bucket is dirty. Take it out!");
            newCreatedChore.setDifficultyEstimator(9);
            newCreatedChore.setDueDate(Date.valueOf("2023-12-12"));
            newCreatedChore.setChoreAssignee(firstHouseMember);
            newCreatedChore.setChoreAssigner(firstHouseManager);
            newCreatedChore.setHouse(defaultHouse);
            newCreatedChore.setChoreStatus(ChoreStatus.inProgress);
            choreRepository.save(newCreatedChore);
        }

        // Act
        List<Chore> choresWithDueDate20231212 = choreRepository.findAllByDueDate(Date.valueOf("2023-12-12"));

        // Assert 
        assertTrue(choresWithDueDate20231212.size() == 6);
    }

    @Test
    public void testFindAllBeforeDueDate() {
        
        // Arrange 
        for(int i = 0; i < 8; i++) {
            Chore newCreatedChore = new Chore();
            newCreatedChore.setChoreName("Clean mop");
            newCreatedChore.setChoreDescription("Mop bucket is dirty. Take it out!");
            newCreatedChore.setDifficultyEstimator(9);
            newCreatedChore.setDueDate(Date.valueOf("2023-12-25"));
            newCreatedChore.setChoreAssignee(firstHouseMember);
            newCreatedChore.setChoreAssigner(firstHouseManager);
            newCreatedChore.setHouse(defaultHouse);
            newCreatedChore.setChoreStatus(ChoreStatus.inProgress);
            choreRepository.save(newCreatedChore);
        }

        // Act 
        List<Chore> choresWithDueDateBeforeXmas = choreRepository.findAllByDueDateBefore(Date.valueOf("2023-12-25"));
        List<Chore> choresWithDueDateBeforeBoxingDay = choreRepository.findAllByDueDateBefore(Date.valueOf("2023-12-26"));

        // Assert
        assertEquals(1,choresWithDueDateBeforeXmas.size());
        assertEquals(9,choresWithDueDateBeforeBoxingDay.size());
    }

    @Test
    public void testFindAllByChoreAssignee() {
        
        // Arrange 
        for(int i = 0; i < 3; i++) {
            Chore newCreatedChore = new Chore();
            newCreatedChore.setChoreName("Clean mop");
            newCreatedChore.setChoreDescription("Mop bucket is dirty. Take it out!");
            newCreatedChore.setDifficultyEstimator(9);
            newCreatedChore.setDueDate(Date.valueOf("2023-12-25"));
            newCreatedChore.setChoreAssignee(firstHouseMember);
            newCreatedChore.setChoreAssigner(firstHouseManager);
            newCreatedChore.setHouse(defaultHouse);
            newCreatedChore.setChoreStatus(ChoreStatus.inProgress);
            choreRepository.save(newCreatedChore);
        }

        // Act 
        List<Chore> choresAssignedToFirstHouseMember = choreRepository.findByChoreAssignee(firstHouseMember);

        // Assert
        assertTrue(choresAssignedToFirstHouseMember.size() == 4);
    }

    @Test
    public void testFindAllByChoreAssigner() {
        
        // Arrange 
        for(int i = 0; i < 20; i++) {
            Chore newCreatedChore = new Chore();
            newCreatedChore.setChoreName("Clean mop");
            newCreatedChore.setChoreDescription("Mop bucket is dirty. Take it out!");
            newCreatedChore.setDifficultyEstimator(9);
            newCreatedChore.setDueDate(Date.valueOf("2023-12-25"));
            newCreatedChore.setChoreAssignee(firstHouseMember);
            newCreatedChore.setChoreAssigner(firstHouseManager);
            newCreatedChore.setHouse(defaultHouse);
            newCreatedChore.setChoreStatus(ChoreStatus.inProgress);
            choreRepository.save(newCreatedChore);
        }

        // Act 
        List<Chore> choresAssignedByFirstHouseManager = choreRepository.findByChoreAssigner(firstHouseManager);

        // Assert
        assertTrue(choresAssignedByFirstHouseManager.size() == 21);
    }

    @Test
    public void testFindAllByHouse() {
        
        // Arrange 
        for(int i = 0; i < 11; i++) {
            Chore newCreatedChore = new Chore();
            newCreatedChore.setChoreName("Clean mop");
            newCreatedChore.setChoreDescription("Mop bucket is dirty. Take it out!");
            newCreatedChore.setDifficultyEstimator(9);
            newCreatedChore.setDueDate(Date.valueOf("2023-12-25"));
            newCreatedChore.setChoreAssignee(firstHouseMember);
            newCreatedChore.setChoreAssigner(firstHouseManager);
            newCreatedChore.setHouse(defaultHouse);
            newCreatedChore.setChoreStatus(ChoreStatus.inProgress);
            choreRepository.save(newCreatedChore);
        }

        // Act 
        List<Chore> choresAssignedToDefaultHouse = choreRepository.findByHouse(defaultHouse);

        // Assert
        assertTrue(choresAssignedToDefaultHouse.size() == 12);
    }
}
