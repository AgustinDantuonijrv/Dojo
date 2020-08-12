package com.example.dojo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class  Register extends AppCompatActivity implements View.OnClickListener {

    public boolean abrirmain = true;
    public String estado;

    private static final String TAG = "Se envio el email";

    public DatabaseReference users;

    public Uri filePath;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    //Declaration EditTexts
    EditText editTextEmail; //inputs for users data
    EditText editTextPassword;
    EditText editTextUsername;
    //Declaration Button
    Button buttonRegister;
    Button buttonlog1;
    public boolean usuarioexistente = false;
    static int  PreqCode = 1;
    static int REQUESCODE = 1;

    public String stringuri;

    private FirebaseStorage storage;
    private StorageReference mStorageRef;

    Uri pickedImageUri ;

    public int sino = 0;

    public String usuario;

    public String dwnloaduri;

    public ImageView ImageUserPhoto;


    //Declaration SqliteHelper

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            initViews();

            storage = FirebaseStorage.getInstance();
            mStorageRef = storage.getReference();

            buttonRegister.setOnClickListener(this);
            buttonlog1.setOnClickListener(this);

            ImageUserPhoto.setOnClickListener(this);

            firebaseAuth = FirebaseAuth.getInstance();

            progressDialog = new ProgressDialog(this);

            users = FirebaseDatabase.getInstance().getReference("users");

           // usuario = firebaseAuth.getCurrentUser().getEmail();


        } catch (Exception e) {
            Toast.makeText(Register.this, "e" + e, Toast.LENGTH_SHORT).show();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("firebasekey", "value");
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    private void registrarUsuario() { // este bloque permite agregar a la seccion de autenticacion un usuario por medio de email y clave para
        //poder tener una vista de los usuarios ingresados y que estos se puedan loguear
        // tambien se le envia un mensaje viamail de confirmacion al usuario del mail ingresado

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) { // para que no quede vacio el edit text destinado al ingreso de email
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();

            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseÒa", Toast.LENGTH_LONG).show(); // lo mismo que el aterior pero en el caso de la clave

            return;

        } else {
            progressDialog.setMessage("Realizando registro en linea...");
            progressDialog.show();
            usuarioexistente = false; //probando si asi se puede resetear el valor cada vez que se ingresa un usuario

            //registramos un nuevo usuario
            firebaseAuth.createUserWithEmailAndPassword(email, password) // usamos las variables creadas para poder tener los datos de los edit text y creamos el usuario
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                final FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseUser user = auth.getCurrentUser(); // conseguimos el suario ingresado
                                assert user != null; // si no es nula la variable a la que se le asigna el usuario
                                user.sendEmailVerification() // se le envia un mail de verificacion que fue editado en la pagina de firebase
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "Email sent."); // se notifica con un onComplete al igual que en el caso anterior que seria e caso de que se haya podido realizar la tarea
                                                }
                                            }
                                        });
                                Toast.makeText(Register.this, "Se ha registrado el usuario con el email: " + editTextEmail.getText(), Toast.LENGTH_LONG).show();
                                    }
                            else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(Register.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                                    usuarioexistente = true;
                                } else {
                                    Toast.makeText(Register.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(Register.this, "Ingrese Un Email Valido!", Toast.LENGTH_SHORT).show();
        } else {
            if (Email.isEmpty()) {
                valid = false;
                Toast.makeText(Register.this, "El Campo Del Email Está Vacío", Toast.LENGTH_SHORT).show();
            }
            valid = true;
            estado = "Estado Valido";
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(Register.this, "El Campo De La Contraseña Está Vacío!", Toast.LENGTH_SHORT).show();
        } else {
            if (Password.length() > 5 | Password.length() > 31) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(Register.this, "La Contraseña debe tener mas de 5 caracteres y menos de 34!", Toast.LENGTH_SHORT).show();
            }
        }

        return valid;
    }

    private void checkAndRequestForPermission(View view){

        //TODO: open gallery and wait for user to pick an image !
        if (ContextCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(Register.this, "Por Favor Acepte el requerimiento para poder agregar una foto", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PreqCode);
            }
        } else {
            openGallery();
        }
    }
    private void openGallery(){

        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            //se eligio exitosamente una imagen
            //guardamos referencia en uri

            pickedImageUri = data.getData();
            stringuri = pickedImageUri.toString();
           // ImageUserPhoto.setImageURI(pickedImageUri);

             filePath = data.getData();
            //se puede probar tambien

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore
                                       .Images
                                       .Media
                                       .getBitmap(
                                           getContentResolver(),
                                           filePath);
                ImageUserPhoto.setImageBitmap(bitmap);
                uploadimage();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //using bitmap and call the method to upload the image to storage

            if (stringuri.contains("com.google")){
                progressDialog.setMessage("Si la imagen no pertenece a su galeria puede generar mal funcionamiento");
            }
        }
    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.TxtEmail);
        editTextPassword = (EditText) findViewById(R.id.TxtPassword);
        buttonRegister = (Button) findViewById(R.id.botonregister);
        buttonlog1 = findViewById(R.id.botonlog1);
        ImageUserPhoto = findViewById(R.id.imageuser);
    }
    public void toLogIn(View view) {
            Intent intent = new Intent(view.getContext(), Login.class);
            startActivityForResult(intent, 0);
    }

    private void uploadimage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Subiendo la imagen seleccionada");
        String randomkey = UUID.randomUUID().toString();
        final StorageReference riversRef = mStorageRef.child("images/" + randomkey);

        riversRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //getdownoalesur();
                         riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                               String email = editTextEmail.getText().toString();
                               //get the substring
                                int pos = email.indexOf("@");
                                String username = email.substring(0,pos);
                                Toast.makeText(Register.this, "nombredeusuarioquesubelafoto:" + username,Toast.LENGTH_SHORT).show();
                               stringuri = uri.toString();
                                users.child(username).child("ImagenUrideregister").setValue(stringuri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                                Snackbar.make(findViewById(android.R.id.content),"Se Cargo su información de perfil correctamente", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            String message = task.getException().toString();
                                            Toast.makeText(getApplicationContext(),"Error:"+ message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                       pd.dismiss();
                        Toast.makeText(Register.this, "No se pudo subir la imagen", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                      double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                      pd.setMessage("Porcentaje" + (int) progressPercent + "%");
            }
        });

    }
    
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.botonregister:
            registrarUsuario();
                if (validate()) {
                    try {

                        String email = editTextEmail  .getText().toString();
                        String password = editTextPassword.getText().toString();

                        // We need to share the image url not the uri and see if with that approach we get the data display with the picasso library

                        // we can change te value of the string in here

                        SharedPreferences shareduri = getSharedPreferences("photouri", MODE_PRIVATE);
                        SharedPreferences.Editor editoruri = shareduri.edit();
                        editoruri.putString("photouri", stringuri); // here we will put the downloaduri
                        editoruri.apply();


                        SharedPreferences sharedPref = getSharedPreferences("email", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.apply();
                        //Los estados los podemos setear en la siguiente actividad
                        SharedPreferences sharedPref2 = getSharedPreferences("password", MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPref2.edit();
                        editor2.putString("password", password);
                        editor2.apply();


                        if (!usuarioexistente) {
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Register.this, "Si ya posee una cuenta puede proceder a loguearse", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Register.this, "ERRORacá:" + e, Toast.LENGTH_SHORT).show();
                    }
                } break;

            case R.id.botonlog1:
                    toLogIn(view);
                 break;
            case R.id.imageuser:
                checkAndRequestForPermission(view);
                break;
        }
    }
}