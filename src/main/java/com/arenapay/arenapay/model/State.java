package com.arenapay.arenapay.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name ="name", length = 100)
    private String name;

    protected State() {
    }

    public State(String name) {
        this.name = name;
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
}
