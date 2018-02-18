package com.example.saul.searchapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul.searchapp.R;
import com.example.saul.searchapp.adapters.DateTimeDialog;
import com.example.saul.searchapp.models.QueryClass;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CustomDialogFragment extends DialogFragment {
  //  private EditText mSort;
private Button btOk;
private CheckBox idArts,idFashStyle,idSport;
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
        void onFinishEditDialog(QueryClass _data);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
       // mSort = (EditText) view.findViewById(R.id.txtSort);
        bgDate = (TextView) view.findViewById(R.id.bgDate);
        mSort = (Spinner) view.findViewById(R.id.txtSort);

        idArts = (CheckBox) view.findViewById(R.id.idArts);
        idFashStyle = (CheckBox) view.findViewById(R.id.idFashStyle);
        idSport = (CheckBox) view.findViewById(R.id.idSport);


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
                ArrayList<String> arr=new ArrayList<>();
                if(idArts.isChecked()){
                    arr.add("Arts");
                }
                if(idFashStyle.isChecked()){
                    arr.add("Fashion");
                    arr.add("Style");
                }
                if(idSport.isChecked()){
                    arr.add("Sports");
                }
                String textSearch= String.valueOf(mSort.getSelectedItem());
                QueryClass _queryCl=new QueryClass(bgDate.getText().toString(),textSearch,arr);
                 listener.onFinishEditDialog(_queryCl);
                // Close the dialog and return back to the parent activity
                dismiss();
             //   return true;
            }
        });

        bgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDatePicker();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_dialog, container);
    }

    private void showDatePicker() {
        DateTimeDialog date = new DateTimeDialog();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.setCancelCallBack(oncancel);
        date.show(getActivity().getSupportFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String s = String.format(Locale.US, "%04d%02d%02d",year , monthOfYear+1,dayOfMonth );
            bgDate.setText(s);
        }
    };

    DatePickerDialog.OnCancelListener oncancel = new DatePickerDialog.OnCancelListener(){

        @Override
        public void onCancel(DialogInterface dialogInterface) {
          //  fillRecycle("");
        }
    };
}
