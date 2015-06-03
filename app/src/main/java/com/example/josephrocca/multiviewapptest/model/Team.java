package com.example.josephrocca.multiviewapptest.model;


import com.example.josephrocca.multiviewapptest.Control;

import java.util.ArrayList;
import java.util.HashMap;

public class Team {

    int nbpts;


    int nbJoueurs;
    int idx;
    private HashMap<String, Player> players;

    public Team(int i, int pt) {
        idx = i;
        nbpts = pt;
        players = new HashMap<String, Player>();
    }


    public int getNbpts() {
        return nbpts;
    }

    public void setNbpts(int nbpts) {
        this.nbpts = nbpts;
    }

    public int getIdx() {
        return idx;
    }

    public int getNbJoueurs() {
        return this.players.size();
    }

    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public boolean containsCurrentPlayer() {
        return this.players.containsKey(Control.getInstance().getUser().getLogin());
    }

    public boolean containsPlayer(String login) {
        return this.players.containsKey(login);
    }

    public void addPlayer(Player p) {
        this.players.put(p.getLogin(), p);
    }

    public ArrayList<Player> getPlayerList() {
        return new ArrayList<Player>(this.players.values());
    }

    public void removePlayer(String login) {
        if (this.players.containsKey(login))
            this.players.remove(login);
    }

}
