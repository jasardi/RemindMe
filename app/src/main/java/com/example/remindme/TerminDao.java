package com.example.remindme;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TerminDao {

    @Insert
    void insert(TerminItem Termin);

    @Update
    void update(TerminItem Termin);

    @Delete
    void delete(TerminItem Termin);

    //@Query("SELECT * FROM TTermine ORDER BY Prioritaet DESC")
    //LiveData<List<TerminItem>> TermineSortByPrioritaet();

    @Query("SELECT * FROM TTermine ORDER BY BisZeit ASC")
    LiveData<List<TerminItem>> getAlleTermine();

}
