package com.example.saul.searchapp.adapters;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saul.searchapp.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SAUL on 2/17/2018.
 */

public class DateTimeDialog extends DialogFragment{
    DatePickerDialog.OnDateSetListener ondateSet;
    DatePickerDialog.OnCancelListener onCancel;
    private int year, month, day;

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    public void setCancelCallBack(DatePickerDialog.OnCancelListener oncancel){
        onCancel = oncancel;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog =  new DatePickerDialog(
                getActivity(),
                //Reso,
                ondateSet,
                year,
                month,
                day);

        return datePickerDialog;
    }


}
