package com.example.josephrocca.multiviewapptest;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class BoutonCapture {

    ImageView capture;

    public BoutonCapture(ImageView c){
        capture=c;
    }

    public void init(){
        capture.setBackgroundColor(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), false));
        capture.setImageResource(R.drawable.capture_ic_f);
        capture.setColorFilter(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), true));

        capture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    capture.setBackgroundColor(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), false));
                    capture.setColorFilter(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), true));
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    capture.setBackgroundColor(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), true));
                    capture.setColorFilter(MyColor.getTeamColorById(Control.getInstance().getUser().getTeamIdx(), false));
                }
                return false;
            }
        });
    }

    public void setOnClickListener(View.OnClickListener l){
        capture.setOnClickListener(l);
    }

}
