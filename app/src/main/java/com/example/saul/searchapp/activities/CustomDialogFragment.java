package com.example.saul.searchapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul.searchapp.R;
import com.example.saul.searchapp.adapters.DateTimeDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CustomDialogFragment extends DialogFragment {
  //  private EditText mSort;
private Button btOk;
    private Spinner mSort;
  private  TextView bgDate;
    public CustomDialogFragment() {
        // Required empty public constructor
    }

     public static CustomDialogFragment newInstance(String title) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
         fragment.setArguments(args);
       return fragment;
    }
    public interface EditCustomDialogListener {
        void onFinishEditDialog(String inputText);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
       // mSort = (EditText) view.findViewById(R.id.txtSort);
        bgDate = (TextView) view.findViewById(R.id.bgDate);
        mSort = (Spinner) view.findViewById(R.id.txtSort);
        btOk = (Button) view.findViewById(R.id.btOk);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
     //   mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCustomDialogListener listener = (EditCustomDialogListener) getActivity();

                String textSearch= String.valueOf(mSort.getSelectedItem());


                        listener.onFinishEditDialog(textSearch);
                // Close the dialog and return back to the parent activity
                dismiss();
             //   return true;
            }
        });

        bgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DateTimeDialog();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_dialog, container);
    }


}
