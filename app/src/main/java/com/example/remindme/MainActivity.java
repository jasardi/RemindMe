package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton add;
    private ArrayList<TerminItem> TermineList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // replace default action bar with customized toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the FloatingActionButton variable
        add = findViewById(R.id.floatingAddButton);
        //check if button is clicked by adding a onClick function on our Button/View
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddActivity();
            }
        });
    }

    // override onCreateOptionsMenu() to specify the options menu for an activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu resource (convert the XML resource into a programmable object) using inflater.inflate()
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Method to open the Add activity/screen
    public void OpenAddActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}
