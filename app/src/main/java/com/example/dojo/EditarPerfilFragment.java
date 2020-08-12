package com.example.dojo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditarPerfilFragment extends Fragment  {
    public EditText profesor;
    public DatabaseReference users;
    public int pos;
    public String user, nombreinstructor;
    public String username, nombres, dnis, faixa, mimbrodesde, profesoredit, fechadenacimiento, emaild;

    public EditarPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        users = FirebaseDatabase.getInstance().getReference("users");
        Bundle bundleedit = this.getArguments();
        if (bundleedit != null) {
            user = bundleedit.getString("useredit", "");
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

            users.child(user).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    emaild = dataSnapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            users.child(user).child("Dni").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dnis = dataSnapshot.getValue(String.class);
                    settextdni(dnis);
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
                    profesoredit = dataSnapshot.getValue(String.class);
                    settextprofesor(profesoredit);
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

        } catch (Exception e) {

            Toast.makeText(getActivity().getApplicationContext(), "exception:" + e, Toast.LENGTH_SHORT).show();
        }


        View rootview = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        Button Editar = (Button) rootview.findViewById(R.id.editarButton);
        final EditText nombre = (EditText) rootview.findViewById(R.id.EditTnombre);
        final EditText dni = (EditText) rootview.findViewById(R.id.editTextdni);
        final Spinner faixaspinner = (Spinner) rootview.findViewById(R.id.spinnnerbelts);
        final EditText miembroedit = (EditText) rootview.findViewById(R.id.editTmiembro);
        final EditText profesoredirt = (EditText) rootview.findViewById(R.id.editTprofesor);
        final EditText nacimientoedit = (EditText) rootview.findViewById(R.id.editTextfecha);


        Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //WE GET ALL THE INFO AND THEN CALL ADD() FUNCTION
                nombres = nombre.getText().toString();
                dnis = dni.getText().toString();
                faixa = faixaspinner.getSelectedItem().toString();
                mimbrodesde = miembroedit.getText().toString();
                profesoredit = profesoredirt.getText().toString();
                fechadenacimiento = nacimientoedit.getText().toString();

                add();
            }
        });

        return rootview;
    }


    public void settextnombre(String nombres) {
        EditText nombre = (EditText) getView().findViewById(R.id.EditTnombre);
        nombre.setText(nombres);
    }

    public void settextdni(String dni) {
        EditText dnit = (EditText) getView().findViewById(R.id.editTextdni);
        dnit.setText(dni);
    }

    public void settextfaixa(String faixa) {
        Spinner faixat = (Spinner) getView().findViewById(R.id.spinnnerbelts);

        if (faixa.contains("Blanco")) {
            faixat.setSelection(0);
        }
        if (faixa.contains("Azul")) {
            faixat.setSelection(1);
        }
        if (faixa.contains("Violeta")) {
            faixat.setSelection(2);
        }
        if (faixa.contains("Marron")) {
            Toast.makeText(getActivity().getApplicationContext(), "Marron", Toast.LENGTH_SHORT).show();
            faixat.setSelection(3);
        }
        if (faixa.contains("Negro")) {
            faixat.setSelection(4);
        }
        if (faixa.contains("Rojo")) {
            faixat.setSelection(5);
        }

        Toast.makeText(getActivity().getApplicationContext(), "faixa: " + faixa, Toast.LENGTH_SHORT).show();
    }

    public void settextnacimiento(String fechadenacimiento) {
        EditText fecha = (EditText) getView().findViewById(R.id.editTextfecha);
        fecha.setText(fechadenacimiento);
    }

    public void miembrodesde(String mimbrodesde) {
        EditText miembros = (EditText) getView().findViewById(R.id.editTmiembro);
        miembros.setText(mimbrodesde);
    }

    public void settextprofesor(String profesor) {
        EditText profesort = (EditText) getView().findViewById(R.id.editTprofesor);
        profesort.setText(profesor);
    }

    private void add() {

        users.child(user).child("Dni").setValue(dnis);
        users.child(user).child("Miembrodesde").setValue(mimbrodesde);
        users.child(user).child("Nacimiento").setValue(fechadenacimiento);
        users.child(user).child("Nombre").setValue(nombres);
        users.child(user).child("email").setValue(emaild);
        users.child(user).child("faixa").setValue(faixa);
        users.child(user).child("permisojiujitsu").setValue(false);
        users.child(user).child("permisojudo").setValue(false);
        users.child(user).child("permisoyoga").setValue(false);
        users.child(user).child("profesor").setValue(profesoredit);
    }

}
