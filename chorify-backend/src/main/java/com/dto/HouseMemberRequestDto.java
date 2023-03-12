package com.dto;

import com.cvatos.chorifybackend.model.House;

import io.micrometer.common.lang.NonNull;

public class HouseMemberRequestDto {

    @NonNull
    private String name;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String emailAddress;

    @NonNull
    private House house;


    // Constructors
    public HouseMemberRequestDto(String name, String phoneNumber, String emailAddress, House house) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.house = house;
    }

    // Getters + Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public House getHouse() {
        return this.house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
