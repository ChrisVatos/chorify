package com.cvatos.chorifybackend.model;

import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private int id;

    @Column(name = "house_name", nullable = false)
    private String houseName;

    @Column(name = "number_of_members", nullable = false)
    private int numberOfMembers;

    @OneToMany(mappedBy = "house", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<HouseManager> houseManagers;

    @OneToMany(mappedBy = "house", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<HouseMember> houseMembers;

    @OneToMany(mappedBy= "house", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Chore> chores;

    // Constructors
    public House() {
    
    }

    public House(String houseName, int numberOfMembers) {
        this.houseName = houseName;
        this.numberOfMembers = numberOfMembers;
    }

    // Getters + Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Set<HouseManager> getHouseManagers() {
        return this.houseManagers;
    }

    public void setHouseManagers(Set<HouseManager> houseManagers) {
        this.houseManagers = houseManagers;
    }

    public Set<HouseMember> getHouseMembers() {
        return this.houseMembers;
    }

    public void setHouseMembers(Set<HouseMember> houseMembers) {
        this.houseMembers = houseMembers;
    }
}
