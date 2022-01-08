package com.example.javaexam;

import android.content.Context;

import androidx.room.Room;

public final class SingletonDb {
    private static SingletonDb instance;
    private Context context;
    public Database db;

    private SingletonDb(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context, Database.class, "database.db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static SingletonDb getDbInstance(Context context) {
        if (instance == null) {
            instance = new SingletonDb(context);
        }
        return instance;
    }
}
