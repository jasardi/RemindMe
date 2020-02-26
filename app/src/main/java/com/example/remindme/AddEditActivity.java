package com.example.remindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class AddEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private int clicked = 0;

    private static final int ALARM = 1;
    private static final int DATUM = 2;

    public static final String EXTRA_ID =
            "com.example.remindme.EXTRA_ID";
    public static final String EXTRA_TERMINNAME =
            "com.example.remindme.EXTRA_TERMINNAME";
    public static final String EXTRA_BISZEIT =
            "com.example.remindme.EXTRA_BISZEIT";
    public static final String EXTRA_PRIORITAET =
            "com.example.remindme.EXTRA_PRIORITAET";

    private EditText EditTextTermin;
    private Button PrioritaetButton;
    String[] PrioritaetListe;
    private TextView PrioritaetTextView;
    int checkedItem = 0;
    private Switch AlarmSwitch;
    private Button AlarmButton;
    private Button DatumButton;
    private StringBuilder ausgewaelteDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        // Initialize a string array with items from prioritaet_liste in strings.xml
        PrioritaetListe = getResources().getStringArray(R.array.prioritaet_liste);

        EditTextTermin = findViewById(R.id.editTextTerminName);
        PrioritaetTextView = findViewById(R.id.textViewPrioritaet);
        PrioritaetButton = findViewById(R.id.buttonPrioritaet);
        PrioritaetButton.setBackgroundResource(R.drawable.prioritaet_button_green);
        PrioritaetButton.setTag(R.drawable.prioritaet_button_green);
        PrioritaetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an alert dialog and give it a title
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddEditActivity.this);
                mBuilder.setTitle(getString(R.string.title_alertDialog));

                /*
                setSingleChoiceItems accepts 2 arguments:
                1. argument: list of data that will show up in alertdialog
                2. argument: the item that you want to be checked when showing the alertdialog;
                    */
                mBuilder.setSingleChoiceItems(PrioritaetListe, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            PrioritaetButton.setBackgroundResource(R.drawable.prioritaet_button_green);
                            PrioritaetButton.setTag(R.drawable.prioritaet_button_green);
                            checkedItem = 0;
                            PrioritaetTextView.setText(R.string.niedrig);
                        } else if (i == 1) {
                            PrioritaetButton.setBackgroundResource(R.drawable.prioritaet_button_yellow);
                            PrioritaetButton.setTag(R.drawable.prioritaet_button_yellow);
                            checkedItem = 1;
                            PrioritaetTextView.setText(R.string.mittel);
                        } else {
                            PrioritaetButton.setBackgroundResource(R.drawable.prioritaet_button_red);
                            PrioritaetButton.setTag(R.drawable.prioritaet_button_red);
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
                if (isChecked) {
                    AlarmButton.setVisibility(View.VISIBLE);
                } else {
                    AlarmButton.setVisibility(View.GONE);
                }
            }
        });
        AlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = ALARM;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        DatumButton = findViewById(R.id.buttonDatum);
        DatumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = DATUM;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle(R.string.termin_bearbeiten);
            EditTextTermin.setText(intent.getStringExtra(EXTRA_TERMINNAME));
            DatumButton.setText(intent.getStringExtra(EXTRA_BISZEIT));
            PrioritaetButton.setBackgroundResource(intent.getIntExtra(EXTRA_PRIORITAET, R.drawable.prioritaet_button_green));
            PrioritaetButton.setTag(intent.getIntExtra(EXTRA_PRIORITAET, R.drawable.prioritaet_button_green));
        }
        else{
            setTitle(R.string.termin_hinzufuegen);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_termin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.termin_speichern:
                terminSpeichern();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if (clicked == ALARM){
            AlarmButton.setText(ausgewaelteDatum.toString());
        }
        else if (clicked == DATUM){
            DatumButton.setText(ausgewaelteDatum.toString());
        }
    }

    private void terminSpeichern(){
        String terminName = EditTextTermin.getText().toString();
        String bisZeit = DatumButton.getText().toString();
        int prioritaet = (Integer)PrioritaetButton.getTag();
        Intent data = new Intent();
        data.putExtra(EXTRA_TERMINNAME, terminName);
        data.putExtra(EXTRA_BISZEIT, bisZeit);
        data.putExtra(EXTRA_PRIORITAET, prioritaet);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK,data);
        finish();
    }
}
