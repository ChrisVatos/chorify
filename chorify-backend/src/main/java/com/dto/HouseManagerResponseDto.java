package com.dto;

import java.util.Set;

import com.cvatos.chorifybackend.model.Chore;

public class HouseManagerResponseDto {

    private int id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private Set<Chore> chores;

    public HouseManagerResponseDto(int id, String name, String phoneNumber, String emailAddress, Set<Chore> chores) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.chores = chores;
    }

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
