package com.example.josephrocca.multiviewapptest.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Date;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.model.Menu;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.Zone;
import com.example.josephrocca.multiviewapptest.utils.Util;
import com.example.josephrocca.multiviewapptest.utils.ZBarConstants;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;


public class MainActivity extends ActionBarActivity {

    public static int SCAN = 1;
    private TopInfo topinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if (Control.getInstance().getUser() != null) {
            ServerRequest.fetchZonesList();
            // Gestion TOPINFO ------------------------------------------------
            final TableRow topinforow = (TableRow) findViewById(R.id.topinfo);
            topinfo = new TopInfo(topinforow, getLayoutInflater());
            topinfo.initPerso();
            topinfo.initTeam();

            // Gestion MENU ---------------------------------------------------
            final ImageView menu1 = (ImageView) findViewById(R.id.menu1);
            final ImageView menu2 = (ImageView) findViewById(R.id.menu2);
            final ImageView menu3 = (ImageView) findViewById(R.id.menu3);
            final ImageView menu4 = (ImageView) findViewById(R.id.menu4);
            Menu mymenu = new Menu(menu1, menu2, menu3, menu4, getFragmentManager(), topinfo);
            Control.getInstance().setMenu(mymenu);
            mymenu.init();
            topinfo.update();

            // Gestion CAPTUREBTN --------------------------------------------
            final ImageView captureBtn = (ImageView) findViewById(R.id.btncapture);
            BoutonCapture capture = new BoutonCapture(captureBtn);
            capture.init(topinfo);
            final Intent intentFlash = new Intent(this, ZBarScannerActivity.class);

            if (Util.getDateFromShortString(Control.getInstance().getCurrentGame().getDateFin()).before(new Date())) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.game_end), Toast.LENGTH_LONG).show();
            } else {
                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(intentFlash, SCAN);
                    }
                });
            }


        }


        // Si pas connecté, go page de connexion
        else {
            final Intent intentConn = new Intent(this, Connexion.class);
            finish();
            startActivity(intentConn);
        }

    }


    // Retour d'activités diverses...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Si retour d'un flash...
        if (requestCode == SCAN) {
            if (data != null) {

                String barcode = data.getStringExtra(ZBarConstants.SCAN_RESULT);

                MediaPlayer player = MediaPlayer.create(this, R.raw.son);

                String response = ServerRequest.flash(barcode);
                if (response.contains("fail")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.flash_error), Toast.LENGTH_LONG).show();
                    MediaPlayer playerError = MediaPlayer.create(this, R.raw.son);
                } else if (response.contains("bonus")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.bonus).setTitle(R.string.bonus_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                    player.start();
                } else {
                    player.start();
                }

                topinfo.update();
            }
        }
    }

}
