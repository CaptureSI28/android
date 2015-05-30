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

    private HashMap<String,Integer> players;


    private ArrayList<Flash> flashs;

    public Game(int i, String n, String c, Date dd, Date df, boolean isp){
        id=i;
        name=n;
        creator=c;
        dateDebut=dd;
        dateFin=df;
        isPrivate=isp;
        players = new HashMap<String,Integer>();
        flashs = new ArrayList<Flash>();
    }

    public Game(){
        players = new HashMap<String,Integer>();

        flashs = new ArrayList<Flash>();
    }

    public void setId (int i)               {id=i;}
    public void setName (String n)          {name=n;}
    public void setDateDebut (Date dd)       {dateDebut=dd;}
    public void setDateFin (Date df)       {dateFin=df;}
    public void setPrivate (boolean isp)    {isPrivate=isp;}

    public int getId ()               {return id;}
    public String getName ()          {return name;}
    public String getDateDebut ()       {return Util.getStringFromDate(dateDebut);}
    public String getDateFin ()       {return Util.getStringFromDate(dateFin);}
    public boolean getIsPrivate ()    {return isPrivate;}


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

    public void addFlash (Flash f) {
        flashs.add(f);
    }

}
