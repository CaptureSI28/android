package com.example.josephrocca.multiviewapptest.model;

import android.util.Log;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class Game {

    private int id;
    private String name;
    private Date dateDebut;
    private Date dateFin;
    private boolean isPrivate;
    private String creator;
    // HashMap of the teams (id of the teams, team)
    private HashMap<Integer, Team> teams;


    private ArrayList<Flash> flashs;

    public Game(int i, String n, String c, Date dd, Date df, boolean isp) {
        id = i;
        name = n;
        creator = c;
        dateDebut = dd;
        dateFin = df;
        isPrivate = isp;
        teams = new HashMap<Integer, Team>();
        teams.put(1, new Team(1, 0));
        teams.put(2, new Team(2, 0));
        teams.put(3, new Team(3, 0));
        teams.put(4, new Team(4, 0));
        flashs = new ArrayList<Flash>();
    }

    public Game() {
        teams = new HashMap<Integer, Team>();
        teams.put(1, new Team(1, 0));
        teams.put(2, new Team(2, 0));
        teams.put(3, new Team(3, 0));
        teams.put(4, new Team(4, 0));
        flashs = new ArrayList<Flash>();
    }

    public void setId(int i) {
        id = i;
    }

    public void setName(String n) {
        name = n;
    }

    public void setDateDebut(Date dd) {
        dateDebut = dd;
    }

    public void setDateFin(Date df) {
        dateFin = df;
    }

    public void setPrivate(boolean isp) {
        isPrivate = isp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateDebut() {
        return Util.getStringFromDate(dateDebut);
    }

    public String getDateFin() {
        return Util.getStringFromDate(dateFin);
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }


    public ArrayList<Flash> getFlashs() {
        return flashs;
    }

    public void setFlashs(ArrayList<Flash> flashs) {
        this.flashs = flashs;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean containsCurrentPlayer() {
        boolean find = false;
        for (Team t : teams.values()) {
            if (t.containsCurrentPlayer())
                find = true;
        }
        return find;
    }

    public int getTeamIdByLogin(String login) {
        int i = 0;
        for (Team team : teams.values()) {
            if (team.containsPlayer(login))
                i = team.getIdx();
        }
        return i;
    }

    public int getTeamIdCurrentUser() {
        int i = 0;
        for (Team team : teams.values()) {
            if (team.containsPlayer(Control.getInstance().getUser().getLogin()))
                i = team.getIdx();
        }
        return i;
    }

    public HashMap<Integer, Team> getTeams() {
        return teams;
    }

    public void setTeams(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    public void addPlayer(Player player, int teamId) {
        if (!teams.get(teamId).containsPlayer(player.getLogin()))
            teams.get(teamId).addPlayer(player);
    }

    public ArrayList<Player> getPlayersList() {
        ArrayList<Player> list = new ArrayList<Player>();
        for (Team team : teams.values()) {
            list.addAll(team.getPlayerList());
        }
        return list;
    }

    public void addFlash(Flash f) {
        flashs.add(f);
    }

}
