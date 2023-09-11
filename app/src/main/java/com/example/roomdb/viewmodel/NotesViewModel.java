package com.example.roomdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomdb.Notes;
import com.example.roomdb.repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NotesRepository notesRepository;
    private LiveData<List<Notes>> data;
    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository=new NotesRepository(application);
        data=notesRepository.getNotes();
    }

    public void insert(Notes notes){
        notesRepository.insert(notes);
    }

    public void update(Notes notes){
        notesRepository.update(notes);
    }
    public void delete(Notes notes){
        notesRepository.delete(notes);
    }
    public LiveData<List<Notes>> getAllNotes(){
        return data;
    }

}
