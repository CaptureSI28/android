package com.example.josephrocca.multiviewapptest.view;


import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.utils.MyColor;
import com.example.josephrocca.multiviewapptest.R;

public class BoutonCapture {

    ImageView capture;

    public BoutonCapture(ImageView c){
        capture=c;
    }

    public void init(final TopInfo topInfo){
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
                topInfo.update();
                return false;
            }
        });
    }

    public void setOnClickListener(View.OnClickListener l){
        capture.setOnClickListener(l);
    }

}
