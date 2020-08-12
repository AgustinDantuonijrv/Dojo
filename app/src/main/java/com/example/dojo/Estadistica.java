package com.example.dojo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Estadistica extends AppCompatActivity implements View.OnClickListener {

    public Button records, rutinas;
    public TextView pesoobj;
    public EditText pesoact;

    public DatabaseReference Fisico;
    public String NombreCompleto;
    public String pesoactual, pesoobjtstring;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        initViews();

        NombreCompleto = getIntent().getStringExtra("usernameforfisico");
        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        rutinas.setOnClickListener(this);
        records.setOnClickListener(this);

        Fisico.child(NombreCompleto).child("pesoactual").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pesoactual = snapshot.getValue(String.class);
                settextact();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Fisico.child(NombreCompleto).child("pesoobjetivo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pesoobjtstring = snapshot.getValue(String.class);
                settextobj();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void settextact () {
        pesoact.setText(pesoactual);
    }
    public void settextobj () {
       pesoobj.setText(pesoobjtstring);
    }

    public void initViews() {
        records = (Button) findViewById(R.id.botonrecords);
        rutinas = (Button) findViewById(R.id.botonrutinas);
        pesoobj = (TextView) findViewById(R.id.valorobjetivo);
        pesoact = (EditText) findViewById(R.id.textopesoactual);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botonrecords:
                String modifyact = pesoact.getText().toString();
                Fisico.child(NombreCompleto).child("pesoactual").setValue(modifyact);

                Intent intent = new Intent(Estadistica.this, Records.class);
                intent.putExtra("fullname", NombreCompleto);
                startActivity(intent);

                break;
            case R.id.botonrutinas:
                String modifyact1 = pesoact.getText().toString();
                Fisico.child(NombreCompleto).child("pesoactual").setValue(modifyact1);
                Intent intent1 = new Intent(Estadistica.this,Fisico.class);
                intent1.putExtra("fullname", NombreCompleto);
                startActivity(intent1);
        }
    }
}
