package com.example.josephrocca.multiviewapptest.view;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.josephrocca.multiviewapptest.Control;
import com.example.josephrocca.multiviewapptest.R;
import com.example.josephrocca.multiviewapptest.server.ServerRequest;
import com.example.josephrocca.multiviewapptest.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fragmentSelPartNew extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Récupération des éléments graphiques
        View v = inflater.inflate(R.layout.fragment_selectpartnew, container, false);

        final EditText nomPartie = (EditText) v.findViewById(R.id.frag_select_new_nom);
        final EditText datedeb = (EditText) v.findViewById(R.id.new_game_datedeb);
        final EditText datefin = (EditText) v.findViewById(R.id.new_game_datefin);
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBoxPrivate);
        final EditText password = (EditText) v.findViewById(R.id.frag_select_new_pwd);
        final Button conn_btn = (Button) v.findViewById(R.id.frag_select_new_btn);
        final TextView errorMessage = (TextView) v.findViewById(R.id.errorMessage);

        // Date picker début
        datedeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment picker = new DatePickerFragment();

                OnDateSetListener datePickerListener = new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int selectedYear,
                                          int selectedMonth, int selectedDay) {
                        datedeb.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                                + selectedYear);
                    }
                };

                picker.setCallBack(datePickerListener);
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        // Date picker fin
        datefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment picker = new DatePickerFragment();

                OnDateSetListener datePickerListener = new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int selectedYear,
                                          int selectedMonth, int selectedDay) {
                        datefin.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                                + selectedYear);
                    }
                };

                picker.setCallBack(datePickerListener);
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        // Si partie privée, afficher le mot de passe
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    password.setVisibility(View.VISIBLE);
                else
                    password.setVisibility(View.INVISIBLE);
            }
        });

        // TODO Remonter les messages d'erreur du serveur (date invalide etc.)
        conn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Vérifier que la partie n'existe pas déjà
                if (nomPartie.getText().toString().isEmpty()) {
                    errorMessage.setText("Veuillez entrer un nom de partie.");
                    errorMessage.setVisibility(View.VISIBLE);
                }
                // Vérification debut < fin
                else if (!dateIsValide(datedeb.getText().toString(), datefin.getText().toString())) {
                    errorMessage.setText("La date de début doit être antérieure à la date de fin, et postérieure à aujourd'hui.");
                    errorMessage.setVisibility(View.VISIBLE);
                }
                // Verification MDP si partie privée
                else if (checkBox.isChecked() && password.getText().length() == 0) {
                    errorMessage.setText("Veuillez entrer un mot de passe.");
                    errorMessage.setVisibility(View.VISIBLE);
                }
                // Création de la partie
                else {
                    errorMessage.setText("");
                    if (ServerRequest.createGame(nomPartie.getText().toString(), Util.formatDateFromDataPicker(datedeb.getText().toString()), Util.formatDateFromDataPicker(datefin.getText().toString()), password.getText().toString())) {
                        Log.d("Succès", "Partie créée");
                        // TODO Rediriger vers la page des parties
                        Intent intent = new Intent(getActivity(), JoinGame.class);
                        intent.putExtra("GAMEID", Control.getInstance().getCurrentGame().getId());
                        startActivity(intent);

                    } else {
                        errorMessage.setText("Erreur de création, veuillez réessayer.");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return v;
    }

    private boolean dateIsValide(String dateDebut, String dateFin) {
        SimpleDateFormat formatIn = new SimpleDateFormat("d-M-yyyy");
        boolean result = false;
        try {
            Date debut = formatIn.parse(dateDebut);
            Date fin = formatIn.parse(dateFin);
            if (!fin.before(debut))
                result = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}