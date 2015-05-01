package com.example.josephrocca.multiviewapptest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;

/**
 * Created by josephrocca on 27/04/15.
 */
public class Connexion extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);


        final Button conn_btn = (Button) findViewById(R.id.conn_button);
        final EditText conn_log = (EditText) findViewById(R.id.conn_login);
        final EditText conn_pwd = (EditText) findViewById(R.id.conn_pwd);

        final Intent intentSelection = new Intent(this, SelectionPartie.class);

        conn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Afficher message erreur connexion
                if(ServerRequest.connectCas(conn_log.getText().toString(), conn_pwd.getText().toString())) {
                    Log.d(Connexion.class.getSimpleName(), "Connexion réussie");
                    // TODO Calculer le nb de points du joueurs
                    Control.getInstance().setUser(new Player(conn_log.toString(), 10, 1));
                    finish();
                    startActivity(intentSelection);
                }


            }
        });
    }
}
