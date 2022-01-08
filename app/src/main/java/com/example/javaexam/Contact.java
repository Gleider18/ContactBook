package com.example.javaexam;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String surname;
    public String name;
    public String phone;

    public Contact(String name, String surname, String phone)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }
}
