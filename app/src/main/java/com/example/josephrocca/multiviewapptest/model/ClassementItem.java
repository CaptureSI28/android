package com.example.josephrocca.multiviewapptest.model;

/**
 * Created by josephrocca on 22/05/15.
 */
public class ClassementItem {

    private int indice;
    private String name;
    private int score;

    public ClassementItem(int i, String n, int s){
        indice = i;
        name = n;
        score = s;
    }

    public int getIndice(){ return indice; }
    public String getName(){ return name; }
    public int getScore(){ return score; }

    public void getIndice(int i){ indice=i; }
    public void getName(String n){ name=n; }
    public void getScore(int s){ score=s; }

}
