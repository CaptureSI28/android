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
import com.example.josephrocca.multiviewapptest.model.Flash;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FragmentHistorique extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.fragment_historique, container, false);

        ListView historiqueListe = (ListView) v.findViewById(R.id.historique_liste);

        // Recuperation extras
        Game currentGame = Control.getInstance().getCurrentGame();

        if (currentGame.getFlashs().size() > 0) {

            SimpleFlashListAdapter arrayAdapter = new SimpleFlashListAdapter(
                    getActivity(),
                    new ArrayList<Flash>(currentGame.getFlashs()));
            historiqueListe.setAdapter(arrayAdapter);
        }

        return v;

    }
}
