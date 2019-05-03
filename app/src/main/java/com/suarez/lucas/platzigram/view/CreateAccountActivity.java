package com.suarez.lucas.platzigram.view;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.suarez.lucas.platzigram.LoginActivity;
import com.suarez.lucas.platzigram.R;

public class CreateAccountActivity extends AppCompatActivity {
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    TextInputEditText etName;
    TextInputEditText etUser;
    TextInputEditText etConfirmPassword;
    Button  btnCreateAccount;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_title_create_account), true);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.email);
        etName = findViewById(R.id.name);
        etUser = findViewById(R.id.user);
        etPassword = findViewById(R.id.password_create_account);
        etConfirmPassword = findViewById(R.id.confirm_password);
        btnCreateAccount = findViewById(R.id.joinUs);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String user = etUser.getText().toString().trim();
                String username = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmation = etConfirmPassword.getText().toString().trim();

                // aqui validamos que el email no sea incorrecto
                // lo validamos con exprecion regular
                if(!isEmailValid(email)) {
                    Toast.makeText(getApplicationContext(), "Email incorrecto", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(password.length() > 0 && confirmation.length() > 0) {
                    if(password.equals(confirmation)) {
                        // aqui mandamos el email y password a firebase para la autenticacion
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(CreateAccountActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(CreateAccountActivity.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    }
                                });

                    }
                    else {
                        showToast("Las contraseñas deben ser iguales");
                    }
                }
                else {
                    showToast("Las contraseñas no pueden ser vacias");
                }
            }
        });



    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    public void showToolbar(String title, boolean upButton) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }

    private void showToast(String msg) {
        Toast.makeText(CreateAccountActivity.this,msg,Toast.LENGTH_LONG).show();
    }


}
