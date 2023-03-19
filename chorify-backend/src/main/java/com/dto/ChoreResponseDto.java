package com.dto;

import java.sql.Date;

import com.cvatos.chorifybackend.model.Chore.ChoreStatus;

public class ChoreResponseDto {

    private int id;
    private String choreDesciption;
    private String choreName;
    private ChoreStatus choreStatus;
    private int difficultyEstimator;
    private Date dueDate;
    private String choreAssignerName;
    private String choreAssigneeName;
    

    public ChoreResponseDto(int id, String choreDesciption, String choreName, ChoreStatus choreStatus, int difficultyEstimator, Date dueDate, String choreAssignerName, String choreAssigneeName) {
        this.id = id;
        this.choreDesciption = choreDesciption;
        this.choreName = choreName;
        this.choreStatus = choreStatus;
        this.difficultyEstimator = difficultyEstimator;
        this.dueDate = dueDate;
        this.choreAssignerName = choreAssignerName;
        this.choreAssigneeName = choreAssigneeName;
    }

    public int getId() {
        return this.id;
    }

    public String getChoreDesciption() {
        return this.choreDesciption;
    }

    public String getChoreName() {
        return this.choreName;
    }

    public ChoreStatus getChoreStatus() {
        return this.choreStatus;
    }

    public int getDifficultyEstimator() {
        return this.difficultyEstimator;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public String getChoreAssignerName() {
        return this.choreAssignerName;
    }

    public String getChoreAssigneeName() {
        return this.choreAssigneeName;
    }
}
