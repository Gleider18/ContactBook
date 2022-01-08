package com.example.javaexam;

import androidx.room.RoomDatabase;

@androidx.room.Database (entities = {Contact.class}, version = 6)
public abstract class Database extends RoomDatabase {
    public abstract ContactDao contactDao();
    

}
