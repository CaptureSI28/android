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
        int teamId = Control.getInstance().getCurrentGame().getTeamIdCurrentUser();
        capture.setBackgroundColor(MyColor.getTeamColorById(teamId, false));
        capture.setImageResource(R.drawable.logocapture80);
        capture.setColorFilter(MyColor.getTeamColorById(teamId, true));

        capture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int teamId = Control.getInstance().getCurrentGame().getTeamIdCurrentUser();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    capture.setBackgroundColor(MyColor.getTeamColorById(teamId, false));
                    capture.setColorFilter(MyColor.getTeamColorById(teamId, true));
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    capture.setBackgroundColor(MyColor.getTeamColorById(teamId, true));
                    capture.setColorFilter(MyColor.getTeamColorById(teamId, false));
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
