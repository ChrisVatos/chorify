package com.dto;

public class HouseResponseDto {

    private int id;

    private String houseName;

    private int  numberOfMembers;

    // Constructors

    public HouseResponseDto(int id, String houseName, int numberOfMembers) {
        this.id = id;
        this.houseName = houseName;
        this.numberOfMembers = numberOfMembers;
    }

    // Getters (No setters to avoid modifying the data retrieved from backend)
    public String getHouseName() {
        return this.houseName;
    }

    public int getNumberOfMembers() {
        return this.numberOfMembers;
    }

    public int getId() {
        return this.id;
    }
}
