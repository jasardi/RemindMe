package com.example.remindme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TERMIN_REQUEST = 1;
    public static final int EDIT_TERMIN_REQUEST = 2;

    private NotificationManagerCompat notificationManagerCompat;
    private FloatingActionButton add;
    private TerminViewModel terminViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // replace default action bar with customized toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        // initialize the FloatingActionButton variable
        add = findViewById(R.id.floatingAddButton);
        //check if button is clicked by adding a onClick function on our Button/View
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddActivity();
            }
        });

        RecyclerView RecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager((this)));
        RecyclerView.setHasFixedSize(true);

        final TerminAdapter Adapter = new TerminAdapter();
        RecyclerView.setAdapter(Adapter);

        terminViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(TerminViewModel.class);
        terminViewModel.getAlleTermine().observe(this, new Observer<List<TerminItem>>() {
            //refresh list when database changes
            @Override
            public void onChanged(List<TerminItem> terminItems) {
                Adapter.submitList(terminItems);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                terminViewModel.delete(Adapter.getTerminAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Termin gelöscht", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(RecyclerView);

        Adapter.setOnItemClickListener(new TerminAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TerminItem terminItem) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.EXTRA_ID, terminItem.getId());
                intent.putExtra(AddEditActivity.EXTRA_TERMINNAME, terminItem.getTextTermin());
                intent.putExtra(AddEditActivity.EXTRA_BISZEIT, terminItem.getBisZeit());
                intent.putExtra(AddEditActivity.EXTRA_PRIORITAET, terminItem.getPrioritaet());
                intent.putExtra(AddEditActivity.EXTRA_ALARM, terminItem.getAlarm());
                startActivityForResult(intent, EDIT_TERMIN_REQUEST);
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
    public void OpenAddActivity() {
        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
        startActivityForResult(intent, ADD_TERMIN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TERMIN_REQUEST && resultCode == RESULT_OK){
            String terminName = data.getStringExtra(AddEditActivity.EXTRA_TERMINNAME);
            String bisZeit = data.getStringExtra(AddEditActivity.EXTRA_BISZEIT);
            int prioritaet = data.getIntExtra(AddEditActivity.EXTRA_PRIORITAET, R.drawable.prioritaet_button_green);
            TerminItem terminItem = new TerminItem(prioritaet,terminName,bisZeit);

            String alarm = data.getStringExtra(AddEditActivity.EXTRA_ALARM);
            if(alarm != null){
                terminItem.setAlarm(alarm);
            }

            terminViewModel.insert(terminItem);
            Toast.makeText(this, R.string.termin_gespeichert, Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_TERMIN_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, R.string.fehler_bearbeiten, Toast.LENGTH_SHORT).show();
                return;
            }
            String terminName = data.getStringExtra(AddEditActivity.EXTRA_TERMINNAME);
            String bisZeit = data.getStringExtra(AddEditActivity.EXTRA_BISZEIT);
            int prioritaet = data.getIntExtra(AddEditActivity.EXTRA_PRIORITAET, R.drawable.prioritaet_button_green);
            TerminItem terminItem = new TerminItem(prioritaet,terminName,bisZeit);
            terminItem.setId(id);

            String alarm = data.getStringExtra(AddEditActivity.EXTRA_ALARM);
            if(alarm != null){
                terminItem.setAlarm(alarm);
            }

            terminViewModel.update(terminItem);
            Toast.makeText(this, R.string.termin_aktualisiert, Toast.LENGTH_SHORT).show();
        }
    }
}
