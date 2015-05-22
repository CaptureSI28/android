package com.example.josephrocca.multiviewapptest.model;

/**
 * Created by josephrocca on 22/05/15.
 */
public class ClassementItem {

    private int indice;
    private String name;
    private int team;
    private int score;

    public ClassementItem(int i, String n, int t, int s){
        indice = i;
        name = n;
        team = t;
        score = s;
    }

    public int getIndice(){ return indice; }
    public String getName(){ return name; }
    public int getTeam(){ return team; }
    public int getScore(){ return score; }

    public void setIndice(int i){ indice=i; }
    public void setName(String n){ name=n; }
    public void setTeam(int t){ team=t; }
    public void setScore(int s){ score=s; }

}
