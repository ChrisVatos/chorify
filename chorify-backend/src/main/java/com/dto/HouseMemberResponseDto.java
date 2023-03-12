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


    public HouseMemberResponseDto(int id, String name, String phoneNumber, String emailAddress, Set<Chore> chores) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.chores = chores;
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
}
