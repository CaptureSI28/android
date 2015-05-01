package com.example.josephrocca.multiviewapptest.model;


import android.graphics.Color;

public class Team {

    int colfonce;
    int colclaire;
    int nbpts;
    int idx;

    public Team(int c1, int c2, int i, int pt){
        colfonce = c1;
        colclaire = c2;
        idx = i;
        nbpts = pt;
    }

    public int getColorFonce(){ return colfonce; }
    public int getColorClaire(){ return colclaire; }
    public int getPoints(){ return nbpts; }
    public int getIdx(){ return idx; }

}
