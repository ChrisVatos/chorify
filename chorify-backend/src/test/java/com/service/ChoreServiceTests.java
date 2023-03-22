package com.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.cvatos.chorifybackend.exception.ChorifyException;
import com.cvatos.chorifybackend.model.Chore;
import com.cvatos.chorifybackend.model.House;
import com.cvatos.chorifybackend.model.HouseManager;
import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.model.Chore.ChoreStatus;
import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;
import com.cvatos.chorifybackend.repository.ChoreRepository;
import com.cvatos.chorifybackend.repository.HouseManagerRepository;
import com.cvatos.chorifybackend.repository.HouseMemberRepository;
import com.cvatos.chorifybackend.repository.HouseRepository;
import com.cvatos.chorifybackend.service.ChoreService;
import com.cvatos.chorifybackend.service.HouseService;

@ExtendWith(MockitoExtension.class)
public class ChoreServiceTests {

    private House savedHouse;
    private HouseManager houseManager;
    private HouseMember houseMember;
    private Chore existingChore;

    @Mock
    private ChoreRepository choreRepository;

    @Mock
    private HouseService houseService;

    @Mock
    private HouseManagerRepository houseManagerRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseMemberRepository houseMemberRepository;

    @InjectMocks
    private ChoreService choreService;

    @BeforeEach
    public void setUp() {

        // Existing house
        int idHouse = 1;
        String houseName = "Test House";
        int numberOfMembers = 5;
        savedHouse = new House(houseName, numberOfMembers);
        savedHouse.setId(idHouse);

        // Existing house manager
        int idManager = 3;
        String nameManager = "Big Boss";
        String emailManager = "email@gmail.com";
        String phoneNumberManager = "514-873-0987";
        HouseManagerType type = HouseManagerType.guardian;
        houseManager = new HouseManager(nameManager, phoneNumberManager, emailManager, type);
        houseManager.setId(idManager);
        houseManager.setHouse(savedHouse);

        // Existing house member
        int idMember = 6;
        String nameMember = "Chris Vatos";
        String emailMember = "cvatos@gmail.com";
        String phoneNumberMember = "514-654-9873";
        houseMember = new HouseMember(nameMember, phoneNumberMember, emailMember);
        houseMember.setId(idMember);
        houseMember.setHouse(savedHouse);

        // Existing Chore
        int idChore = 89;
        String choreName = "Default Chore";
        String choreDescription = "Some description goes here";
        int difficuly = 5;
        Date dueDate = Date.valueOf("2023-08-08");
        ChoreStatus status = ChoreStatus.inProgress;
        existingChore = new Chore(choreName, choreDescription, difficuly, dueDate, status);
        existingChore.setId(idChore);
        existingChore.setChoreAssignee(houseMember);
        existingChore.setChoreAssigner(houseManager);
        existingChore.setHouse(savedHouse);
    }

    @Test
    public void testCreateChore() {

        String choreName = "Clean Room";
        String choreDescription = "Make sure bed is made and baseboards dusted!";
        int difficuly = 5;
        Date dueDate = Date.valueOf("2023-12-12");
        ChoreStatus status = ChoreStatus.inProgress;
        Chore choreToCreate = new Chore(choreName, choreDescription, difficuly, dueDate, status);
    

        Mockito.when(houseManagerRepository.findById(houseManager.getId())).thenReturn(houseManager);
        Mockito.when(houseMemberRepository.findById(houseMember.getId())).thenReturn(houseMember);
        Mockito.when(houseRepository.findById(savedHouse.getId())).thenReturn(savedHouse);
        Mockito.when(choreRepository.save(Mockito.any(Chore.class))).thenReturn(choreToCreate);


        Chore createdChore = choreService.createChore(choreToCreate, houseManager.getId(), houseMember.getId(), savedHouse.getId());

        assertTrue(createdChore.getChoreName().equals(choreName));
        assertTrue(createdChore.getChoreDescription().equals(choreDescription));
        assertTrue(createdChore.getDifficultyEstimator() == difficuly);
        assertTrue(createdChore.getDueDate().equals(dueDate));
        assertTrue(createdChore.getChoreStatus().equals(status));
        assertTrue(createdChore.getChoreAssigner().getId() == houseManager.getId());
        assertTrue(createdChore.getChoreAssignee().getId() == houseMember.getId());
        assertTrue(createdChore.getHouse().getId() == savedHouse.getId());
    }

