package com.example.josephrocca.multiviewapptest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


public class MainActivity extends ActionBarActivity {

    public static int SCAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if(Control.getInstance().getUser() != null) {
            // Gestion MENU ---------------------------------------------------
            final ImageView menu1 = (ImageView) findViewById(R.id.menu1);
            final ImageView menu2 = (ImageView) findViewById(R.id.menu2);
            final ImageView menu3 = (ImageView) findViewById(R.id.menu3);
            final ImageView menu4 = (ImageView) findViewById(R.id.menu4);
            Menu mymenu = new Menu(menu1, menu2, menu3, menu4, getFragmentManager());
            mymenu.init();

            // Gestion CAPTUREBTN --------------------------------------------
            final ImageView captureBtn = (ImageView) findViewById(R.id.btncapture);
            BoutonCapture capture = new BoutonCapture(captureBtn);
            capture.init();
            final Intent intentFlash = new Intent(this, ZBarScannerActivity.class);
            capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(intentFlash, SCAN);
                }
            });

            // Gestion TOPINFO ------------------------------------------------
            final TableRow topinforow = (TableRow) findViewById(R.id.topinfo);
            TopInfo topinfo = new TopInfo(topinforow, getLayoutInflater());
            topinfo.initPerso();
            topinfo.initTeam();
        }


        // Si pas connecté, go page de connexion
        else{
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
            if(data!=null) {
                String barcode = data.getStringExtra(ZBarConstants.SCAN_RESULT);
                System.out.println(barcode);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(barcode);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        }
    }

}
