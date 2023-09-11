package com.example.roomdb.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdb.Notes;
import com.example.roomdb.dao.NotesDao;

@Database(entities = {Notes.class},exportSchema = false, version = 1)
public abstract class NotesDB extends RoomDatabase {
    static final String DB_NAME="notes_db";
    static NotesDB instance;
    public abstract NotesDao notesDao();

    public static synchronized NotesDB getDatabaseInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDB.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}