package com.example.remindme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddActivity extends AppCompatActivity {
    private Button Prioritaet;
    String[] prioritaetListe;
    int checkedItem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // Initialize a string array with items from prioritaet_liste in strings.xml
        prioritaetListe = getResources().getStringArray(R.array.prioritaet_liste);

        Prioritaet = findViewById(R.id.buttonPrioritaet);
        Prioritaet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an alert dialog and give it a title
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddActivity.this);
                mBuilder.setTitle("Wähle die Priorität aus");

                /*
                setSingleChoiceItems accepts 2 arguments:
                1. argument: list of data that will show up in alertdialog
                2. argument: the item that you want to be checked when showing the alertdialog;
                    */
                mBuilder.setSingleChoiceItems(prioritaetListe, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            Prioritaet.setBackgroundResource(R.drawable.prioritaet_button_green);
                            checkedItem = 0;
                        }
                        else if(i == 1){
                            Prioritaet.setBackgroundResource(R.drawable.prioritaet_button_yellow);
                            checkedItem = 1;
                        }
                        else{
                            Prioritaet.setBackgroundResource(R.drawable.prioritaet_button_red);
                            checkedItem = 2;
                        }
                        dialogInterface.dismiss();
                    }
                });
                // show alert dialog
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }
}
