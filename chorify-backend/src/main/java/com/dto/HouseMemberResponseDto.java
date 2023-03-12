package com.dto;

import java.util.Set;

import com.cvatos.chorifybackend.model.Chore;
import com.cvatos.chorifybackend.model.House;

public class HouseMemberResponseDto {

    private int id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private House house;
    private Set<Chore> chores;


    public HouseMemberResponseDto(int id, String name, String phoneNumber, String emailAddress, House house) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.house = house;
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

    public House getHouse() {
        return this.house;
    }

    public Set<Chore> getChores() {
        return this.chores;
    } 
}
