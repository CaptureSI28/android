package com.example.josephrocca.multiviewapptest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Gestion MENU ---------------------------------------------------
        final ImageView menu1 = (ImageView)findViewById(R.id.menu1);
        final ImageView menu2 = (ImageView)findViewById(R.id.menu2);
        final ImageView menu3 = (ImageView)findViewById(R.id.menu3);
        final ImageView menu4 = (ImageView)findViewById(R.id.menu4);
        Menu mymenu = new Menu(menu1, menu2, menu3, menu4, getFragmentManager());
        mymenu.init();


        // Gestion CAPTUREBTN --------------------------------------------
        final ImageView captureBtn = (ImageView)findViewById(R.id.btncapture);
        BoutonCapture capture = new BoutonCapture(captureBtn);
        capture.init();


        // Gestion TOPINFO ------------------------------------------------
        final TableRow topinforow = (TableRow)findViewById(R.id.topinfo);
        TopInfo topinfo = new TopInfo(topinforow, getLayoutInflater());
        topinfo.initPerso();
        topinfo.initTeam();


    }

}
