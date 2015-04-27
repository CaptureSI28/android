package com.example.josephrocca.multiviewapptest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by josephrocca on 27/04/15.
 */
public class fragmentSelPartOld extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Game> games = ServerRequest.fetchGamesList();

        View v = inflater.inflate(R.layout.fragment_selectpartold, container, false);
        LinearLayout globalayout = (LinearLayout) v.findViewById(R.id.globalayout);

        return v;
    }
}