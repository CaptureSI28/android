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

    private HashMap<String,Integer> players;

    public Game(int i, String n, String dd, String df, boolean isp){
        id=i;
        name=n;
        dateDebut=dd;
        dateFin=df;
        isPrivate=isp;
        players = new HashMap<String,Integer>();
    }

    public Game(){
        players = new HashMap<String,Integer>();
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

    public HashMap<String,Integer> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String,Integer> players) {
        this.players = players;
    }

    public boolean containsCurrentPlayer () {
        return this.players.containsKey(Control.getInstance().getUser().getLogin());
    }

    public void addPlayer (String playerLogin, int t) {
        this.players.put(playerLogin, t);
    }

    public ArrayList<Player> getPlayerList () {
        ArrayList<Player> list = new ArrayList<Player>();
        for(String login : players.keySet()) {
            list.add(Control.getInstance().getPlayerByLogin(login));
        }
        return list;
    }


}
