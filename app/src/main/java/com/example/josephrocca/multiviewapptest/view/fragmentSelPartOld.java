package com.example.josephrocca.multiviewapptest.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by josephrocca on 27/04/15.
 */
public class fragmentSelPartOld extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Récupération des données depuis le server
        ArrayList<Game> games = ServerRequest.fetchGamesList();
        Control.getInstance().setGames(games);

        // Récupération des éléments graphiques
        View v = inflater.inflate(R.layout.fragment_selectpartold, container, false);
        ListView partieList = (ListView) v.findViewById(R.id.partieList);


        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatOut = new SimpleDateFormat("d MMMM yyyy", Locale.FRENCH);

        // Création de la liste de remplissage
        List<String> fill_list = new ArrayList<String>();
        for(Game g : games){
            String content=g.getName()+"\n";
            try {
                Date debut = formatIn.parse(g.getShortDateDeb());
                content = content.concat("Du " + formatOut.format(debut) + " ");
                Log.d("debut", formatOut.format(debut));
                Date fin = formatIn.parse(g.getShortDateFin());
                content = content.concat("au " + formatOut.format(fin));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            content = content.concat(g.getIsPrivate()?"\nPartie privée":"\nPartie publique");
            fill_list.add(content);
        }

        if(fill_list.size()==0) {
            fill_list.add("Pas de partie en cours.");
        }

        // Creation de la liste en elle-même
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                fill_list);
        partieList.setAdapter(arrayAdapter);

        // Création du listener
        partieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), JoinGame.class);
                intent.putExtra("GAMEID", String.valueOf(position+1));
                startActivity(intent);
            }
        });

        return v;
    }
}