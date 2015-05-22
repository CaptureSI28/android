package com.example.josephrocca.multiviewapptest.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.ClassementItem;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by josephrocca on 22/05/15.
 */
public class FragmentClassement extends Fragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_classement, container, false);


        // Gestion du selecteur
        Spinner selecteur = (Spinner) v.findViewById(R.id.classement_spinner);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Points");
        spinnerArray.add("Flashs");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) v.findViewById(R.id.classement_spinner);
        sItems.setAdapter(adapter);




        ServerRequest.getClassement("joueurs");
        /*
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
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    fill_list);
            partieList.setAdapter(arrayAdapter);
        */


        ListView partieList = (ListView) v.findViewById(R.id.frag_class_list);
        HashMap<Integer, ClassementItem> allclassement = new HashMap<Integer, ClassementItem>();

        allclassement.put(0, new ClassementItem(1, "roccajos", 2, 30));
        allclassement.put(1, new ClassementItem(2, "tricioli", 1, 25));

        ClassementListAdapter arrayAdapter = new ClassementListAdapter(
                getActivity(),
                allclassement);
        partieList.setAdapter(arrayAdapter);

        return v;
    }

}
