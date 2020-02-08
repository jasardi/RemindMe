package com.example.remindme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Button PrioritaetButton;
    String[] PrioritaetListe;
    private TextView PrioritaetTextView;
    int checkedItem = 0;
    private Switch AlarmSwitch;
    private Button AlarmButton;
    private StringBuilder ausgewaelteDatum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // Initialize a string array with items from prioritaet_liste in strings.xml
        PrioritaetListe = getResources().getStringArray(R.array.prioritaet_liste);

        PrioritaetTextView = findViewById(R.id.textViewPrioritaet);
        PrioritaetButton = findViewById(R.id.buttonPrioritaet);
        PrioritaetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an alert dialog and give it a title
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddActivity.this);
                mBuilder.setTitle(getString(R.string.title_alertDialog));

                /*
                setSingleChoiceItems accepts 2 arguments:
                1. argument: list of data that will show up in alertdialog
                2. argument: the item that you want to be checked when showing the alertdialog;
                    */
                mBuilder.setSingleChoiceItems(PrioritaetListe, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            PrioritaetButton.setBackgroundResource(R.drawable.prioritaet_button_green);
                            checkedItem = 0;
                            PrioritaetTextView.setText(R.string.niedrig);
                        }
                        else if(i == 1){
                            PrioritaetButton.setBackgroundResource(R.drawable.prioritaet_button_yellow);
                            checkedItem = 1;
                            PrioritaetTextView.setText(R.string.mittel);
                        }
                        else{
                            PrioritaetButton.setBackgroundResource(R.drawable.prioritaet_button_red);
                            checkedItem = 2;
                            PrioritaetTextView.setText(R.string.hoch);
                        }
                        dialogInterface.dismiss();
                    }
                });
                // show alert dialog
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        AlarmButton = findViewById(R.id.buttonAlarm);
        AlarmSwitch = findViewById(R.id.switchAlarm);
        AlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlarmButton.setVisibility(View.VISIBLE);
                }
                else{
                    AlarmButton.setVisibility(View.GONE);
                }
            }
        });
        AlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }


    // Save the date chosen by the user in the AlarmButton text
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ausgewaelteDatum = new StringBuilder(DateFormat.getDateInstance(DateFormat.MEDIUM).format((c.getTime())));


        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ausgewaelteDatum.append(", " + hourOfDay + ":" + minute);
        AlarmButton.setText(ausgewaelteDatum.toString());
    }
}
