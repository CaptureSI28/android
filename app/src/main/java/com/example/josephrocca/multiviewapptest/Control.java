package com.example.josephrocca.multiviewapptest;


import android.graphics.Color;

import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.model.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class Control {

    static private Control instance = new Control();

    private Player user;
    private ArrayList<Game> games;
    private Game currentGame;

    private HashMap<Integer, Team> teams;

    private Control(){
        init();
    }

    static public Control getInstance(){
        return instance;
    }

    public void init(){
        teams = new HashMap<Integer, Team>();
        games = new ArrayList<Game>();

        // TODO Pourquoi ne pas utiliser les couleurs de MyColor ? Pour avoir les mêmes ?
        teams.put(1, new Team(Color.rgb(0, 110, 160), Color.rgb(120, 190, 225), 1, 200));
        teams.put(2, new Team(Color.rgb(130, 110, 0), Color.rgb(255, 225, 70), 2, 300));
        teams.put(3, new Team(Color.rgb(120, 35, 15), Color.rgb(235, 95, 65), 3, 250));
        teams.put(4, new Team(Color.rgb(0, 95, 40), Color.rgb(185, 195, 0), 4, 500));
    }

    public void setUser(Player p) { user = p;}
    public Player getUser(){ return user; }

    public void setGames(ArrayList<Game> g){ games=g; }
    public ArrayList<Game> getGames(){ return games; }

    public void setCurrentGame(Integer idx){ currentGame=games.get(idx); }
    public Game getCurrentGame(){ return currentGame; }

    public HashMap<Integer, Team> getTeams(){ return teams; }
    public Team getTeamByIdx(int idx){ return teams.get(idx); }

}
