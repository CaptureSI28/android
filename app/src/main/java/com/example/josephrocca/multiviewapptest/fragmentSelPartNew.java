package com.example.josephrocca.multiviewapptest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

        datefin.setOnFocusChangeListener((View.OnFocusChangeListener) getActivity());

        return inflater.inflate(
                R.layout.fragment_selectpartnew, container, false);
    }

}