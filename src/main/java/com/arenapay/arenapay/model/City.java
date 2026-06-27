package com.arenapay.arenapay.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false, foreignKey = @ForeignKey(name = "fk_city_state"))
    private State state;

    public City() {
    }

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
