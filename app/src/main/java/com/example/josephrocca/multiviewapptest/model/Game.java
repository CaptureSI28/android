package com.example.josephrocca.multiviewapptest.model;

import android.util.Log;

import com.example.josephrocca.multiviewapptest.Control;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private int id;
    private String name;
    private String dateDebut;
    private String dateFin;
    private boolean isPrivate;

    private HashMap<Player,Team> players;

    public Game(int i, String n, String dd, String df, boolean isp){
        id=i;
        name=n;
        dateDebut=dd;
        dateFin=df;
        isPrivate=isp;
        players = new HashMap<Player,Team>();
    }

    public Game(){
        players = new HashMap<Player,Team>();
    }

    public void setId (int i)               {id=i;}
    public void setName (String n)          {name=n;}
    public void setDateDebut (String dd)       {dateDebut=dd;}
    public void setDateFin (String df)       {dateFin=df;}
    public void setPrivate (boolean isp)    {isPrivate=isp;}

    public int getId ()               {return id;}
    public String getName ()          {return name;}
    public String getDateDebut ()       {return dateDebut;}
    public String getShortDateDeb ()  {return dateDebut.substring(0,10);}
    public String getDateFin ()       {return dateFin;}
    public String getShortDateFin ()  {return dateFin.substring(0,10);}
    public boolean getIsPrivate ()    {return isPrivate;}

    public HashMap<Player,Team> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<Player,Team> players) {
        this.players = players;
    }

    public boolean containsCurrentPlayer () {
        Log.d("User",Control.getInstance().getUser().getLogin());
        return this.players.containsKey(Control.getInstance().getUser());
    }

    public void addPlayer (String login, int team_id) {
        this.players.put(new Player(login), Control.getInstance().getTeamByIdx(team_id));
    }

    public void addPlayer (Player p) {
        this.players.put(p, Control.getInstance().getTeamByIdx(p.getTeamIdx()));
    }

}
