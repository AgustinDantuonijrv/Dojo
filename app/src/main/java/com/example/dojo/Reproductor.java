package com.example.dojo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Reproductor extends AppCompatActivity  {

    public VideoView videoView;
    public Uri uri;
    public String titulo;
    public TextView tw;

    public MediaPlayer.OnCompletionListener completionListener, completionListener2;

    //if the video does not work using the uri get from the position we can try to have an index and get the data from the database using the index value
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);

        Pojovideos video = getIntent().getParcelableExtra("video");
        uri = Uri.parse(video.getUri());
        titulo = video.getTitulo();
        initviews();


        final MediaController mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(videoView);

        videoView.setMediaController(mediaController2);
        videoView.setEnabled(true);
        videoView.setVideoURI(uri);

        PlayVideo();
    }

    //WE AVE TO HAVE IN CONSIDERATION THAT THE POSITION GETS THE URI AND THE TITTLE TO SET IN THE TEXT AND THEN WE HAVE TO SUPPORT ALL THAT IS BASICALLY NEEDED IN A MEDIA PLAYER (SEE ANDROID DEVELOPERS OFICIAL DOCUMENTATION)
    private void initviews() {
        videoView = findViewById(R.id.videoView);
        tw = findViewById(R.id.titulodelvideo);
    }


    private void PlayVideo() {

        videoView.setOnCompletionListener(completionListener);

        try {

            MediaController mediaController2 = new MediaController(Reproductor.this);
            mediaController2.setAnchorView(videoView);

            videoView.setMediaController(mediaController2);
            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.start();
                    tw.setText(titulo);
                }
            });

        } catch (Exception e) {

            System.out.println("Video Play Error :" + e.toString());
            finish();

        }
    }

}
