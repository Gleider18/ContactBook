package com.example.javaexam;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ContactViewModel extends ViewModel {
    public void updateContact(Contact contact, Context context) {
        new Thread(() -> SingletonDb.getDbInstance(context).db.contactDao().update(contact)).start();
    }

    public void deleteContact(Contact contact, Context context) {
        new Thread(() -> SingletonDb.getDbInstance(context).db.contactDao().delete(contact)).start();
    }

    public LiveData<List<Contact>> getAllContact(Context context) {
        return SingletonDb.getDbInstance(context).db.contactDao().getAllLiveData();
    }

    public void insertContact(Contact contact, Context context) {
        new Thread(() -> SingletonDb.getDbInstance(context).db.contactDao().insert(contact)).start();
    }
}
