package com.dto;

import java.sql.Date;

import org.springframework.lang.NonNull;

import com.cvatos.chorifybackend.model.Chore.ChoreStatus;

public class ChoreRequestDto {

    @NonNull
    private String choreDescription;
    
    @NonNull
    private String choreName;

    @NonNull
    private ChoreStatus choreStatus;

    @NonNull
    private int difficultyEstimator;

    @NonNull
    private Date dueDate;

    @NonNull
    private int choreAssignerID;

    @NonNull
    private int choreAssigneeID;

    @NonNull
    private int houseID;


    public ChoreRequestDto(String choreDescription, String choreName, ChoreStatus choreStatus, int difficultyEstimator, Date dueDate, int choreAssignerID, int choreAssigneeID, int houseID) {
        this.choreDescription = choreDescription;
        this.choreName = choreName;
        this.choreStatus = choreStatus;
        this.difficultyEstimator = difficultyEstimator;
        this.dueDate = dueDate;
        this.choreAssignerID = choreAssignerID;
        this.choreAssigneeID = choreAssigneeID;
        this.houseID = houseID;
    }

    public String getChoreDescription() {
        return this.choreDescription;
    }

    public void setChoreDescription(String choreDescription) {
        this.choreDescription = choreDescription;
    }

    public String getChoreName() {
        return this.choreName;
    }

    public void setChoreName(String choreName) {
        this.choreName = choreName;
    }

    public ChoreStatus getChoreStatus() {
        return this.choreStatus;
    }

    public void setChoreStatus(ChoreStatus choreStatus) {
        this.choreStatus = choreStatus;
    }

    public int getDifficultyEstimator() {
        return this.difficultyEstimator;
    }

    public void setDifficultyEstimator(int difficultyEstimator) {
        this.difficultyEstimator = difficultyEstimator;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getChoreAssignerID() {
        return this.choreAssignerID;
    }

    public void setChoreAssignerID(int choreAssignerID) {
        this.choreAssignerID = choreAssignerID;
    }

    public int getChoreAssigneeID() {
        return this.choreAssigneeID;
    }

    public void setChoreAssigneeID(int choreAssigneeID) {
        this.choreAssigneeID = choreAssigneeID;
    }

    public int getHouseID() {
        return this.houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }


}