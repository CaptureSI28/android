package com.example.josephrocca.multiviewapptest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;

import java.util.ArrayList;

/**
 * Created by josephrocca on 30/04/15.
 */
public class JoinGame extends Activity {

    int idgameToJoin;
    Game gameToJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingame);


        // Recuperation elements graphiques
        final EditText password = (EditText) findViewById(R.id.joingame_pwd);

        TextView gamename = (TextView) findViewById(R.id.joingame_namegame);
        TextView gamecrea = (TextView) findViewById(R.id.joingame_crea);
        TextView gamedatedeb = (TextView) findViewById(R.id.joingame_datedeb);
        TextView gamedatefin = (TextView) findViewById(R.id.joingame_datefin);
        ListView partieList = (ListView) findViewById(R.id.joingame_listparticipant);

        // Recuperation extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idgameToJoin = extras.getInt("GAMEID");
            gameToJoin = Control.getInstance().getGames().get(idgameToJoin);
            gamename.setText(gameToJoin.getName());
            gamecrea.setText("");
            gamedatedeb.setText("du "+gameToJoin.getDateDebut());
            gamedatefin.setText("au "+gameToJoin.getDateFin());

            SimplePlayerListAdapter arrayAdapter = new SimplePlayerListAdapter(
                    this,
                    new ArrayList<Player>(gameToJoin.getPlayerList()));
            partieList.setAdapter(arrayAdapter);
        }


        final Intent intentMainActivity = new Intent(this, MainActivity.class);
        intentMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        // Bouton 'Rejoindre'
        final Button joinButton = (Button) findViewById(R.id.joingame_joinbut);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RB checked=", String.valueOf(getTeamChecked()));
                boolean isOk = ServerRequest.joinGame(idgameToJoin, password.getText().toString(), getTeamChecked());

                if(isOk){
                    Control.getInstance().setCurrentGame(idgameToJoin -1);
                    Control.getInstance().getUser().setTeamIdx(getTeamChecked());
                    gameToJoin.addPlayer(Control.getInstance().getUser().getLogin(), getTeamChecked());
                    finish();
                    startActivity(intentMainActivity);
                }
            }
        });

        // Bouton 'Annuler'
        final Button cancelButton = (Button) findViewById(R.id.joingame_cancel);
        final Intent intentConn = new Intent(this,SelectionPartie.class);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentConn);
            }
        });

    }

    private int getTeamChecked(){
        final RadioButton RB_red = (RadioButton) findViewById(R.id.joingame_radio_button_red);
        final RadioButton RB_blue = (RadioButton) findViewById(R.id.joingame_radio_button_yellow_blue);
        final RadioButton RB_green = (RadioButton) findViewById(R.id.joingame_radio_button_green);
        final RadioButton RB_yellow = (RadioButton) findViewById(R.id.joingame_radio_button_yellow_yellow);

        if(RB_red.isChecked())
            return 1;
        if(RB_blue.isChecked())
            return 2;
        if(RB_green.isChecked())
            return 3;
        if(RB_yellow.isChecked())
            return 4;
        return -1;
    }

}