package com.example.dojo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Modifyrecords extends AppCompatActivity {

    public EditText Repeticionesrecords,PesoMaximoRecords;

    public TextView   EjercicioRecords, ObjetivoRecords;

    public String ejerciciostring, repeticionesstring, objetivostring, pesomaximostring, index, NombreCompleto;

    public Button modifybutton;

    private ProgressDialog progressDialog;

    public DatabaseReference Fisico;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyrecords);

        NombreCompleto = getIntent().getStringExtra("nombrecompleto");
        Pojorecords info = getIntent().getParcelableExtra("inforecords");

        progressDialog = new ProgressDialog(Modifyrecords.this);

        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        initviews();

        ejerciciostring = info.getEjercicio();
        repeticionesstring = info.getRepeticiones();
        objetivostring = info.getObjetivo();
        pesomaximostring = info.getPesomaximo();

        index = info.getIndex();

        settext();

        modifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
                Toast.makeText(Modifyrecords.this, "Se ha modificado la información", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //EL USUARIO PUEDE MODIFICAR LAS REPETICIONES Y EL PESO MAXIMO

    public void initviews(){

        EjercicioRecords = (TextView) findViewById(R.id.ejerciciorecords2);
        Repeticionesrecords = (EditText) findViewById(R.id.editrepeticiones2);
        ObjetivoRecords = (TextView) findViewById(R.id.objetivorecords2);
        PesoMaximoRecords = (EditText) findViewById(R.id.editpesomaximo2);
        modifybutton = (Button) findViewById(R.id.recordsbuttons);
    }

    public void settext(){

       EjercicioRecords.setText(ejerciciostring);
       Repeticionesrecords.setText(repeticionesstring);
       ObjetivoRecords.setText(objetivostring);
       PesoMaximoRecords.setText(pesomaximostring);
    }

    public void add() {

        ejerciciostring = EjercicioRecords.getText().toString();
        Fisico.child(NombreCompleto).child("records").child(index).child("ejercicio").setValue(ejerciciostring);

        repeticionesstring = Repeticionesrecords.getText().toString();
        Fisico.child(NombreCompleto).child("records").child(index).child("repeticiones").setValue(repeticionesstring);

        objetivostring = ObjetivoRecords.getText().toString();
        Fisico.child(NombreCompleto).child("records").child(index).child("objetivo").setValue(objetivostring);

        pesomaximostring = PesoMaximoRecords.getText().toString();
        Fisico.child(NombreCompleto).child("records").child(index).child("pesomaximo").setValue(pesomaximostring);

    }

    public void newact() {
        Intent intent = new Intent(Modifyrecords.this, Records.class);
        startActivity(intent);
    }
}
