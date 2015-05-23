package com.example.josephrocca.multiviewapptest.model;

import java.util.HashMap;

/**
 * Created by josephrocca on 23/05/15.
 */
public class Classements {

    HashMap<Integer, ClassementItem> classementPoints;
    HashMap<Integer, ClassementItem> classementFlashs;

    public Classements(){
        classementPoints = new HashMap<Integer, ClassementItem>();
        classementFlashs = new HashMap<Integer, ClassementItem>();
    }



    public void setClassementPoints(HashMap<Integer, ClassementItem> cp){
        classementPoints=cp;
    }
    public HashMap<Integer, ClassementItem> getClassementPoints(int teamidx){
        if(teamidx==0)
            return classementPoints;
        else{
            return filtreByTeam(classementPoints, teamidx);
        }
    }



    public void setClassementFlashs(HashMap<Integer, ClassementItem> cf){
        classementFlashs=cf;
    }
    public HashMap<Integer, ClassementItem> getClassementFlashs(int teamidx){
        if(teamidx==0)
            return classementFlashs;
        else{
            return filtreByTeam(classementFlashs, teamidx);
        }
    }



    private HashMap<Integer, ClassementItem> filtreByTeam(HashMap<Integer, ClassementItem> tf, int teamidx){
        int idx = 1;
        HashMap<Integer, ClassementItem> toreturn = new HashMap<Integer, ClassementItem>();
        for(ClassementItem ci : tf.values()){
            if(ci.getTeam()==teamidx){
                toreturn.put(idx, new ClassementItem(idx, ci.getName(), ci.getTeam(), ci.getScore()));
                idx++;
            }
        }
        return toreturn;
    }
}
