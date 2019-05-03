package com.suarez.lucas.platzigram;


import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.suarez.lucas.platzigram.utils.Constants;


public class MainApplicationInitialize extends Application {

    StorageReference storageReference;
    DatabaseReference postReference;


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl(Constants.FIREBASE_STORAGE_URL);

        FirebaseDatabase  firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        postReference = firebaseDatabase.getReference(Constants.FIREBASE_DATABASE_LOCATION_POST);
    }


    public StorageReference getStorageReference(){
        return storageReference;
    }

    public DatabaseReference getPostReference() {
        return postReference;
    }
}
