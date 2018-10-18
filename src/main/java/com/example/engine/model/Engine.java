package com.example.engine.model;

import java.util.Objects;

public class Engine {
    private int id;
    private String name;
    private int power;
    private Petrol petrol;

    public Engine() {
    }

    public Engine(int id, String name, int power, Petrol petrol) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.petrol = petrol;
    }

    public Engine(int id, String name, int power) {
        this.id = id;
        this.name = name;
        this.petrol = Petrol.PETROL;
        this.power = power;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Petrol getPetrol() {
        return petrol;
    }

    public void setPetrol(Petrol petrol) {
        this.petrol = petrol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return id == engine.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
