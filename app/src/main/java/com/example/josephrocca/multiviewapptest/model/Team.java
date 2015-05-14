package com.example.josephrocca.multiviewapptest.model;


import android.graphics.Color;

public class Team {

    int nbpts;
    int idx;
    int nbJoueurs;

    public Team(int i, int pt){
        idx = i;
        nbpts = pt;
    }


    public int getNbpts() {
        return nbpts;
    }

    public void setNbpts(int nbpts) {
        this.nbpts = nbpts;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }
}
