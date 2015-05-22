package com.example.josephrocca.multiviewapptest.model;


public class Player {

    String login;
    int points;
    int teamidx;
    String session_id;

    public Player(){
        login="anonym";
        points=0;
        teamidx=1;
    }

    public Player(String l, int p, int t, String s){
        login=l;
        points=p;
        teamidx=t;
        session_id=s;
    }

    public void setLogin(String log) { login=log; }
    public String getLogin(){ return login; }

    public void setPoints(int pts) { points=pts; }
    public int getPoint(){ return points; }

    public void setTeamIdx(int tidx) { teamidx=tidx; }
    public int getTeamIdx(){ return teamidx; }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

}
