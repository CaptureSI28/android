package com.example.josephrocca.multiviewapptest.view;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    OnDateSetListener ondateSet;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }

    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }
}