package com.example.dojo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PagoJudoFragment extends Fragment implements AdapterVideos.OnItemListener {

    public DatabaseReference Videos;
    public RecyclerView recyclerView;
    ArrayList<Pojovideos> list;
    Adaptervideosjudo adapterVideos;

    public PagoJudoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_pago_judo, container, false);

        Videos = FirebaseDatabase.getInstance().getReference("Videos");
        recyclerView = rootview.findViewById(R.id.recyclerdevideos);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<Pojovideos>();

        Videos.child("Judo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Pojovideos p = dataSnapshot.getValue(Pojovideos.class);
                    list.add(p);
                }
                initrecycler();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  rootview;
    }

    private void initrecycler(){

        adapterVideos = new Adaptervideosjudo(getActivity(),list,this);
        recyclerView.setAdapter(adapterVideos);
        adapterVideos.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity().getApplicationContext(), Reproductor.class);
        intent.putExtra("video", list.get(position));
    }
}
