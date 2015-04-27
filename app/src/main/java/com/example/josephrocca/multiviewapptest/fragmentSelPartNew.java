package com.example.josephrocca.multiviewapptest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by josephrocca on 27/04/15.
 */
public class fragmentSelPartNew extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_selectpartnew, container, false);
    }
}