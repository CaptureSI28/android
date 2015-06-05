package com.example.josephrocca.multiviewapptest.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private static String spinnerTxt1 = "Classement points";
    private static String spinnerTxt2 = "Classement flashs";
    private ClassementListAdapter arrayAdapter;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_classement, container, false);


        // Gestion du selecteur
        Spinner selecteur = (Spinner) v.findViewById(R.id.classement_spinner);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add(spinnerTxt1);
        spinnerArray.add(spinnerTxt2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) v.findViewById(R.id.classement_spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RadioGroup teamsel = (RadioGroup) getActivity().findViewById(R.id.classement_teamselector);
                int teamidx;
                switch(teamsel.getCheckedRadioButtonId()){
                    case R.id.classement_radio_button_red: teamidx=1;break;
                    case R.id.classement_radio_button_blue: teamidx=2;break;
                    case R.id.classement_radio_button_green: teamidx=3;break;
                    case R.id.classement_radio_button_yellow: teamidx=4;break;
                    default: teamidx=0;
                }

                if(parent.getItemAtPosition(position).toString().equals(spinnerTxt1)){
                    Control.getInstance().getClst().setClassementPoints(ServerRequest.getClassement("points"));
                    arrayAdapter.setList(Control.getInstance().getClst().getClassementPoints(teamidx));
                }
                if(parent.getItemAtPosition(position).toString().equals(spinnerTxt2)){
                    Control.getInstance().getClst().setClassementFlashs(ServerRequest.getClassement("flashs"));
                    arrayAdapter.setList(Control.getInstance().getClst().getClassementFlashs(teamidx));
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        RadioGroup teamselector = (RadioGroup) v.findViewById(R.id.classement_teamselector);
        teamselector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Spinner classSpinner = (Spinner)getActivity().findViewById(R.id.classement_spinner);
                String typClass = classSpinner.getSelectedItem().toString();

                int teamidx;
                switch(checkedId){
                    case R.id.classement_radio_button_red: teamidx=1;break;
                    case R.id.classement_radio_button_blue: teamidx=2;break;
                    case R.id.classement_radio_button_green: teamidx=3;break;
                    case R.id.classement_radio_button_yellow: teamidx=4;break;
                    default: teamidx=0;
                }

                if(typClass.equals(spinnerTxt1))
                    arrayAdapter.setList(Control.getInstance().getClst().getClassementPoints(teamidx));
                if(typClass.equals(spinnerTxt2))
                    arrayAdapter.setList(Control.getInstance().getClst().getClassementFlashs(teamidx));
                arrayAdapter.notifyDataSetChanged();

            }
        });


        Control.getInstance().getClst().setClassementPoints(ServerRequest.getClassement("points"));
        ListView partieList = (ListView) v.findViewById(R.id.frag_class_list);
        arrayAdapter = new ClassementListAdapter(
                getActivity(),
                Control.getInstance().getClst().getClassementPoints(0));
        partieList.setAdapter(arrayAdapter);

        return v;
    }

}
