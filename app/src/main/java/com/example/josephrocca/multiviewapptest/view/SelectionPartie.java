package com.example.josephrocca.multiviewapptest.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.view.fragmentSelPartNew;
import com.example.josephrocca.multiviewapptest.view.fragmentSelPartOld;

/**
 * Created by josephrocca on 27/04/15.
 */
public class SelectionPartie extends Activity {

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
}