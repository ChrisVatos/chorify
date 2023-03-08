package com.dto;

import org.springframework.lang.NonNull;

public class HouseRequestDto {

    @NonNull
    private String houseName;

    @NonNull
    private int numberOfMembers;

    // Constructors
    public HouseRequestDto() {
    }

    public HouseRequestDto(String houseName, int numberOfMembers) {
        this.houseName = houseName;
        this.numberOfMembers = numberOfMembers;
    }

    // Getters + Setters
    public String getHouseName() {
        return this.houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getNumberOfMembers() {
        return this.numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }
    
}
