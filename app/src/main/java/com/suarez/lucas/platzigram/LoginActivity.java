package com.suarez.lucas.platzigram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.suarez.lucas.platzigram.view.ContainerActivity;
import com.suarez.lucas.platzigram.view.CreateAccountActivity;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail;
    TextInputEditText etPassword;
    TextView txtCreateHere;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        txtCreateHere = findViewById(R.id.createHere);
        firebaseAuth = FirebaseAuth.getInstance();

        // remover
        etEmail.setText("lucas1@gmail.com");
        etPassword.setText("123456");

        //subrayado de texto "Crear una aqui"
        txtCreateHere.setPaintFlags(txtCreateHere.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();



                // aqui validamos que los campos del login no sean vacias y tengan espacios
                if(email.length() > 0 && password.length() > 0) {
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        FirebaseUser user = task.getResult().getUser();
                                        SharedPreferences.Editor sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE).edit();
                                        sharedPreferences.putString("email", user.getEmail());
                                        sharedPreferences.commit();
//                                        sharedPreferences.apply();
                                        Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_LONG).show();
                                        goHome();
                                    }
                                }
                            });
                }
                else {

                    Toast.makeText(LoginActivity.this, "Email o Password vacios", Toast.LENGTH_SHORT).show();
                }





            }
        });

    }



    public void goCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }


    public void goHome() {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }
}
