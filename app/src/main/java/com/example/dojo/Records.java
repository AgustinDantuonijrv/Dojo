package com.example.dojo;

import android.content.Intent;
import android.os.Bundle;
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

public class Records extends AppCompatActivity implements AdapterRecyclerRecords.OnItemListener {

    public String NombreCompleto;
    public DatabaseReference Fisico;
    public RecyclerView recyclerView;
    ArrayList<Pojorecords> list;
    AdapterRecyclerRecords adapterrecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);

        NombreCompleto = getIntent().getStringExtra("fullname");
        Toast.makeText(Records.this, NombreCompleto, Toast.LENGTH_SHORT).show();

        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        initviews();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Pojorecords>();

        Fisico.child(NombreCompleto).child("records").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pojorecords per = dataSnapshot.getValue(Pojorecords.class);

                    Toast.makeText(Records.this, "Entre acá", Toast.LENGTH_SHORT).show();
                    list.add(per);
                }
                initRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Records.this, "Oppps algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initRecycler() {

            adapterrecycler = new AdapterRecyclerRecords(this, list , this);
            recyclerView.setAdapter(adapterrecycler);
            adapterrecycler.notifyDataSetChanged();
    }

    public void initviews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerrecords);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Records.this, Modifyrecords.class);

        intent.putExtra("inforecords", list.get(position));
        intent.putExtra("nombrecompleto", NombreCompleto);

        startActivity(intent);
    }
}
