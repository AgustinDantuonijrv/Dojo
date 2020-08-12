package com.example.dojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pojovideos implements Parcelable {

    private String titulo;
    private String Uri;

    public Pojovideos(){
    }

    public Pojovideos(String titulo, String Uri){
        this.titulo = titulo;
        this.Uri = Uri;
    }

    protected Pojovideos(Parcel in) {
        titulo = in.readString();
        Uri = in.readString();
    }

    public static final Creator<Pojovideos> CREATOR = new Creator<Pojovideos>() {
        @Override
        public Pojovideos createFromParcel(Parcel in) {
            return new Pojovideos(in);
        }

        @Override
        public Pojovideos[] newArray(int size) {
            return new Pojovideos[size];
        }
    };

    public String getTitulo(){
        return titulo;
    }
    public String getUri(){
        return Uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(Uri);
    }
}