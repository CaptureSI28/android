package com.example.josephrocca.multiviewapptest.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.model.ClassementItem;
import com.example.josephrocca.multiviewapptest.model.Game;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;
import com.example.josephrocca.multiviewapptest.utils.MyColor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by josephrocca on 22/05/15.
 */
public class FragmentClassement extends Fragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_classement, container, false);


        // Gestion du selecteur
        Spinner selecteur = (Spinner) v.findViewById(R.id.classement_spinner);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Classement points");
        spinnerArray.add("Classement flashs");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) v.findViewById(R.id.classement_spinner);
        sItems.setAdapter(adapter);


        RadioGroup teamselector = (RadioGroup) v.findViewById(R.id.classement_teamselector);
        teamselector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.classement_radio_button_blue){
                    System.out.println("debug");
                }
            }
        });


        Control.getInstance().getClst().setClassementPoints(ServerRequest.getClassement("points"));

        ListView partieList = (ListView) v.findViewById(R.id.frag_class_list);

        ClassementListAdapter arrayAdapter = new ClassementListAdapter(
                getActivity(),
                Control.getInstance().getClst().getClassementPoints(0));
        partieList.setAdapter(arrayAdapter);

        return v;
    }

}
