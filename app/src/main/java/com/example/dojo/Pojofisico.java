package com.example.dojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pojofisico implements Parcelable {

    private String Observacionesdeusuario;
    private String diaparte;
    private String index;
    private String rutinaformato;


    public Pojofisico(){
    }

    public Pojofisico(String Observacionesdeusuario, String diaparte, String index, String rutinaformato)
    {
        this.Observacionesdeusuario = Observacionesdeusuario;
        this.diaparte = diaparte;
        this.index = index;
        this.rutinaformato = rutinaformato;
    }

    protected Pojofisico(Parcel in) {
        Observacionesdeusuario = in.readString();
        diaparte = in.readString();
        index = in.readString();
        rutinaformato = in.readString();
    }

    public static final Creator<Pojofisico> CREATOR = new Creator<Pojofisico>() {
        @Override
        public Pojofisico createFromParcel(Parcel in) {
            return new Pojofisico(in);
        }

        @Override
        public Pojofisico[] newArray(int size) {
            return new Pojofisico[size];
        }
    };

    public String getObservacionesdeusuario() {
        return Observacionesdeusuario;
    }
    public String getDiaparte() {
        return  diaparte;
    }
    public String getIndex() {
        return index;
    }
    public String getRutinaformato() {
        return rutinaformato;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Observacionesdeusuario);
        parcel.writeString(diaparte);
        parcel.writeString(index);
        parcel.writeString(rutinaformato);
    }
}