    @Test
    public void testGetChoreByValidId() {
       
        Mockito.when(choreRepository.findById(existingChore.getId())).thenReturn(existingChore);

        Chore retrievedChore = choreService.getChoreById(existingChore.getId());

        assertTrue(retrievedChore.getChoreName().equals(existingChore.getChoreName()));
        assertTrue(retrievedChore.getChoreDescription().equals(existingChore.getChoreDescription()));
        assertTrue(retrievedChore.getDifficultyEstimator() == existingChore.getDifficultyEstimator());
        assertTrue(retrievedChore.getDueDate().equals(existingChore.getDueDate()));
        assertTrue(retrievedChore.getChoreStatus().equals(existingChore.getChoreStatus()));
        assertTrue(retrievedChore.getChoreAssigner().getId() == houseManager.getId());
        assertTrue(retrievedChore.getChoreAssignee().getId() == houseMember.getId());
        assertTrue(retrievedChore.getHouse().getId() == savedHouse.getId());
    }

    @Test
    public void testGetChoreByInvalidId() {
       
        int invalidID = 190;
        Mockito.when(choreRepository.findById(invalidID)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> choreService.getChoreById(invalidID));

        assertTrue(exception.getMessage().equals("Chore with id " + invalidID + " does not exist."));
        assertTrue(exception.getStatus().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testGetChoreByValidName() {
       
        Mockito.when(choreRepository.findByChoreName(existingChore.getChoreName())).thenReturn(existingChore);

        Chore retrievedChore = choreService.getChoreByName(existingChore.getChoreName());

        assertTrue(retrievedChore.getChoreName().equals(existingChore.getChoreName()));
        assertTrue(retrievedChore.getChoreDescription().equals(existingChore.getChoreDescription()));
        assertTrue(retrievedChore.getDifficultyEstimator() == existingChore.getDifficultyEstimator());
        assertTrue(retrievedChore.getDueDate().equals(existingChore.getDueDate()));
        assertTrue(retrievedChore.getChoreStatus().equals(existingChore.getChoreStatus()));
        assertTrue(retrievedChore.getChoreAssigner().getId() == houseManager.getId());
        assertTrue(retrievedChore.getChoreAssignee().getId() == houseMember.getId());
        assertTrue(retrievedChore.getHouse().getId() == savedHouse.getId());
    }

    @Test
    public void testGetChoreByInvalidName() {
       
        String invalidName = "Invalid";
        Mockito.when(choreRepository.findByChoreName(invalidName)).thenReturn(null);

        ChorifyException exception = assertThrows(ChorifyException.class, () -> choreService.getChoreByName(invalidName));

        assertTrue(exception.getMessage().equals("Chore with id " + invalidName + " does not exist."));
        assertTrue(exception.getStatus().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testGetChoresByStatus() {

        // New Chore
        int idChore = 89;
        String choreName = "Default Chore";
        String choreDescription = "Some description goes here";
        int difficuly = 5;
        Date dueDate = Date.valueOf("2023-08-08");
        ChoreStatus status = ChoreStatus.inProgress;
        Chore newChore = new Chore(choreName, choreDescription, difficuly, dueDate, status);
        newChore.setId(idChore);
        newChore.setChoreAssignee(houseMember);
        newChore.setChoreAssigner(houseManager);
        newChore.setHouse(savedHouse);

        List<Chore> choresInProgress = new ArrayList<Chore>();
        choresInProgress.add(existingChore);
        choresInProgress.add(newChore);

        Mockito.when(choreRepository.findAllByChoreStatus(ChoreStatus.inProgress)).thenReturn(choresInProgress);

        List<Chore> retrievedChoresInProgress = choreService.getChoresByStatus(ChoreStatus.inProgress, savedHouse.getId());

        assertTrue(retrievedChoresInProgress.size() == 2);
    }

    @Test
    public void testGetChoresByDifficulty() {

        // New Chore
        int idChore = 89;
        String choreName = "Default Chore";
        String choreDescription = "Some description goes here";
        int difficuly = 5;
        Date dueDate = Date.valueOf("2023-08-08");
        ChoreStatus status = ChoreStatus.inProgress;
        Chore newChore = new Chore(choreName, choreDescription, difficuly, dueDate, status);
        newChore.setId(idChore);
        newChore.setChoreAssignee(houseMember);
        newChore.setChoreAssigner(houseManager);
        newChore.setHouse(savedHouse);

        List<Chore> choresWithDifficultEqualTo5 = new ArrayList<Chore>();
        choresWithDifficultEqualTo5.add(existingChore);
        choresWithDifficultEqualTo5.add(newChore);

        Mockito.when(choreRepository.findAllByDifficultyEstimator(difficuly)).thenReturn(choresWithDifficultEqualTo5);

        List<Chore> retrievedChoresWithDesiredDifficulty = choreService.getChoresByDifficulty(difficuly, savedHouse.getId());

        assertTrue(retrievedChoresWithDesiredDifficulty.size() == 2);
    }

    @Test
    public void testGetChoresDueOnSpecificDate() {

        // New Chore
        int idChore = 89;
        String choreName = "Default Chore";
        String choreDescription = "Some description goes here";
        int difficuly = 5;
        Date dueDate = Date.valueOf("2023-08-08");
        ChoreStatus status = ChoreStatus.inProgress;
        Chore newChore = new Chore(choreName, choreDescription, difficuly, dueDate, status);
        newChore.setId(idChore);
        newChore.setChoreAssignee(houseMember);
        newChore.setChoreAssigner(houseManager);
        newChore.setHouse(savedHouse);

        List<Chore> choresWithSpecificDueDate = new ArrayList<Chore>();
        choresWithSpecificDueDate.add(existingChore);
        choresWithSpecificDueDate.add(newChore);

        Mockito.when(choreRepository.findAllByDueDate(dueDate)).thenReturn(choresWithSpecificDueDate);

        List<Chore> retrievedChoresWithDesiredDifficulty = choreService.getChoresDueOnSpecificDate(dueDate, savedHouse.getId());

        assertTrue(retrievedChoresWithDesiredDifficulty.size() == 2);
    }

    @Test
    public void testGetChoresDueOnOrBeforeSpecificDate() {

        // Chore Due Before
        int idChoreBefore = 89;
        String choreNameBefore = "Default Chore";
        String choreDescriptionBefore = "Some description goes here";
        int difficulyBefore = 5;
        Date dueDateBefore = Date.valueOf("2023-05-05");
        ChoreStatus statusBefore = ChoreStatus.inProgress;
        Chore newChoreDueBefore = new Chore(choreNameBefore, choreDescriptionBefore, difficulyBefore, dueDateBefore, statusBefore);
        newChoreDueBefore.setId(idChoreBefore);
        newChoreDueBefore.setChoreAssignee(houseMember);
        newChoreDueBefore.setChoreAssigner(houseManager);
        newChoreDueBefore.setHouse(savedHouse);

        // New Chore
        int idChore = 89;
        String choreName = "Default Chore";
        String choreDescription = "Some description goes here";
        int difficuly = 5;
        Date dueDate = Date.valueOf("2023-08-08");
        ChoreStatus status = ChoreStatus.inProgress;
        Chore newChore = new Chore(choreName, choreDescription, difficuly, dueDate, status);
        newChore.setId(idChore);
        newChore.setChoreAssignee(houseMember);
        newChore.setChoreAssigner(houseManager);
        newChore.setHouse(savedHouse);

        List<Chore> choresDueBeforeOrOnSpecificDueDate = new ArrayList<Chore>();
        choresDueBeforeOrOnSpecificDueDate.add(existingChore);
        choresDueBeforeOrOnSpecificDueDate.add(newChore);
        choresDueBeforeOrOnSpecificDueDate.add(newChoreDueBefore);


        Mockito.when(choreRepository.findAllByDueDate(dueDate)).thenReturn(choresDueBeforeOrOnSpecificDueDate);

        List<Chore> retrievedChoresWithDesiredDifficulty = choreService.getChoresDueOnOrBeforeSpecificDate(dueDate, savedHouse.getId());

        assertTrue(retrievedChoresWithDesiredDifficulty.size() == 3);
    } 

    @Test
    public void testUpdateChoreStatus() {

        ChoreStatus newStatus = ChoreStatus.complete;

        Chore updatedChore = existingChore;
        updatedChore.setChoreStatus(newStatus);

        Mockito.when(choreRepository.findById(existingChore.getId())).thenReturn(existingChore);
        Mockito.when(choreRepository.save(Mockito.any(Chore.class))).thenReturn(updatedChore);

        Chore updatedChoreRetrieved = choreService.updateChoreStatus(existingChore.getId(), newStatus);

        assertTrue(updatedChoreRetrieved.getChoreName().equals(existingChore.getChoreName()));
        assertTrue(updatedChoreRetrieved.getChoreDescription().equals(existingChore.getChoreDescription()));
        assertTrue(updatedChoreRetrieved.getDifficultyEstimator() == existingChore.getDifficultyEstimator());
        assertTrue(updatedChoreRetrieved.getDueDate().equals(existingChore.getDueDate()));
        assertTrue(updatedChoreRetrieved.getChoreStatus().equals(newStatus));
        assertTrue(updatedChoreRetrieved.getChoreAssigner().getId() == houseManager.getId());
        assertTrue(updatedChoreRetrieved.getChoreAssignee().getId() == houseMember.getId());
        assertTrue(updatedChoreRetrieved.getHouse().getId() == savedHouse.getId());
    }

    // Analogous for other updates

    @Test
    public void testDeleteByValidId() {

        Mockito.when(choreRepository.findById(existingChore.getId())).thenReturn(existingChore);

        choreService.deleteChore(existingChore.getId());

        Mockito.verify(choreRepository, times(1)).deleteById(existingChore.getId());
    }

}
