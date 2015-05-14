package com.example.josephrocca.multiviewapptest.model;


import android.graphics.Color;

public class Team {

    int colfonce;
    int colclaire;
    int nbpts;
    int idx;
    int nbJoueurs;

    public Team(int c1, int c2, int i, int pt){
        colfonce = c1;
        colclaire = c2;
        idx = i;
        nbpts = pt;
    }

    public int getColfonce() {
        return colfonce;
    }

    public void setColfonce(int colfonce) {
        this.colfonce = colfonce;
    }

    public int getColclaire() {
        return colclaire;
    }

    public void setColclaire(int colclaire) {
        this.colclaire = colclaire;
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
