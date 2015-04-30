package com.example.josephrocca.multiviewapptest;

/**
 * Created by josephrocca on 27/04/15.
 */
public class Game {

    private int id;
    private String name;
    private String dateDebut;
    private String dateFin;
    private boolean isPrivate;

    public Game(int i, String n, String dd, String df, boolean isp){
        id=i;
        name=n;
        dateDebut=dd;
        dateFin=df;
        isPrivate=isp;
    }

    public Game(){}

    public void setId (int i)               {id=i;}
    public void setName (String n)          {name=n;}
    public void setDateDeb (String dd)       {dateDebut=dd;}
    public void setDateFin (String df)       {dateFin=df;}
    public void setIsPrivate (boolean isp)    {isPrivate=isp;}

    public int getId ()               {return id;}
    public String getName ()          {return name;}
    public String getDateDeb ()       {return dateDebut;}
    public String getShortDateDeb ()  {return dateDebut.substring(0,10);}
    public String getDateFin ()       {return dateFin;}
    public String getShortDateFin ()  {return dateFin.substring(0,10);}
    public boolean getIsPrivate ()    {return isPrivate;}

}
