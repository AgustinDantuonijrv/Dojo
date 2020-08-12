package com.example.dojo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Modifyrutine extends AppCompatActivity {

    public DatabaseReference Fisico;
    public TextView editdetalles, editrutina;
    public EditText seeobservaciones,ultimoedit;
    public String obs, rut , det, NombreCompleto, ultimostring, observaciones;
    public Button botonconfirmar;
    public boolean bol,bol1;
    public String index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyrutine);
        // Profile usuario = getIntent().getParcelableExtra("usuario");

        Pojofisico datos = getIntent().getParcelableExtra("inforutina");
        NombreCompleto = getIntent().getStringExtra("nombrecompleto");

        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        initviews();

         index = datos.getIndex();
         obs = datos.getObservacionesdeusuario();
         if (obs != null) {
             seeobservaciones.setText(obs);
         }
         rut = datos.getRutinaformato();
         settext(editrutina,rut);
         det = datos.getDiaparte();
         settext(editdetalles,det);
        // Toast.makeText(Modifyrutine.this, "observaciones:" + observacioness, Toast.LENGTH_SHORT).show();

        botonconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
                add();
            }
        });
    }
    public void initviews(){
      editdetalles = (TextView) findViewById(R.id.detalleseditable);
      editrutina = (TextView) findViewById(R.id.rutinaquemuestra);
      seeobservaciones = (EditText) findViewById(R.id.observacionesmirable);
      botonconfirmar = (Button) findViewById(R.id.modificarrutinasadmin);
      ultimoedit = (EditText) findViewById(R.id.ultimopesoedit);
    }

    public void settext(TextView t, String datos){
          t.setText(datos);
    }
    public void getdata(){
        //Observaciones y utlimo peso
         observaciones = seeobservaciones.getText().toString();
        ultimostring = ultimoedit.getText().toString();
    }

    public void add() {
        bol = false;
        bol1 = false;
        //Observaciones y utlimo peso
        Fisico.child(NombreCompleto).child("rutinas2").child(index).child("Observacionesdeusuario").setValue(observaciones).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                bol1 = true;
            }
        });
        Fisico.child(NombreCompleto).child("rutinas2").child(index).child("UltimoPeso").setValue(ultimostring).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                bol = true;
            }
        });
        if (bol1 && bol) {
            Toast.makeText(Modifyrutine.this, "Se ha modificado la información", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Modifyrutine.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
        }
    }
}
