package com.example.josephrocca.multiviewapptest.model;

public class Zone {

    private int id;
    private String name;
    private int team;

    public Zone (int i, String n) {
        id = i;
        name = n;
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

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
}
