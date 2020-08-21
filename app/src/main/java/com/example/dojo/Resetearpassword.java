package com.example.dojo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Resetearpassword extends AppCompatActivity {

    private static final String TAG = "Se envio el email";


    public EditText get_email;
    public String email;
    public Button sendemail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetear);
        initviews();
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //we have to get the email snd the verification and then redirect to the log screnn with an oncmpletion listener
                email = get_email.getText().toString();
                if (!get_email.getText().toString().equals("")) {
                    verify(email);
                } else {
                    Toast.makeText(Resetearpassword.this, "Debe ingresar un email valido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initviews(){
        get_email = (EditText) findViewById(R.id.emailget);
        sendemail = (Button) findViewById(R.id.sendemail);
    }
    private void verify(final String email){

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email Enviado a :   " + email);
                            Toast.makeText(Resetearpassword.this, "Email enviado a: " + email, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "No se pudo enviar el email");
                            Toast.makeText(Resetearpassword.this, "No se pudo enviar el email", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
