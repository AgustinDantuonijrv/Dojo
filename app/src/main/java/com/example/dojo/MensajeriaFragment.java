package com.example.dojo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MensajeriaFragment extends Fragment {

   public String messeage, clase;
   public DatabaseReference users;
   private FirebaseAuth firebaseAuth;
   public TextView textvmessage;
   public TextView textViewclase;

    public MensajeriaFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.mensajeria, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        users = FirebaseDatabase.getInstance().getReference("users");

        users.child("Mensaje General").child("Mensaje").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              messeage = snapshot.getValue(String.class);
              setTextmesseage(messeage);
              //Here we need to call the function that makes the notification
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        users.child("Mensaje General").child("Grupo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             clase = snapshot.getValue(String.class);
             setTextclass(clase);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      //   View rootview = inflater.inflate(R.layout.mensajeria, container, false);

      return rootview;

    }
    public void setTextmesseage(String messeage) {
        TextView m = (TextView) getView().findViewById(R.id.textViewmensaje);
        m.setText(messeage);
    }
    public void setTextclass(String clase){
        TextView c = (TextView) getView().findViewById(R.id.textViewclasedefragmento);
        c.setText(clase);
    }
}

