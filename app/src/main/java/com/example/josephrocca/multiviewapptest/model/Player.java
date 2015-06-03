package com.example.josephrocca.multiviewapptest.model;


import com.example.josephrocca.multiviewapptest.Control;

import java.util.HashMap;

public class Player {

    String login;
    int points;
    String session_id;

    public Player(String l) {
        login = l;
        points = 0;
        session_id = "";
    }

    public Player(String l, int p, int t, String s) {
        login = l;
        points = p;
        session_id = s;
    }

    public void setLogin(String log) {
        login = log;
    }

    public String getLogin() {
        return login;
    }

    public void setPoints(int pts) {
        points = pts;
    }

    public int getPoint() {
        return points;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }


}
