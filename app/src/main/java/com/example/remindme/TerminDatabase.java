package com.example.remindme;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TerminItem.class, version = 1)
public abstract class TerminDatabase extends RoomDatabase {

    private static TerminDatabase instance;

    public abstract TerminDao terminDao();

    public  static synchronized TerminDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TerminDatabase.class, "termin_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
