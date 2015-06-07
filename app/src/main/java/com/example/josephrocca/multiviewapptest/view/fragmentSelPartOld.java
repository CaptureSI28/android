package com.example.josephrocca.multiviewapptest.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.josephrocca.multiviewapptest.server.AsynkTaskResultsStringListener;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;

import java.util.Collection;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class fragmentSelPartOld extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Game> id_games;
    private ListView partieList;
    ArrayAdapter<Game> arrayAdapter;
    private static SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        // Récupération des éléments graphiques
        View v = inflater.inflate(R.layout.fragment_selectpartold, container, false);
        partieList = (ListView) v.findViewById(R.id.partieList);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout_discussions_fragment);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        // Récupération des données depuis le server
        if (ServerRequest.fetchGamesList()) {
            // Creation de la liste en elle-même
            id_games = new ArrayList<>(Control.getInstance().getGamesArrayList());
            arrayAdapter = new GamesListAdapter(
                    getActivity(), id_games);
            partieList.setAdapter(arrayAdapter);

            // Création du listener
            partieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Game game = Control.getInstance().getGames().get(id_games.get(position).getId());
                    Control.getInstance().setCurrentGame(game.getId());
                    final Intent intentMainActivity = new Intent(getActivity(), JoinGame.class);
                    intentMainActivity.putExtra("GAMEID", game.getId());
                    startActivity(intentMainActivity);
                }
            });
        }
        return v;
    }

    @Override
    public void onRefresh() {
        Control.getInstance().clear();
        mSwipeRefreshLayout.setRefreshing(true);
        if (ServerRequest.fetchGamesList()) {
            mSwipeRefreshLayout.setRefreshing(false);
            arrayAdapter.clear();
            arrayAdapter.addAll(Control.getInstance().getGamesArrayList());
            arrayAdapter.notifyDataSetChanged();
            partieList.setAdapter(arrayAdapter);
            partieList.invalidateViews();
        }
    }
}