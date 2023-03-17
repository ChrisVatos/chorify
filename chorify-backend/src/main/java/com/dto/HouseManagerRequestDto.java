package com.dto;


import org.springframework.lang.NonNull;

import com.cvatos.chorifybackend.model.HouseManager.HouseManagerType;

public class HouseManagerRequestDto {
    
    @NonNull
    private String name;

    @NonNull
    private String emailAddress;

    @NonNull
    private String phoneNumber;

    @NonNull
    private HouseManagerType houseManagerType;

    @NonNull
    private int houseID;

    // Constructor
    public HouseManagerRequestDto(String name, String emailAddress, String phoneNumber, HouseManagerType houseManagerType, int houseID) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.houseManagerType = houseManagerType;
        this.houseID = houseID;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public HouseManagerType getHouseManagerType() {
        return this.houseManagerType;
    }

    public void setHouseManagerType(HouseManagerType houseManagerType) {
        this.houseManagerType = houseManagerType;
    }

    public int getHouseID() {
        return this.houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    
}
