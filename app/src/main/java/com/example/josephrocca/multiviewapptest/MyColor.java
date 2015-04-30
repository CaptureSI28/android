package com.example.josephrocca.multiviewapptest;

import android.content.res.Resources;
import android.graphics.Color;

/**
 * Created by josephrocca on 30/04/15.
 */
public class MyColor {

    static int getTeamColorById(int idx, boolean dark){
        switch (idx){
            case 1:
                if(dark)
                    return App.getContext().getResources().getColor(R.color.darkred);
                else
                    return App.getContext().getResources().getColor(R.color.red);
            case 2:
                if(dark)
                    return App.getContext().getResources().getColor(R.color.darkblue);
                else
                    return App.getContext().getResources().getColor(R.color.blue);
            case 3:
                if(dark)
                    return App.getContext().getResources().getColor(R.color.darkgreen);
                else
                    return App.getContext().getResources().getColor(R.color.green);
            case 4:
                if(dark)
                    return App.getContext().getResources().getColor(R.color.darkorange);
                else
                    return App.getContext().getResources().getColor(R.color.orange);
            default:
                if(dark)
                    return App.getContext().getResources().getColor(R.color.darkneutre);
                else
                    return App.getContext().getResources().getColor(R.color.neutre);
        }

    }

}
