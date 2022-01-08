package com.example.javaexam;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact")
    LiveData<List<Contact>> getAllLiveData();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact getById(long id);

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
