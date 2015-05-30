package com.example.josephrocca.multiviewapptest.model;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.example.josephrocca.multiviewapptest.App;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.view.FragmentClassement;
import com.example.josephrocca.multiviewapptest.view.FragmentHistorique;
import com.example.josephrocca.multiviewapptest.view.FragmentMap;
import com.example.josephrocca.multiviewapptest.view.TopInfo;
import com.example.josephrocca.multiviewapptest.view.FragmentSettings;

import java.util.Arrays;
import java.util.List;

public class Menu {

    ImageView m1;
    ImageView m2;
    ImageView m3;
    ImageView m4;
    FragmentManager fm;
    TopInfo topinfo;

    public Menu(ImageView menu1, ImageView menu2, ImageView menu3, ImageView menu4, FragmentManager f, TopInfo ti){
        m1 = menu1;
        m2 = menu2;
        m3 = menu3;
        m4 = menu4;
        fm=f;
        topinfo=ti;
    }

    public void init(){

        m1.setImageResource(R.drawable.menuicmap2);
        m2.setImageResource(R.drawable.menuiclast);
        m3.setImageResource(R.drawable.menuicclassement);
        m4.setImageResource(R.drawable.menuicreglage);

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(1);
                updateMenu(m1, Arrays.asList(m2, m3, m4));
            }
        });

        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(2);
                updateMenu(m2, Arrays.asList(m1, m3, m4));
            }
        });

        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(3);
                updateMenu(m3, Arrays.asList(m2, m1, m4));
            }
        });

        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(4);
                updateMenu(m4, Arrays.asList(m2, m3, m1));
            }
        });

        updateMenu(m1, Arrays.asList(m2, m3, m4));
        selectFrag(1);

    }

    private void updateMenu(ImageView mIn, List<ImageView> mOut){
        mIn.setBackgroundColor(App.getContext().getResources().getColor(R.color.darkgray));
        mIn.setColorFilter(App.getContext().getResources().getColor(R.color.lightgray));
        for(ImageView iv : mOut){
            iv.setBackgroundColor(App.getContext().getResources().getColor(R.color.lightgray));
            iv.setColorFilter(App.getContext().getResources().getColor(R.color.darkgray));
        }
        topinfo.update();
    }

    public void selectFrag(int frag) {
        Fragment fr;

        switch(frag){
            case 1 : fr = new FragmentMap();break;
            case 2 : fr = new FragmentHistorique();break;
            case 3 : fr = new FragmentClassement();break;
            case 4 : fr = new FragmentSettings();break;
            default: fr = new FragmentMap();break;
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.globalcontent, fr);
        fragmentTransaction.commit();
    }

}
