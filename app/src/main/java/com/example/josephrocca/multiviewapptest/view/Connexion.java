package com.example.josephrocca.multiviewapptest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.model.Player;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;


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

                if (ServerRequest.connectCas(conn_log.getText().toString(), conn_pwd.getText().toString(), getApplicationContext())) {
                    Log.d(Connexion.class.getSimpleName(), "Connexion réussie");
                    finish();
                    startActivity(intentSelection);
                } else {
                    Toast.makeText(getApplicationContext(), v.getResources().getString(R.string.connexion_error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
