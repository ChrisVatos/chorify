package com.cvatos.chorifybackend.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "houseManagers")
public class HouseManager extends Person {

    @Column(name = "house_manager_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HouseManagerType houseManagerType;

    @ManyToOne
    private House house;

    @OneToMany(mappedBy = "choreAssigner")
    private Set<Chore> chores;

    public HouseManager(String name, String phoneNumber, String emailAddress, HouseManagerType houseManagerType ) {
        super(name, phoneNumber, emailAddress);
        this.houseManagerType = houseManagerType;
     }

    public HouseManager() {
    }

    public HouseManagerType getHouseManagerType() {
        return this.houseManagerType;
    }

    public void setHouseManagerType(HouseManagerType houseManagerType) {
        this.houseManagerType = houseManagerType;
    }

    public House getHouse() {
        return this.house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Set<Chore> getChores() {
        return this.chores;
    }

    public void setChores(Set<Chore> chores) {
        this.chores = chores;
    }
    

    // Enumeration class for HouseManagerType
    public enum HouseManagerType {
        parent,
        guardian,
        olderSibling,
        extendedFamily,
        other
    }
    
}
