package com.example.roomdb.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomdb.Notes;
import com.example.roomdb.dao.NotesDao;
import com.example.roomdb.db.NotesDB;

import java.util.List;


public class NotesRepository {
    private NotesDao notesDao;
    private LiveData<List<Notes>> noteList;

    public NotesRepository(Application application ) {
        NotesDB notesDB=NotesDB.getDatabaseInstance(application);
        notesDao = notesDB.notesDao();
        noteList = notesDao.getAll();
    }






    public void insert(Notes notes){new Insert(notesDao).execute(notes);}
    public void delete(Notes notes){new Delete(notesDao).execute(notes);}
    public void update(Notes notes){new Update(notesDao).execute(notes);}
    public LiveData<List<Notes>> getNotes(){
        return noteList;
    }


    public static class Insert extends AsyncTask<Notes, Void,Void> {
        private NotesDao notesDao;

        public Insert(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.insert(notes[0]);
            return null;
        }
    }
    public static class Delete extends AsyncTask<Notes, Void,Void> {
        private NotesDao notesDao;

        public Delete(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.delete(notes[0]);
            return null;
        }
    }
    public static class Update extends AsyncTask<Notes, Void,Void> {
        private NotesDao notesDao;

        public Update(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.update(notes[0]);
            return null;
        }
    }

}
