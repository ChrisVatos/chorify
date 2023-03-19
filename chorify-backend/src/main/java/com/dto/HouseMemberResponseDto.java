package com.dto;

import java.util.Set;

import com.cvatos.chorifybackend.model.Chore;
import com.cvatos.chorifybackend.model.House;

public class HouseMemberResponseDto {

    private int id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private Set<Chore> chores;
    private int houseID;


    public HouseMemberResponseDto(int id, String name, String phoneNumber, String emailAddress, Set<Chore> chores, int houseID) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.chores = chores;
        this.houseID = houseID;
    }

    // Getters (No setters to avoid modifying the data retrieved from backend)
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Set<Chore> getChores() {
        return this.chores;
    } 

    public int getHouseID() {
        return this.houseID;
    }
}
