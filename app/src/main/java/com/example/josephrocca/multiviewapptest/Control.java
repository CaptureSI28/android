package com.example.josephrocca.multiviewapptest;


import android.graphics.Color;

import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.model.Team;
import com.example.josephrocca.multiviewapptest.model.Zone;

import java.util.ArrayList;
import java.util.HashMap;

public class Control {

    static private Control instance = new Control();

    private Player user;
    private HashMap<Integer, Game> games;
    private Game currentGame;
    private HashMap<Integer, Team> teams;
    private HashMap<Integer, Zone> zones;

    private Control(){
        init();
    }

    static public Control getInstance(){
        return instance;
    }

    public void init(){
        teams = new HashMap<Integer, Team>();
        zones = new HashMap<Integer, Zone>();
        games = new HashMap<Integer, Game>();

        teams.put(1, new Team(1, 0));
        teams.put(2, new Team(2, 0));
        teams.put(3, new Team(3, 0));
        teams.put(4, new Team(4, 0));

        zones.put(1, new Zone(1, "BatA", 0));
        zones.put(2, new Zone(2, "BatB", 0));
        zones.put(3, new Zone(3, "BatC", 0));
        zones.put(4, new Zone(4, "BatD", 0));

    }


    public void setUser(Player p) { user = p;}
    public Player getUser(){ return user; }

    public void setGames(HashMap<Integer, Game> g){ games=g; }
    public HashMap<Integer, Game> getGames(){ return games; }
    public void addGame(int gameId, Game g) { games.put(gameId, g); }

    public void setCurrentGame(Integer idx){ currentGame=games.get(idx); }
    public Game getCurrentGame(){ return currentGame; }

    public HashMap<Integer, Team> getTeams(){ return teams; }
    public Team getTeamByIdx(int idx){ return teams.get(idx); }

    public HashMap<Integer, Zone> getZones(){ return zones; }
    public Zone getZoneByIdx(int idx){ return zones.get(idx); }
    public void addZone(Zone z) { zones.put(z.getId(), z); }

}
