package com.example.remindme;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TerminRepository {
    private TerminDao terminDao;
    private LiveData<List<TerminItem>> alleTermine;

    public TerminRepository(Application application){
        TerminDatabase database = TerminDatabase.getInstance(application);
        terminDao = database.terminDao();
        alleTermine = terminDao.getAlleTermine();
    }

    public void insert(TerminItem terminItem){
        new InsertNoteAsyncTask(terminDao).execute(terminItem);
    }

    public void update(TerminItem terminItem){
        new UpdateNoteAsyncTask(terminDao).execute(terminItem);
    }

    public void delete(TerminItem terminItem){
        new DeleteNoteAsyncTask(terminDao).execute(terminItem);
    }

    public LiveData<List<TerminItem>> getAlleTermine() {
        return alleTermine;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<TerminItem, Void, Void>{
        private TerminDao terminDao;

        private InsertNoteAsyncTask(TerminDao terminDao){
            this.terminDao = terminDao;
        }

        @Override
        protected Void doInBackground(TerminItem... terminItems) {
            terminDao.insert(terminItems[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<TerminItem, Void, Void>{
        private TerminDao terminDao;

        private UpdateNoteAsyncTask(TerminDao terminDao){
            this.terminDao = terminDao;
        }

        @Override
        protected Void doInBackground(TerminItem... terminItems) {
            terminDao.update(terminItems[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<TerminItem, Void, Void>{
        private TerminDao terminDao;

        private DeleteNoteAsyncTask(TerminDao terminDao){
            this.terminDao = terminDao;
        }

        @Override
        protected Void doInBackground(TerminItem... terminItems) {
            terminDao.delete(terminItems[0]);
            return null;
        }
    }
}
