package com.cvatos.chorifybackend.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chores")
public class Chore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chore_name", nullable = false)
    private String choreName;
    
    @Column(name = "chore_description", nullable = false)
    private String choreDescription;

    @Column(name = "difficulty_estimator", nullable = false)
    private int difficultyEstimator;

    @Column(name = "due_date", nullable = false, columnDefinition = "DATE")
    private Date dueDate;

    @Column(name = "chore_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChoreStatus choreStatus;

    @ManyToOne
    @JoinColumn(name = "choreAssigner_id", referencedColumnName = "id", nullable = true)
    private HouseManager choreAssigner;
    
    @ManyToOne
    @JoinColumn(name = "choreAssignee_id", referencedColumnName = "id", nullable = true)
    private HouseMember choreAssignee;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = true)
    private House house;


    public Chore() {

    }
    
    public Chore(String choreName, String choreDescription, int difficultyEstimator, Date dueDate, ChoreStatus choreStatus) {
        this.id = id;
        this.choreName = choreName;
        this.choreDescription = choreDescription;
        this.difficultyEstimator = difficultyEstimator;
        this.dueDate = dueDate;
        this.choreStatus = choreStatus;
    }
    
    public House getHouse() {
        return this.house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChoreName() {
        return this.choreName;
    }

    public void setChoreName(String choreName) {
        this.choreName = choreName;
    }

    public String getChoreDescription() {
        return this.choreDescription;
    }

    public void setChoreDescription(String choreDescription) {
        this.choreDescription = choreDescription;
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

    public ChoreStatus getChoreStatus() {
        return this.choreStatus;
    }

    public void setChoreStatus(ChoreStatus choreStatus) {
        this.choreStatus = choreStatus;
    }

    public HouseManager getChoreAssigner() {
        return this.choreAssigner;
    }

    public void setChoreAssigner(HouseManager choreAssigner) {
        this.choreAssigner = choreAssigner;
    }

    public HouseMember getChoreAssignee() {
        return this.choreAssignee;
    }

    public void setChoreAssignee(HouseMember choreAssignee) {
        this.choreAssignee = choreAssignee;
    }

    public enum ChoreStatus {
        notStarted,
        inProgress,
        waitingForReview,
        complete
    }
}
