package com.example.dojo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Fisico extends AppCompatActivity implements Adapterrecyclerrutinas.OnItemListener {

    public String NombreCompleto;
    public TextView displaynombre;
    public String pesoactual;
    public String pesoobjetivostring;
    public String diaspinner;
    public RecyclerView recyclerView;
    ArrayList<Pojofisico> list;
    Adapterrecyclerrutinas adapterrecycler;
    public DatabaseReference Fisico;
    public Button verrecords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisico);

        NombreCompleto = getIntent().getStringExtra("fullname");

        Toast.makeText(Fisico.this, NombreCompleto,Toast.LENGTH_SHORT).show();

        //ESTA CLASE CORRESPONDE A LAS RUTINAS Y ES LA QUE ANDA

        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        initviews();

        // displaynombre.setText(NombreCompleto);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Pojofisico>();
        //We get the data from the userweights and then see the spinner thing

        Fisico.child(NombreCompleto).child("rutinas2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //taking care of the photo uri parse
                    Pojofisico p = dataSnapshot.getValue(Pojofisico.class);
                    //puede que haya que hacer un poco mas para especificar bien que key pertenece a cada variable del objeto profile
                    list.add(p); //creamos un objeto de tipo perfil que espera todos los parametros string y lo seteamos al adapter
                }
                initRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void initRecycler() {
        if (list != null) {
            adapterrecycler = new Adapterrecyclerrutinas(this, list,this);
            recyclerView.setAdapter(adapterrecycler);
            adapterrecycler.notifyDataSetChanged();
        }
    }

    private void initviews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerRutinas);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(Fisico.this, Modifyrutine.class);

        intent.putExtra("inforutina", list.get(position));
        intent.putExtra("nombrecompleto", NombreCompleto);

      startActivity(intent);
    }
}
