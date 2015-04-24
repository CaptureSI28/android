package com.example.josephrocca.multiviewapptest;


public class Player {

    String login;
    int points;
    int teamidx;

    public Player(){
        login="anonym";
        points=0;
        teamidx=1;
    }

    public Player(String l, int p, int t){
        login=l;
        points=p;
        teamidx=t;
    }

    public String getLogin(){ return login; }
    public int getPoint(){ return points; }
    public int getTeamIdx(){ return teamidx; }
}
