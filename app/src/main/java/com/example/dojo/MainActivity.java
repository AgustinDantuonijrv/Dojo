package com.example.dojo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button Aregistrar;
    public String email, password, DNI, Fecha, nombre, username, miembrodesde,profesor;
    public Spinner SpinnActividades, SpinnBelts;
    public EditText EdDNI, EdFecha, Ednombre, EdMiembrodesde, EdProfesor;
    public CheckBox jujitsubox, judobox, yogabox;
    public Button alog;
    public String faixa ;
    public boolean add = false;
    public String sumastring;
    public String ch1e,ch2e,ch3e, Faixa;
    public RequestQueue requestQueue;
    public Boolean permisojujitsu,permisojudo,permisoyoga;
    public Boolean servidorbug = false;
    public String userapasar, uri;
    public int contadorusers;
    public Integer contadorastring;
    public DatabaseReference users, usernames;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //despues agregamos el toast con un mensaje invitando a llenar el formulario
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        users = FirebaseDatabase.getInstance().getReference("users");
        usernames = FirebaseDatabase.getInstance().getReference("usernames");

        SharedPreferences shareduri = getSharedPreferences("photouri", MODE_PRIVATE);
        uri = shareduri.getString("photouri", "");

        Toast.makeText(MainActivity.this, "uri: " + uri, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPref = getSharedPreferences("email", MODE_PRIVATE);
        email = sharedPref.getString("email", "");
        Toast.makeText(MainActivity.this, "Estamos consiguiendo el email" + email, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences1 = getSharedPreferences("password", MODE_PRIVATE);
        password = sharedPreferences1.getString("password", "");


        miembrodesde = EdMiembrodesde.getText().toString();
        profesor = EdProfesor.getText().toString();

        permisojujitsu = false;
        permisojudo = false;
        permisoyoga = false;

        Aregistrar.setOnClickListener(this);
        alog.setOnClickListener(this);

        int pos = email.indexOf("@");
        userapasar = email.substring(0, pos);
        Toast.makeText(MainActivity.this, "userpasar:" + userapasar, Toast.LENGTH_SHORT).show();

        usernames.child("contadorusuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contadorusers = snapshot.getValue(Integer.class);
                Toast.makeText(MainActivity.this, "valorcontadorusers:" + contadorusers, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initViews() {
        Aregistrar = (Button) findViewById(R.id.botonaregistrar2);
        alog = (Button) findViewById(R.id.botonalog);
        EdFecha = (EditText) findViewById(R.id.editTextfecha);
        EdDNI = (EditText) findViewById(R.id.editTextdni);
        EdMiembrodesde = findViewById(R.id.editTextmiembro);
        EdProfesor = findViewById(R.id.editTextProfesor);
        Ednombre = (EditText) findViewById(R.id.EditTextNombre);
        SpinnBelts = findViewById(R.id.spinnbelts);
    }

    private void Add() { //to create a user with this values

    //usernames
        usernames.child("contadorusuarios").setValue(contadorusers); // esta mandando siempre uno como si fuera una constqnte
        usernames.child("usuario" + contadorusers).child("identificadorusuario").setValue(nombre + "/" + userapasar);

    //users
    users.child(userapasar).child("Dni").setValue(DNI);
    users.child(userapasar).child("Miembrodesde").setValue(miembrodesde);
    users.child(userapasar).child("Nacimiento").setValue(Fecha);
    users.child(userapasar).child("Nombre").setValue(nombre);
    users.child(userapasar).child("email").setValue(email);
    users.child(userapasar).child("faixa").setValue(faixa);
    users.child(userapasar).child("permisojiujitsu").setValue(false);
    users.child(userapasar).child("permisojudo").setValue(false);
    users.child(userapasar).child("permisoyoga").setValue(false);
    users.child(userapasar).child("profesor").setValue(profesor);
    users.child(userapasar).child("Mensaje").setValue("Acá se muestran los comentarios de parte de tu entrenador con respecto a tu desempeño");

    }

    public  Boolean validate() {
        //Here qe ar going to validate the inputs before sending to the server
        boolean valid = false;
        String DNI = EdDNI.getText().toString();
        String Fecha = EdFecha.getText().toString();
        String nombre = Ednombre.getText().toString();

        String miembro = EdMiembrodesde.getText().toString();
        String profesor = EdProfesor.getText().toString();

        if (DNI.isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        if (Fecha.isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        if (nombre.isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        if (miembro.isEmpty()){
            valid = false;
        } else {
            valid = true;
        }
        return  valid;
    }
    @Override
    public void onClick(View view) {
        try{
        switch (view.getId()) {
            case R.id.botonaregistrar2:

                if (validate()) {

                    nombre = Ednombre.getText().toString();
                    DNI = EdDNI.getText().toString();
                    Fecha = EdFecha.getText().toString();
                    miembrodesde = EdMiembrodesde.getText().toString();
                    profesor = EdProfesor.getText().toString();

                    faixa = SpinnBelts.getSelectedItem().toString();

                    contadorusers = contadorusers + 1;
                    Add();
                        Toast.makeText(MainActivity.this, "El usuario se creo correctamente, proceda a loguearse", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Falta Indormación", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.botonalog:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
        }
        }catch (Exception e){
            Toast.makeText(MainActivity.this,"ERROR:" + e, Toast.LENGTH_LONG).show();
        }
    }
}
