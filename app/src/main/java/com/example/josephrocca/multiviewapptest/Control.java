package com.example.josephrocca.multiviewapptest;


import android.graphics.Color;

import com.example.josephrocca.multiviewapptest.model.Classements;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.model.Team;
import com.example.josephrocca.multiviewapptest.model.Zone;
import com.example.josephrocca.multiviewapptest.model.Menu;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class Control {

    static private Control instance = new Control();


    private Menu menu;
    private Player user;
    private HashMap<Integer, Game> games;
    private Game currentGame;
    private HashMap<Integer, Team> teams;
    private HashMap<Integer, Zone> zones;
    private HashMap<String, Player> players;
    private Classements clst;

    private Control() {
        init();
    }

    public void clear() {
        games.clear();
        players.clear();
        clst = new Classements();
        ServerRequest.fetchGamesList();
    }

    static public Control getInstance() {
        return instance;
    }

    public void init() {
        teams = new HashMap<Integer, Team>();
        zones = new HashMap<Integer, Zone>();
        games = new HashMap<Integer, Game>();
        players = new HashMap<String, Player>();
        clst = new Classements();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setUser(Player p) {
        user = p;
    }

    public Player getUser() {
        return user;
    }

    public void setGames(HashMap<Integer, Game> g) {
        games = g;
    }

    public HashMap<Integer, Game> getGames() {
        return games;
    }

    public boolean isGameAlreadyExist (String name) {
        boolean find = false;
        for(Game game : games.values()) {
            if(game.getName().equals(name))
                find=true;
        }
        return find;
    }

    public ArrayList<Game> getGamesArrayList() {
        return new ArrayList<Game>(games.values());
    }

    public void addGame(int gameId, Game g) {
        games.put(gameId, g);
    }

    public void setCurrentGame(Integer idx) {
        currentGame = games.get(idx);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void addPlayer(Player p) {
        players.put(p.getLogin(), p);
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public Player getPlayerByLogin(String login) {
        return players.get(login);
    }

    public HashMap<Integer, Zone> getZones() {
        return zones;
    }

    public Zone getZoneByIdx(int idx) {
        return zones.get(idx);
    }

    public void addZone(Zone z) {
        zones.put(z.getId(), z);
    }

    public Classements getClst() {
        return clst;
    }


}
