package com.example.dojo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;


public class Fragmentovistadeusuario extends Fragment {

    public String userfragment;
    public DatabaseReference users;
    public String user;
    public String uri;
    public String username, nombres,dni,faixa,mimbrodesde,profesor,fechadenacimiento,emaild;
    // las variables que van a almacenar los valores en forma de string para setear los textos

    public Fragmentovistadeusuario() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        users = FirebaseDatabase.getInstance().getReference("users");

        View rootview = inflater.inflate(R.layout.fragment_fragmentovistadeusuarios, container, false);

        Bundle bundle = this.getArguments();
        if (bundle!= null){
            user = bundle.getString("userfragment", "");
            Toast.makeText(getActivity().getApplicationContext(), "username:" + user, Toast.LENGTH_SHORT).show();
        }
        try {
            users.child(user).child("Nombre").addListenerForSingleValueEvent(new ValueEventListener() { //hay que asignarle el valor a nombre dentro del listener
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    nombres = dataSnapshot.getValue(String.class);
                    settextnombre(nombres);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            users.child(user).child("Dni").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dni = dataSnapshot.getValue(String.class);
                    settextdni(dni);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            users.child(user).child("faixa").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    faixa = dataSnapshot.getValue(String.class);
                    settextfaixa(faixa);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            users.child(user).child("Miembrodesde").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mimbrodesde = dataSnapshot.getValue(String.class);
                    miembrodesde(mimbrodesde);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            users.child(user).child("profesor").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    profesor = dataSnapshot.getValue(String.class);
                    settextprofesor(profesor);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            users.child(user).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    emaild = dataSnapshot.getValue(String.class);
                    settextemail(emaild);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            users.child(user).child("Nacimiento").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    fechadenacimiento = dataSnapshot.getValue(String.class);
                    settextnacimiento(fechadenacimiento);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            users.child(user).child("ImagenUri").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    uri = snapshot.getValue(String.class);
                    setimage(uri);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } catch (Exception e) {

            Toast.makeText(getActivity().getApplicationContext(), "exception:" + e , Toast.LENGTH_SHORT).show();
        }

        return rootview;
    }

    public void settextnombre(String nombres) {
            TextView nombre = (TextView) getView().findViewById(R.id.valornombre);
            nombre.setText(nombres);
    }
    public void settextemail (String emails) {

        TextView email = (TextView) getView().findViewById(R.id.valoremail);
        email.setText(emails);
    }
    public  void settextdni (String dni) {
        TextView dnit = (TextView) getView().findViewById(R.id.valordni);
        dnit.setText(dni);
    }
    public void settextfaixa (String faixa) {
        TextView faixat = (TextView) getView().findViewById(R.id.valorfaixa);
        faixat.setText(faixa);
    }
    public void settextnacimiento (String fechadenacimiento) {
        TextView fecha = (TextView) getView().findViewById(R.id.valorfecha);
        fecha.setText(fechadenacimiento);
    }
    public void miembrodesde (String mimbrodesde) {
        TextView miembros = (TextView) getView().findViewById(R.id.valormiembrodesde);
        miembros.setText(mimbrodesde);
    }
    public void settextprofesor (String profesor) {
        TextView profesort = (TextView) getView().findViewById(R.id.valorprofesor);
        profesort.setText(profesor);
    }
    public void setimage (String uri) {
        ImageView userphoto = (ImageView) getView().findViewById(R.id.userphoto);
        if (uri!= null) {
            Toast.makeText(getActivity().getApplicationContext(), "ENTRE NO SOY NULO" + Uri.parse(uri).toString(), Toast.LENGTH_SHORT).show();
           // userphoto.setImageURI(Uri.parse(uri));
            Glide.with(getActivity().getApplicationContext()).load(uri).into(userphoto);
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(), "Imagen Nula", Toast.LENGTH_SHORT).show();
        }
    }
}
