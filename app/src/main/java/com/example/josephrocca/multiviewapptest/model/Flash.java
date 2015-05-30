package com.example.josephrocca.multiviewapptest.model;

import java.util.Date;

public class Flash {
    private Date date_flash;
    private Player player;
    private Zone zone;
    private int nbpoints;

    public Flash(Date date_flash, Player player, Zone zone, int nbpoints) {
        this.date_flash = date_flash;
        this.player = player;
        this.zone = zone;
        this.nbpoints = nbpoints;
    }

    public int getNbpoints() {
        return nbpoints;
    }

    public void setNbpoints(int nbpoints) {
        this.nbpoints = nbpoints;
    }

    public Date getDate_flash() {
        return date_flash;
    }

    public void setDate_flash(Date date_flash) {
        this.date_flash = date_flash;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
