package com.example.dojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pojorecords implements Parcelable {

    private String ejercicio;
    private String index;
    private String objetivo;
    private String pesomaximo;
    private String repeticiones;


    public Pojorecords(){

    }

    public Pojorecords(String ejercicio, String index, String objetivo, String pesomaximo, String repeticiones )
    {
        this.ejercicio = ejercicio;
        this.index = index;
        this.objetivo = objetivo;
        this.pesomaximo = pesomaximo;
        this.repeticiones = repeticiones;
    }

    protected Pojorecords(Parcel in) {
        ejercicio = in.readString();
        index = in.readString();
        objetivo = in.readString();
        pesomaximo = in.readString();
        repeticiones = in.readString();
    }

    public static final Creator<Pojorecords> CREATOR = new Creator<Pojorecords>() {
        @Override
        public Pojorecords createFromParcel(Parcel in) {
            return new Pojorecords(in);
        }

        @Override
        public Pojorecords[] newArray(int size) {
            return new Pojorecords[size];
        }
    };

    public String getEjercicio(){
        return ejercicio;
    }
    public String getIndex(){
        return index;
    }
    public String getObjetivo(){
        return objetivo;
    }
    public String getPesomaximo(){
        return pesomaximo;
    }
    public String getRepeticiones(){
        return repeticiones;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ejercicio);
        parcel.writeString(index);
        parcel.writeString(objetivo);
        parcel.writeString(pesomaximo);
        parcel.writeString(repeticiones);
    }
}
