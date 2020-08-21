package com.example.dojo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class OlduserviewFragment extends Fragment {
    public RequestQueue requestQueue;
    public TextView Mensajeprincipal;
    public int dnientero;
    public String mensajesetear;
    public DatabaseReference users;
    public String userhome, profe, DNI ; //hardcodeamos por ahora para probar si podemos hacer que consiga distintos datos si hay distintos emails
    public String user; //this is the email and it is going to be use to get the specific the messeage for the user in cuestion


    public OlduserviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        users = FirebaseDatabase.getInstance().getReference("users");

//A better aproach is to pass the user and get the messeage in here

        View rootview = inflater.inflate(R.layout.fragment_olduserview, container, false);

        Bundle bundlehome = this.getArguments();
        if (bundlehome!= null){

            userhome = bundlehome.getString("userhome", "");
          //  Toast.makeText(getActivity().getApplicationContext(), "user:" + userhome, Toast.LENGTH_SHORT).show();

            users.child(userhome).child("Mensaje").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     mensajesetear = snapshot.getValue(String.class);
             //       Toast.makeText(getActivity().getApplicationContext(), userhome, Toast.LENGTH_SHORT).show();
                    settext(mensajesetear);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
           // settext(userhome);

        } else {

         //   Toast.makeText(getActivity().getApplicationContext(), "Null Bundle", Toast.LENGTH_SHORT).show();
        }

        return rootview;
    }
    public void settext(String mensaje){
        TextView mensajeprincipal = getActivity().findViewById(R.id.textodelhome2);
        mensajeprincipal.setText(mensaje);
    }



}
