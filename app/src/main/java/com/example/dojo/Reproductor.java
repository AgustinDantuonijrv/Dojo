package com.example.dojo;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Reproductor extends AppCompatActivity {

    public VideoView videoView;
    public Uri uri;
    public String titulo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);

      Pojovideos video = getIntent().getParcelableExtra("video");
      uri = Uri.parse(video.getUri());
      titulo = video.getTitulo();

      initviews();
    }
    private void initviews(){
        videoView = findViewById(R.id.videoView);
        startvideo();
    }
    public void startvideo(){
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
