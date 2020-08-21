package com.example.dojo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserView extends FragmentActivity {

    public NavigationView navigationView;
    Fragment selectedFragment = null;
    public DatabaseReference users;
    public String id;
    public String Nombre;
    public String mensaje;
    private FirebaseAuth firebaseAuth;
    public String intencion;
    String pickedImageUri ;
    public String userstringdeloginymain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userview);

        firebaseAuth = FirebaseAuth.getInstance();

        users = FirebaseDatabase.getInstance().getReference("users");

        String uri = getIntent().getStringExtra("photouser");

        intencion = getIntent().getStringExtra("user");
        /*
          userapasar = email.substring(0,pos);
       if (userapasar.contains(".")){
           userapasar = email.substring(0,pos2);
       }
         */
        int pos = intencion.indexOf("@");
        int pos2 = intencion.indexOf(".");

        userstringdeloginymain = intencion.substring(0, pos);
        if (userstringdeloginymain.contains(".")){
            userstringdeloginymain = intencion.substring(0,pos2);
        }

   //     Toast.makeText(UserView.this, "Nombre de usuario obtenido:" + userstringdeloginymain, Toast.LENGTH_SHORT).show();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                // Handle the back button event
                selectedFragment = new OlduserviewFragment();

                Bundle bundlehome = new Bundle();
                bundlehome.putString("userhome", userstringdeloginymain);

                selectedFragment.setArguments(bundlehome);
                replace(selectedFragment);
            }
        };

        this.getOnBackPressedDispatcher().addCallback(this, callback);

        try {

            users.child(userstringdeloginymain).child("Mensaje").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //here we get the data from the snapshot
                  mensaje = snapshot.getValue(String.class);
           //       Toast.makeText(UserView.this, "Mensaje Principal" + mensaje, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            final DrawerLayout drawerLayout = findViewById(R.id.drawelayout);

            findViewById(R.id.imagemenu).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            users.child(userstringdeloginymain).child("Nombre").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     Nombre = snapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            if (findViewById(R.id.navHostFragment) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                OlduserviewFragment homefragment = new OlduserviewFragment();
                Bundle bundlehome = new Bundle();
                bundlehome.putString("userhome", userstringdeloginymain);
                homefragment.setArguments(bundlehome);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.navHostFragment, homefragment).commit();
            }

        }catch (Exception e) {

        }

        navigationView = findViewById(R.id.navigationview);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.Pagojiujitsu:
                            selectedFragment = new PagoJiujitsuFragment();
                            Bundle bundlejiu = new Bundle();
                            bundlejiu.putString("userjiu", userstringdeloginymain);
                            selectedFragment.setArguments(bundlejiu);
                            replace(selectedFragment);
                            break;
                        case R.id.Pagojudo:
                            selectedFragment = new PagoJudoFragment();
                            Bundle bundlejudo = new Bundle();
                            bundlejudo.putString("userjudo", userstringdeloginymain);
                            selectedFragment.setArguments(bundlejudo);
                            replace(selectedFragment);
                            break;
                        case R.id.Pagoyoga:
                            selectedFragment = new PagoYogaFragment();
                            Bundle bundleyoga = new Bundle();
                            bundleyoga.putString("useryoga", userstringdeloginymain);
                            selectedFragment.setArguments(bundleyoga);
                            replace(selectedFragment);
                            break;
                        case R.id.Cambiarusuario:
                            selectedFragment = new CambiarUsuarioFragment();
                            Intent intentlog = new Intent(UserView.this,Login.class);
                            startActivity(intentlog);
                           // replace(selectedFragment);
                            break;
                        case R.id.editarperfil:
                            selectedFragment = new EditarPerfilFragment();

                            Bundle bundleedit = new Bundle();
                            bundleedit.putString("useredit", userstringdeloginymain);

                            selectedFragment.setArguments(bundleedit);
                            replace(selectedFragment);

                            break;
                        case R.id.Mensajeria:
                            selectedFragment = new MensajeriaFragment();
                            replace(selectedFragment);
                            break;
                        case R.id.OutFragment:
                           System.exit(0);
                            break;
                        case R.id.Reportarbugs:
                            selectedFragment = new PlaystoreFragment();
                            replace(selectedFragment);
                            break;
                        case R.id.olduserviewFragment: //home fragment
                                selectedFragment = new OlduserviewFragment();

                            Bundle bundlehome = new Bundle();
                            bundlehome.putString("userhome", userstringdeloginymain);

                            selectedFragment.setArguments(bundlehome);
                            replace(selectedFragment);
                            break;
                        case R.id.contactoFragment:
                            selectedFragment = new ContactoFragment();
                            replace(selectedFragment);
                            break;
                        case R.id.fisico:
                            //selectedFragment = new FisicoFragment();

                            Intent intentfisico = new Intent(UserView.this, Estadistica.class);
                            intentfisico.putExtra("usernameforfisico",Nombre);
                            startActivity(intentfisico);
                            //replace(selectedFragment);
                            break;
                        case R.id.pagos:
                            selectedFragment = new PagosFragment();
                            replace(selectedFragment);
                            break;
                        case R.id.verusuario:
                            selectedFragment = new Fragmentovistadeusuario();

                              Bundle bundle = new Bundle();
                              bundle.putString("userfragment", userstringdeloginymain);

                            selectedFragment.setArguments(bundle);
                            replace(selectedFragment);
                            break;
                    }
                    return true;
                    }
                });
    }


    public  void replace(Fragment selectedFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.navHostFragment,selectedFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
