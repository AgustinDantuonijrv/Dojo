package com.example.dojo;

public class LocalUser {
    public String username;
    public String id;
    public String email;
    public String password;

    public LocalUser(String id,String username, String email, String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
