package com.cvatos.chorifybackend.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "houseMembers")
public class HouseMember extends Person {
    
    @ManyToOne
    private House house;

    @OneToMany(mappedBy = "choreAssignee")
    private Set<Chore> chores;

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
}
