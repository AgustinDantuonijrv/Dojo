package com.example.dojo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login  extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Se envio el email";

    public DatabaseReference users;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    public String usuario;
    public boolean abrirmain = true;
    public boolean validuser = false;
    public  LocalUser user;
    //Declaration EditTexts
    public boolean loged = false;
    EditText editTextEmail;
    public String userapasar;
    public ImageView ImageUserPhoto;
    EditText editTextPassword;
    public String userstring;
    EditText editTextusername;
    EditText editTextdni;
    public String email, password;
    public String Id;
    //Declaration Button
    Button buttonRegister; //Intent to register
    Button buttonlog1;// validate and shared preferences to make then an intent to the main activity
    //ver si modifica si se aplica el public en la declaracion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        buttonRegister.setOnClickListener(this);
        buttonlog1.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        users = FirebaseDatabase.getInstance().getReference("users");

        //usuario = firebaseAuth.getCurrentUser().getEmail();

        String email = editTextEmail.getText().toString();
        String password= editTextPassword.getText().toString();
    }

    public boolean validate() {

        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(Login.this, "Ingrese Un Email Valido!", Toast.LENGTH_SHORT).show();
            abrirmain = false;
        } else {
            if (Email.isEmpty()) {
                valid = false;
                Toast.makeText(Login.this, "El Campo Del Email Está Vacío", Toast.LENGTH_SHORT).show();
            }
            valid = true;
            abrirmain = true;
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(Login.this, "El Campo De La Contraseña Está Vacío!", Toast.LENGTH_SHORT).show();
        } else {
            if (Password.length() > 5 | Password.length() > 31) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(Login.this, "La Contraseña debe tener mas de 5 caracteres y menos de 34!", Toast.LENGTH_SHORT).show();
            }
        }

        return valid;
    }

    private void initViews() {

        ImageUserPhoto = (ImageView) findViewById(R.id.imageUser);
        editTextEmail = (EditText) findViewById(R.id.TxtEmaillog);
        editTextPassword = (EditText) findViewById(R.id.TxtPasswordlog);
        buttonRegister = (Button) findViewById(R.id.toregistera);
        buttonlog1 = findViewById(R.id.loguearme);
    }

    public void toRegister(View view){
        Intent intent = new Intent(view.getContext(), Register.class);
        startActivityForResult(intent, 0);
    }

    private void loguearUsuario() {  // es similar a la anterior pero utiliza el objeto FirebaseAuth para poder permitirle al usuario ingresar sus datos y en caso de que exista el usuario wn la parte de autenticacion le permite ingresar

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        // consigue el usuario
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    // la misma funcion que en el caso anterior pero para poder ingresar
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //el oncomplete es para tener la comprobacion sea del campo del login o el singup

                        if (task.isSuccessful()) {
                            // si el usuario se encontraba creado entonces la tarea es exitosa

                            int pos = email.indexOf("@");
                             userstring = email.substring(0, pos); //consigue el nombre del usuario
                            Toast.makeText(Login.this, "Bienvenido: " + editTextEmail.getText(), Toast.LENGTH_LONG).show(); // consigue el email que habia sido asignado a la variable TextEmail
                            Intent intencion = new Intent(getApplication(), UserView.class); // nueva actividad
                            intencion.putExtra("user", email); // le pasa como dato al usuario ingresado a la proxima actividad
                            startActivity(intencion); //hay que hacer el get del usuario en la parte principal
                           // userapasar = user;
                            loged = true;

                        } else {
                            loged = false;

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisiÛn

                                Toast.makeText(Login.this, "Procesando", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                String userId = firebaseUser.getUid();
                String userEmail = firebaseUser.getEmail();

               SharedPreferences sharedPrefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor5 = sharedPrefs.edit();
                editor5.putString("firebasekey", userId);
                editor5.apply(); // un intento de pasar el usuario nuevo si cmbia el mismo que va a ser usado en futuras versiones de la app
            }
        }
    };

    public void shared() {
        // Intentando agregar el uso de la id

        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();
        String username = userstring;

        SharedPreferences sharedlog = getSharedPreferences("usernamelast" , MODE_PRIVATE);
        SharedPreferences.Editor editorlogin = sharedlog.edit();
        editorlogin.putString("usernamelast", username);
        editorlogin.apply();

        SharedPreferences sharedPref = getSharedPreferences("email", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", Email);
        editor.apply();
        //Los estados los podemos setear en la siguiente actividad
        SharedPreferences sharedPref2 = getSharedPreferences("password", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putString("password", Password);
        editor2.apply();


    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.loguearme:
                    if (validate()) {
                        try {
                            loguearUsuario();
                                if (loged) {
                                   // Toast.makeText(Login.this, "ENTRE A ESTA PARTE", Toast.LENGTH_SHORT).show();
                                    shared();
                                        Intent intentlogmain = new Intent(Login.this, UserView.class);
                                        startActivity(intentlogmain);
                                    }
                                 else {
                                    Toast.makeText(Login.this, "No Se Pudo Loguear", Toast.LENGTH_SHORT).show();
                                }

                           // Intent intent = new Intent(Login.this, UserView.class);
                            //startActivity(intent);

                        } catch (Exception e) {
                            Toast.makeText(Login.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                    // I have to collect all the information from the edit text use the validate function and then make it work using shared preferences to main activity

                    break;
                case R.id.toregistera:
                    toRegister(view);
                    break;
            }
        }catch (Exception e) {
            Toast.makeText(Login.this, "Este es el trycatch del onclick" + e, Toast.LENGTH_SHORT).show();
        }
    }
}
