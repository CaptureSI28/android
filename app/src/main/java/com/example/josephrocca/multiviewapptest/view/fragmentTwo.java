package com.example.josephrocca.multiviewapptest.view;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.josephrocca.multiviewapptest.R;

public class fragmentTwo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.fragment_two, container, false);
    }
}
