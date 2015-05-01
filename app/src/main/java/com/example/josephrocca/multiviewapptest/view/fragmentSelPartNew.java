package com.example.josephrocca.multiviewapptest.view;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.josephrocca.multiviewapptest.R;

import java.util.Calendar;

/**
 * Created by josephrocca on 27/04/15.
 */
public class fragmentSelPartNew extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Récupération des éléments graphiques
        View v = inflater.inflate(R.layout.fragment_selectpartnew, container, false);
        EditText datedeb = (EditText) v.findViewById(R.id.new_game_datedeb);
        EditText datefin = (EditText) v.findViewById(R.id.new_game_datefin);

        return inflater.inflate(
                R.layout.fragment_selectpartnew, container, false);
    }
}