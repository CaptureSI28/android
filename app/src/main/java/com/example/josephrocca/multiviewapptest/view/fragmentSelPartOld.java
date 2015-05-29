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

import java.util.Collection;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by josephrocca on 27/04/15.
 */
public class fragmentSelPartOld extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        // Récupération des éléments graphiques
        View v = inflater.inflate(R.layout.fragment_selectpartold, container, false);
        ListView partieList = (ListView) v.findViewById(R.id.partieList);

        // Récupération des données depuis le server
        if (ServerRequest.fetchGamesList()) {
            Collection<Game> games = Control.getInstance().getGames().values();
            SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatOut = new SimpleDateFormat("d MMMM yyyy", Locale.FRENCH);

            // Création de la liste de remplissage
            List<String> fill_list = new ArrayList<String>();
            final HashMap<Integer,Integer> id_games = new HashMap<Integer, Integer>();
            int i=0;
            for (Game g : games) {
                String content = g.getName() + "\n";
                try {
                    Date debut = formatIn.parse(g.getShortDateDeb());
                    content = content.concat("Du " + formatOut.format(debut) + " ");
                    Log.d("debut", formatOut.format(debut));
                    Date fin = formatIn.parse(g.getShortDateFin());
                    content = content.concat("au " + formatOut.format(fin));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                content = content.concat(g.getIsPrivate() ? "\nPartie privée" : "\nPartie publique");
                fill_list.add(content);
                id_games.put(i,g.getId());
                i++;
            }

            if (fill_list.size() == 0) {
                fill_list.add("Pas de partie en cours.");
            }

            // Creation de la liste en elle-même
            ArrayAdapter<Game> arrayAdapter = new GamesListAdapter(
                    getActivity(),
                    new ArrayList<Game>(Control.getInstance().getGames().values()));
            partieList.setAdapter(arrayAdapter);

            // Création du listener
            partieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Game game = Control.getInstance().getGames().get(id_games.get(position));
                    Log.d("Liste joueur partie",game.getName()+" - "+game.getPlayers().toString());
                    if(!game.containsCurrentPlayer()) {
                        final Intent intentMainActivity = new Intent(getActivity(), JoinGame.class);
                        intentMainActivity.putExtra("GAMEID", game.getId());
                        startActivity(intentMainActivity);
                    } else {
                        final Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("GAMEID", game.getId());
                        Control.getInstance().setCurrentGame(game.getId()-1);
                        int teamId = game.getPlayers().get(Control.getInstance().getUser().getLogin());
                        Control.getInstance().getUser().setTeamIdx(teamId);
                        startActivity(intent);
                    }
                }
            });
        }
        return v;
    }
}