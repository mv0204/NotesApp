package com.example.roomdb.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdb.Notes;

import java.util.List;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM my_notes ")
    LiveData<List<Notes>> getAll();



    @Delete
    void delete(Notes notes);
    @Update
    void update(Notes notes);
    @Insert
    void insert(Notes notes);
}