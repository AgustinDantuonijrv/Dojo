package com.example.dojo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScrennActivity extends AppCompatActivity {

    public Timer timer;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Toast.makeText(SplashScrennActivity.this,"Cargando Registros",Toast.LENGTH_SHORT).show();
        timer = new Timer();
        timer.schedule(new TimerTask() { //el timer como objeto permite crear esta funcion override run a la que se le agrega un intent por abrir una nueva actividad
            @Override
            public void run() {

                Intent intent = new Intent(SplashScrennActivity.this, Register.class);
                startActivity(intent);
                finish();
            }

        } ,3000); // el tiempo antes de que se abra la otra actividad

    }
}
