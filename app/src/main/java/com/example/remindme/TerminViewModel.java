package com.example.remindme;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TerminViewModel extends AndroidViewModel {
    private TerminRepository repository;
    private LiveData<List<TerminItem>> alleTermine;

    public TerminViewModel(@NonNull Application application) {
        super(application);
        repository = new TerminRepository(application);
        alleTermine = repository.getAlleTermine();
    }

    public void insert(TerminItem terminItem){
        repository.insert(terminItem);
    }

    public void update(TerminItem terminItem){
        repository.update(terminItem);
    }

    public void delete(TerminItem terminItem){
        repository.delete(terminItem);
    }

    public LiveData<List<TerminItem>> getAlleTermine(){
        return alleTermine;
    }
}

