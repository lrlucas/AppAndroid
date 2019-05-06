package com.suarez.lucas.platzigram.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.suarez.lucas.platzigram.MainApplicationInitialize;
import com.suarez.lucas.platzigram.R;
import com.suarez.lucas.platzigram.model.Post;
import com.suarez.lucas.platzigram.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewPostFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView ivPicture;
    Button btnPicture;
    String mCurrentPhotoPath;
    String mCurrentAbsolutePhotoPath;
    MainApplicationInitialize app;
    StorageReference storageReference;


    //test

    StorageReference storageReference1;
    DatabaseReference postReference;


    //end test
    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);
        ivPicture = (ImageView) view.findViewById(R.id.ivPicture);
        btnPicture = view.findViewById(R.id.btnTakePicture);

        //test
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference1 = storage.getReferenceFromUrl(Constants.FIREBASE_STORAGE_URL);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // error aqui cuando se quiere persistir la data
//        firebaseDatabase.setPersistenceEnabled(true);
        postReference = firebaseDatabase.getReference(Constants.FIREBASE_DATABASE_LOCATION_POST);
        //endtest

//        app = (MainApplicationInitialize) getActivity().getApplicationContext();
//        storageReference = app.getStorageReference();
        storageReference = storageReference1;

        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {

            Picasso.get().load(mCurrentPhotoPath).into(ivPicture);
            addPictureToGallery();
            Toast.makeText(getActivity(), mCurrentPhotoPath, Toast.LENGTH_LONG).show();

            uploadFile();
        }
    }

    private void uploadFile() {
        File newFile = new File(mCurrentAbsolutePhotoPath);
        Uri contentUri = Uri.fromFile(newFile);

        StorageReference imageReference = storageReference.child(Constants.FIREBASE_STORAGE_IMAGES + contentUri.getLastPathSegment());
        UploadTask uploadTask = imageReference.putFile(contentUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error subiendo la imagen", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // agregado para obtener la url de descarga de la imagen subida
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!urlTask.isSuccessful());
                Uri downloadUrl =urlTask.getResult();

                Toast.makeText(getActivity(), downloadUrl.toString(), Toast.LENGTH_LONG).show();

                String imageUrl = downloadUrl.toString();
                createNewPost(imageUrl);
            }
        });
    }

    private void createNewPost(String imageUrl) {
        SharedPreferences prefs = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");

        String enCodedEmail = email.replace(".",",");


        Post post = new Post(email, imageUrl, (double) new Date().getTime());

        // aqui guardamos la informacion en firebase
        postReference.push().setValue(post);
    }

    //creamos intent para acceder a la camara del telefono
    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.suarez.lucas.platzigram", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }


    private void addPictureToGallery() {
        Intent mediaScanInten = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanInten.setData(contentUri);
        getActivity().sendBroadcast(mediaScanInten);

    }


    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        mCurrentAbsolutePhotoPath = image.getAbsolutePath();
        return image;
    }





}
