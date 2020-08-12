package com.example.dojo;

import java.io.Serializable;

//id nombre password fecha juji judo yoga dni emailservidor
public class User implements Serializable {
    public String id;
    public String nombre;
    public String password;
    public String fecha;
    public String juji;
    public String judo;
    public String yoga;
    public String dni;
    public String emailservidor;

    public User(String id,String nombre,String password, String fecha, String juji, String judo, String yoga, String dni, String emailservidor) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.fecha = fecha;
        this.juji = juji;
        this.judo = judo;
        this.yoga = yoga;
        this.dni = dni;
        this.emailservidor = emailservidor;
    }

}
