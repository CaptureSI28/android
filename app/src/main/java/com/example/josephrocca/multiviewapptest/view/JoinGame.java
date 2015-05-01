package com.example.josephrocca.multiviewapptest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;

/**
 * Created by josephrocca on 30/04/15.
 */
public class JoinGame extends Activity {

    int gameToJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingame);


        // Recuperation elements graphiques
        final TextView gameinfotxt = (TextView) findViewById(R.id.joingame_info);
        final EditText password = (EditText) findViewById(R.id.joingame_pwd);

        // Recuperation extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String gameid = extras.getString("GAMEID");
            gameToJoin = Integer.parseInt(gameid);
            gameinfotxt.setText(Control.getInstance().getGames().get(gameToJoin-1).getName());
        }

        final Intent intentMainActivity = new Intent(this, MainActivity.class);
        intentMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        // Bouton 'Rejoindre'
        final Button joinButton = (Button) findViewById(R.id.joingame_joinbut);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOk = ServerRequest.joinGame(gameToJoin, password.getText().toString(), getTeamChecked());

                if(isOk){
                    Control.getInstance().setCurrentGame(gameToJoin-1);
                    Control.getInstance().getUser().setTeamIdx(getTeamChecked());
                    finish();
                    startActivity(intentMainActivity);
                }
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