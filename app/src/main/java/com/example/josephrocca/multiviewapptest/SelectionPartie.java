package com.example.josephrocca.multiviewapptest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by josephrocca on 27/04/15.
 */
public class SelectionPartie extends Activity implements View.OnFocusChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectpartie_global);

        final TextView selectold = (TextView) findViewById(R.id.selectpartold);
        final TextView selectnew = (TextView) findViewById(R.id.selectpartnew);


        selectold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.selectioncontent, new fragmentSelPartOld());
                fragmentTransaction.commit();

                final FrameLayout fm1 = (FrameLayout) findViewById(R.id.selectparties1);
                fm1.setBackgroundColor(Color.rgb(123,135,170));
                final FrameLayout fm2 = (FrameLayout) findViewById(R.id.selectparties2);
                fm2.setBackgroundColor(Color.rgb(255,255,255));
            }
        });

        selectnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.selectioncontent, new fragmentSelPartNew());
                fragmentTransaction.commit();

                final FrameLayout fm1 = (FrameLayout) findViewById(R.id.selectparties2);
                fm1.setBackgroundColor(Color.rgb(123,135,170));
                final FrameLayout fm2 = (FrameLayout) findViewById(R.id.selectparties1);
                fm2.setBackgroundColor(Color.rgb(255,255,255));
            }
        });

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.selectioncontent, new fragmentSelPartOld());
        fragmentTransaction.commit();

        final FrameLayout fm1 = (FrameLayout) findViewById(R.id.selectparties1);
        fm1.setBackgroundColor(Color.rgb(123,135,170));
        final FrameLayout fm2 = (FrameLayout) findViewById(R.id.selectparties2);
        fm2.setBackgroundColor(Color.rgb(255,255,255));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch(v.getId()){
            case R.id.new_game_datefin: System.out.println("%%%%%%%%%%%%%%%%%%%%%%");
        }
    }
}