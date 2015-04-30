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

    public void setLogin(String log) { login=log; }
    public String getLogin(){ return login; }

    public void setPoints(int pts) { points=pts; }
    public int getPoint(){ return points; }

    public void setTeamIdx(int tidx) { teamidx=tidx; }
    public int getTeamIdx(){ return teamidx; }

}
