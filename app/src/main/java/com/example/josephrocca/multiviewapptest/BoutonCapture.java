package com.example.josephrocca.multiviewapptest;


import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BoutonCapture {

    ImageView capture;

    public BoutonCapture(ImageView c){
        capture=c;
    }

    public void init(){
        capture.setBackgroundColor(Control.getInstance().getTeamByIdx(Control.getInstance().getUser().getTeamIdx()).getColorClaire());
        capture.setImageResource(R.drawable.capture_ic_f);
        capture.setColorFilter(Control.getInstance().getTeamByIdx(Control.getInstance().getUser().getTeamIdx()).getColorFonce());

        capture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    capture.setBackgroundColor(Control.getInstance().getTeamByIdx(Control.getInstance().getUser().getTeamIdx()).getColorClaire());
                    capture.setColorFilter(Control.getInstance().getTeamByIdx(Control.getInstance().getUser().getTeamIdx()).getColorFonce());
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    capture.setBackgroundColor(Control.getInstance().getTeamByIdx(Control.getInstance().getUser().getTeamIdx()).getColorFonce());
                    capture.setColorFilter(Control.getInstance().getTeamByIdx(Control.getInstance().getUser().getTeamIdx()).getColorClaire());
                }
                return false;
            }
        });
    }

}
