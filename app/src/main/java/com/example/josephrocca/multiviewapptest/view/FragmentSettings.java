package com.example.josephrocca.multiviewapptest.view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.utils.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FragmentSettings extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.fragment_settings, container, false);

        TextView gamename = (TextView) v.findViewById(R.id.settings_nom_partie);
        TextView gamecrea = (TextView) v.findViewById(R.id.settings_createur);
        TextView gamedatedeb = (TextView) v.findViewById(R.id.settings_date_debut);
        TextView gamedatefin = (TextView) v.findViewById(R.id.settings_date_fin);
        ListView partieList = (ListView) v.findViewById(R.id.settings_listparticipants);
        final Button quitButton = (Button) v.findViewById(R.id.settings_button_quit);

        // Recuperation extras
        Game currentGame = Control.getInstance().getCurrentGame();
        gamename.setText(currentGame.getName());
        gamecrea.setText("Créée par " + currentGame.getCreator());
        gamedatedeb.setText("Du " + currentGame.getDateDebut());
        gamedatefin.setText("au " + currentGame.getDateFin());

        SimplePlayerListAdapter arrayAdapter = new SimplePlayerListAdapter(
                getActivity(),
                new ArrayList<Player>(currentGame.getPlayerList()));
        partieList.setAdapter(arrayAdapter);

        final Intent intentConn = new Intent(getActivity(), SelectionPartie.class);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentConn);
            }
        });

        return v;

    }
}
