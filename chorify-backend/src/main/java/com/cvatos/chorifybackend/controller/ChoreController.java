package com.cvatos.chorifybackend.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvatos.chorifybackend.model.Chore;
import com.cvatos.chorifybackend.model.Chore.ChoreStatus;
import com.cvatos.chorifybackend.service.ChoreService;
import com.dto.ChoreRequestDto;
import com.dto.ChoreResponseDto;

@Controller
public class ChoreController {

    @Autowired
    private ChoreService choreService;

    @PostMapping("/chores/newChore")
    public ResponseEntity<ChoreResponseDto> createChore(@RequestBody ChoreRequestDto choreRequest) {

        Chore choreToCreate = new Chore(
            choreRequest.getChoreName(), 
            choreRequest.getChoreDescription(), 
            choreRequest.getDifficultyEstimator(), 
            choreRequest.getDueDate(), 
            choreRequest.getChoreStatus()
        );

        int choreAssignerID = choreRequest.getChoreAssignerID();
        int choreAssigneeID = choreRequest.getChoreAssigneeID();
        int choreHouseID = choreRequest.getHouseID();

        Chore createdChore = choreService.createChore(choreToCreate, choreAssignerID, choreAssigneeID, choreHouseID);

        ChoreResponseDto createdChoreResponse = new ChoreResponseDto(
            createdChore.getId(),
            createdChore.getChoreDescription(),
            createdChore.getChoreName(),
            createdChore.getChoreStatus(),
            createdChore.getDifficultyEstimator(),
            createdChore.getDueDate(),
            createdChore.getChoreAssigner().getName(),
            createdChore.getChoreAssignee().getName(),
            createdChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(createdChoreResponse, HttpStatus.CREATED);
    }

    @GetMapping("/chores/id/{id}")
    public ResponseEntity<ChoreResponseDto> getChoreByID(@PathVariable int id) {
        
        Chore retrievedChore = choreService.getChoreById(id);

        ChoreResponseDto retrievedChoreResponse = new ChoreResponseDto(
            retrievedChore.getId(),
            retrievedChore.getChoreDescription(),
            retrievedChore.getChoreName(),
            retrievedChore.getChoreStatus(),
            retrievedChore.getDifficultyEstimator(),
            retrievedChore.getDueDate(),
            retrievedChore.getChoreAssigner().getName(),
            retrievedChore.getChoreAssignee().getName(),
            retrievedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(retrievedChoreResponse, HttpStatus.OK);
    }

    @GetMapping("/chores/status/{houseID}/{status}")
    public ResponseEntity<List<ChoreResponseDto>> getChoresByStatus(@PathVariable ChoreStatus status, @PathVariable int houseID) {
        
        List<Chore> choresByStatus = choreService.getChoresByStatus(status, houseID);
        List<ChoreResponseDto> choresResponseByStatus = new ArrayList<ChoreResponseDto>();

        for(Chore chore: choresByStatus) {
            ChoreResponseDto choreResponse = new ChoreResponseDto(
                chore.getId(),
                chore.getChoreDescription(),
                chore.getChoreName(),
                chore.getChoreStatus(),
                chore.getDifficultyEstimator(),
                chore.getDueDate(),
                chore.getChoreAssigner().getName(),
                chore.getChoreAssignee().getName(),
                chore.getHouse().getId()
            );

            choresResponseByStatus.add(choreResponse);
        }

        return new ResponseEntity<List<ChoreResponseDto>>(choresResponseByStatus, HttpStatus.OK);
    }

    @GetMapping("/chores/difficulty/{houseID}/{difficulty}")
    public ResponseEntity<List<ChoreResponseDto>> getChoresByStatus(@PathVariable int difficulty, @PathVariable int houseID) {
      
        List<Chore> choresByDifficulty = choreService.getChoresByDifficulty(difficulty, houseID);
        List<ChoreResponseDto> choresResponseByDifficulty = new ArrayList<ChoreResponseDto>();

        for(Chore chore: choresByDifficulty) {
            ChoreResponseDto choreResponse = new ChoreResponseDto(
                chore.getId(),
                chore.getChoreDescription(),
                chore.getChoreName(),
                chore.getChoreStatus(),
                chore.getDifficultyEstimator(),
                chore.getDueDate(),
                chore.getChoreAssigner().getName(),
                chore.getChoreAssignee().getName(),
                chore.getHouse().getId()
            );

            choresResponseByDifficulty.add(choreResponse);
        }

        return new ResponseEntity<List<ChoreResponseDto>>(choresResponseByDifficulty, HttpStatus.OK);
    }

    @GetMapping("/chores/specificDate/{houseID}/{dueDate}")
    public ResponseEntity<List<ChoreResponseDto>> getChoresDueOnSpecificDate(@PathVariable Date dueDate, @PathVariable int houseID) {

        List<Chore> choresDueOnSpecificDate = choreService.getChoresDueOnSpecificDate(dueDate, houseID);
        List<ChoreResponseDto> choresResponseDueOnSpecificDate = new ArrayList<ChoreResponseDto>();

        for(Chore chore: choresDueOnSpecificDate) {
            ChoreResponseDto choreResponse = new ChoreResponseDto(
                chore.getId(),
                chore.getChoreDescription(),
                chore.getChoreName(),
                chore.getChoreStatus(),
                chore.getDifficultyEstimator(),
                chore.getDueDate(),
                chore.getChoreAssigner().getName(),
                chore.getChoreAssignee().getName(),
                chore.getHouse().getId()
            );

            choresResponseDueOnSpecificDate.add(choreResponse);
        }

        return new ResponseEntity<List<ChoreResponseDto>>(choresResponseDueOnSpecificDate, HttpStatus.OK);
    }

    @GetMapping("/chores/onOrBeforeDate/{houseID}/{dueDate}")
    public ResponseEntity<List<ChoreResponseDto>> getChoresDueOnOrBeforeSpecificDate(@PathVariable Date dueDate, @PathVariable int houseID) {

        List<Chore> choresDueOnOrBeforeSpecificDate = choreService.getChoresDueOnOrBeforeSpecificDate(dueDate, houseID);
        List<ChoreResponseDto> choresResponseDueOnOrBeforeSpecificDate = new ArrayList<ChoreResponseDto>();

        for(Chore chore: choresDueOnOrBeforeSpecificDate) {
            ChoreResponseDto choreResponse = new ChoreResponseDto(
                chore.getId(),
                chore.getChoreDescription(),
                chore.getChoreName(),
                chore.getChoreStatus(),
                chore.getDifficultyEstimator(),
                chore.getDueDate(),
                chore.getChoreAssigner().getName(),
                chore.getChoreAssignee().getName(),
                chore.getHouse().getId()
            );

            choresResponseDueOnOrBeforeSpecificDate.add(choreResponse);
        }

        return new ResponseEntity<List<ChoreResponseDto>>(choresResponseDueOnOrBeforeSpecificDate, HttpStatus.OK);
    }

    @GetMapping("/chores/choreAssigner/{choreAssignerID}")
    public ResponseEntity<List<ChoreResponseDto>> getChoresByChoreAssigner(@PathVariable int choreAssignerID) {

        List<Chore> choresByAssigner= choreService.getChoresByAssigner(choreAssignerID);
        List<ChoreResponseDto> choresResponseByAssigner = new ArrayList<ChoreResponseDto>();

        for(Chore chore: choresByAssigner) {
            ChoreResponseDto choreResponse = new ChoreResponseDto(
                chore.getId(),
                chore.getChoreDescription(),
                chore.getChoreName(),
                chore.getChoreStatus(),
                chore.getDifficultyEstimator(),
                chore.getDueDate(),
                chore.getChoreAssigner().getName(),
                chore.getChoreAssignee().getName(),
                chore.getHouse().getId()
            );

            choresResponseByAssigner.add(choreResponse);
        }

        return new ResponseEntity<List<ChoreResponseDto>>(choresResponseByAssigner, HttpStatus.OK);
    }

    @GetMapping("/chores/choreAssignee/{choreAssigneeID}")
    public ResponseEntity<List<ChoreResponseDto>> getChoresByChoreAssignee(@PathVariable int choreAssigneeID) {

        List<Chore> choresByAssignee= choreService.getChoresByAssignee(choreAssigneeID);
        List<ChoreResponseDto> choresResponseByAssignee = new ArrayList<ChoreResponseDto>();

        for(Chore chore: choresByAssignee) {
            ChoreResponseDto choreResponse = new ChoreResponseDto(
                chore.getId(),
                chore.getChoreDescription(),
                chore.getChoreName(),
                chore.getChoreStatus(),
                chore.getDifficultyEstimator(),
                chore.getDueDate(),
                chore.getChoreAssigner().getName(),
                chore.getChoreAssignee().getName(),
                chore.getHouse().getId()
            );

            choresResponseByAssignee.add(choreResponse);
        }

        return new ResponseEntity<List<ChoreResponseDto>>(choresResponseByAssignee, HttpStatus.OK);
    }

    @GetMapping("/chores/house/{houseID}")
    public ResponseEntity<List<ChoreResponseDto>> getChoresByHouse(@PathVariable int houseID) {

        List<Chore> choresByHouse= choreService.getChoresByHouse(houseID);
        List<ChoreResponseDto> choresResponseByHouse= new ArrayList<ChoreResponseDto>();

        for(Chore chore: choresByHouse) {
            ChoreResponseDto choreResponse = new ChoreResponseDto(
                chore.getId(),
                chore.getChoreDescription(),
                chore.getChoreName(),
                chore.getChoreStatus(),
                chore.getDifficultyEstimator(),
                chore.getDueDate(),
                chore.getChoreAssigner().getName(),
                chore.getChoreAssignee().getName(),
                chore.getHouse().getId()
            );

            choresResponseByHouse.add(choreResponse);
        }

        return new ResponseEntity<List<ChoreResponseDto>>(choresResponseByHouse, HttpStatus.OK);
    }

    @PutMapping("/chores/update/name/{choreID}/{newName}")
    public ResponseEntity<ChoreResponseDto> updateChoreName(@PathVariable int choreID, @PathVariable String newName) {

        Chore updatedChore = choreService.updateChoreName(choreID, newName);

        ChoreResponseDto updatedChoreResponse = new ChoreResponseDto(
            updatedChore.getId(),
            updatedChore.getChoreDescription(),
            updatedChore.getChoreName(),
            updatedChore.getChoreStatus(),
            updatedChore.getDifficultyEstimator(),
            updatedChore.getDueDate(),
            updatedChore.getChoreAssigner().getName(),
            updatedChore.getChoreAssignee().getName(),
            updatedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(updatedChoreResponse, HttpStatus.OK);
    }

    @PutMapping("/chores/update/description/{choreID}/{newDescription}")
    public ResponseEntity<ChoreResponseDto> updateChoreDescription(@PathVariable int choreID, @PathVariable String newDescription) {

        Chore updatedChore = choreService.updateChoreDescription(choreID, newDescription);

        ChoreResponseDto updatedChoreResponse = new ChoreResponseDto(
            updatedChore.getId(),
            updatedChore.getChoreDescription(),
            updatedChore.getChoreName(),
            updatedChore.getChoreStatus(),
            updatedChore.getDifficultyEstimator(),
            updatedChore.getDueDate(),
            updatedChore.getChoreAssigner().getName(),
            updatedChore.getChoreAssignee().getName(),
            updatedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(updatedChoreResponse, HttpStatus.OK);
    }

    @PutMapping("/chores/update/status/{choreID}/{newStatus}")
    public ResponseEntity<ChoreResponseDto> updateChoreStatus(@PathVariable int choreID, @PathVariable ChoreStatus newStatus) {

        Chore updatedChore = choreService.updateChoreStatus(choreID, newStatus);

        ChoreResponseDto updatedChoreResponse = new ChoreResponseDto(
            updatedChore.getId(),
            updatedChore.getChoreDescription(),
            updatedChore.getChoreName(),
            updatedChore.getChoreStatus(),
            updatedChore.getDifficultyEstimator(),
            updatedChore.getDueDate(),
            updatedChore.getChoreAssigner().getName(),
            updatedChore.getChoreAssignee().getName(),
            updatedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(updatedChoreResponse, HttpStatus.OK);
    }

    @PutMapping("/chores/update/difficulty/{choreID}/{newDifficulty}")
    public ResponseEntity<ChoreResponseDto> updateChoreDifficulty(@PathVariable int choreID, @PathVariable int newDifficulty) {

        Chore updatedChore = choreService.updateDifficulty(choreID, newDifficulty);

        ChoreResponseDto updatedChoreResponse = new ChoreResponseDto(
            updatedChore.getId(),
            updatedChore.getChoreDescription(),
            updatedChore.getChoreName(),
            updatedChore.getChoreStatus(),
            updatedChore.getDifficultyEstimator(),
            updatedChore.getDueDate(),
            updatedChore.getChoreAssigner().getName(),
            updatedChore.getChoreAssignee().getName(),
            updatedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(updatedChoreResponse, HttpStatus.OK);
    }

    @PutMapping("/chores/update/dueDate/{choreID}/{newDueDate}")
    public ResponseEntity<ChoreResponseDto> updateChoreDueDate(@PathVariable int choreID, @PathVariable Date newDueDate) {

        Chore updatedChore = choreService.updateDueDate(choreID, newDueDate);

        ChoreResponseDto updatedChoreResponse = new ChoreResponseDto(
            updatedChore.getId(),
            updatedChore.getChoreDescription(),
            updatedChore.getChoreName(),
            updatedChore.getChoreStatus(),
            updatedChore.getDifficultyEstimator(),
            updatedChore.getDueDate(),
            updatedChore.getChoreAssigner().getName(),
            updatedChore.getChoreAssignee().getName(),
            updatedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(updatedChoreResponse, HttpStatus.OK);
    }

    @PutMapping("/chores/update/choreAssigner/{choreID}/{newChoreAssignerID}")
    public ResponseEntity<ChoreResponseDto> updateChoreAssigner(@PathVariable int choreID, @PathVariable int newChoreAssignerID) {

        Chore updatedChore = choreService.updateChoreAssigner(choreID, newChoreAssignerID);

        ChoreResponseDto updatedChoreResponse = new ChoreResponseDto(
            updatedChore.getId(),
            updatedChore.getChoreDescription(),
            updatedChore.getChoreName(),
            updatedChore.getChoreStatus(),
            updatedChore.getDifficultyEstimator(),
            updatedChore.getDueDate(),
            updatedChore.getChoreAssigner().getName(),
            updatedChore.getChoreAssignee().getName(),
            updatedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(updatedChoreResponse, HttpStatus.OK);
    }

    @PutMapping("/chores/update/choreAssignee/{choreID}/{newChoreAssigneeID}")
    public ResponseEntity<ChoreResponseDto> updateChoreAssignee(@PathVariable int choreID, @PathVariable int newChoreAssigneeID) {

        Chore updatedChore = choreService.updateChoreAssignee(choreID, newChoreAssigneeID);

        ChoreResponseDto updatedChoreResponse = new ChoreResponseDto(
            updatedChore.getId(),
            updatedChore.getChoreDescription(),
            updatedChore.getChoreName(),
            updatedChore.getChoreStatus(),
            updatedChore.getDifficultyEstimator(),
            updatedChore.getDueDate(),
            updatedChore.getChoreAssigner().getName(),
            updatedChore.getChoreAssignee().getName(),
            updatedChore.getHouse().getId()
        );

        return new ResponseEntity<ChoreResponseDto>(updatedChoreResponse, HttpStatus.OK);
    }

    @DeleteMapping("/chores/delete/{id}")
    public ResponseEntity<String> deleteChore(@PathVariable int id) {
        
        choreService.deleteChore(id);

        return new ResponseEntity<String>("Chore with id " + id + " has been deleted.", HttpStatus.OK);
    }
}
