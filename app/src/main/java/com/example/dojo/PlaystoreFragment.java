package com.example.dojo;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.webkit.WebResourceRequest;

public class PlaystoreFragment extends Fragment {

    public WebSettings webSettings;

    public PlaystoreFragment() {
        // Required empty public constructor
    }

WebView w;
    String url = "https://play.google.com/store/apps?hl=en_US";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_playstore, container, false);

        w = (WebView)rootview.findViewById(R.id.playstorewebview);
        w.loadUrl(url);
        WebSettings settings = w.getSettings();
        settings.setJavaScriptEnabled(true);
       // w.setWebViewClient(new WebViewClient());
        return  rootview;
    }


}
