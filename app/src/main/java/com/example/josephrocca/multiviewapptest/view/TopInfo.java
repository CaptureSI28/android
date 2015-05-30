package com.example.josephrocca.multiviewapptest.view;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.Team;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TopInfo {

    TableRow        row;
    LayoutInflater  linf;

    View            persoView;
    ArrayList<View> teamsView;


    public TopInfo(TableRow viewrow, LayoutInflater linflater){

        row     = viewrow;
        linf    = linflater;

    }

    public void init() {
        row.setBackgroundColor(Color.rgb(240, 240, 245));
    }

    public void initPerso(){

        persoView = linf.inflate(R.layout.toppersoinfo, row, false);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        persoView.setLayoutParams(params);

        TextView userpt             = (TextView)persoView.findViewById(R.id.userpt);
        LinearLayout colorlayout    = (LinearLayout)persoView.findViewById(R.id.colorlayperso);

        userpt.setText(Integer.toString(Control.getInstance().getUser().getPoint()));
        userpt.setTextColor(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), true));
        colorlayout.setBackgroundColor(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), false));

        row.addView(persoView);

    }

    public void initTeam(){

        teamsView = new ArrayList<View>();

        HashMap<Integer, Team> team_map = Control.getInstance().getTeams();
        int idxteamplayer = Control.getInstance().getUser().getTeamIdx();
        int nbteam = team_map.size();
        Team t;

        // Team du joueur
        Team teamPlayer = team_map.get((idxteamplayer));



        for(int i=0; i<nbteam; i++){
            t = team_map.get((idxteamplayer+i-1)%nbteam+1);

            View child = linf.inflate(R.layout.topteaminfo, row, false);
            TableRow.LayoutParams params = new TableRow.LayoutParams(1, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            child.setLayoutParams(params);

            TextView teampt             = (TextView)child.findViewById(R.id.ptteam);
            TextView teamnb             = (TextView)child.findViewById(R.id.topteaminfo_nb);
            LinearLayout colorlayout    = (LinearLayout)child.findViewById(R.id.colorlayteam);

            teampt.setText(Integer.toString(t.getNbpts()));
            teampt.setTextColor(MyColor.getTeamColorById(t.getIdx(), true));

            colorlayout.setBackgroundColor(MyColor.getTeamColorById(t.getIdx(), false));

            teamnb.setTextColor(MyColor.getTeamColorById(t.getIdx(), true));

            row.addView(child);

            teamsView.add(child);
        }

    }

    public void update() {
        ServerRequest.getInfosPartie();
        Control.getInstance().getClst().setClassementPoints(ServerRequest.getClassement("points"));
        Control.getInstance().getClst().setClassementFlashs(ServerRequest.getClassement("flashs"));
        updatePerso();
        updateTeams();
    }

    public void updatePerso() {
        TextView userpt             = (TextView)persoView.findViewById(R.id.userpt);
        userpt.setText(Integer.toString(Control.getInstance().getUser().getPoint()));
    }


    public void updateTeams() {
        HashMap<Integer, Team> team_map = Control.getInstance().getTeams();
        int idxteamplayer = Control.getInstance().getUser().getTeamIdx();
        int nbteam = team_map.size();
        Team t;

        for(int i=0; i<nbteam; i++) {
            t = team_map.get((idxteamplayer+i-1)%nbteam+1);

            TextView teampt = (TextView) teamsView.get(i).findViewById(R.id.ptteam);
            teampt.setText(Integer.toString(t.getNbpts()));

            TextView teamnb = (TextView) teamsView.get(i).findViewById(R.id.topteaminfo_nb);
            teamnb.setText(Integer.toString(t.getNbJoueurs()));
        }
    }

}
