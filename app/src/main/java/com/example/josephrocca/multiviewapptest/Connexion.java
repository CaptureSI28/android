package com.example.josephrocca.multiviewapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

                // Test de connexion fictif !
                if(conn_log.getText().toString().equals("Jojo")) {
                    Control.getInstance().setUser(new Player("Jojo", 10, 1));
                    finish();
                    startActivity(intentSelection);
                }
            }
        });
    }
}
