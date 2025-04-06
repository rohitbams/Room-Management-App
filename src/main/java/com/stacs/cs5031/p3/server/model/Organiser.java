package com.stacs.cs5031.p3.server.model;

import org.springframework.stereotype.Component;

@Component
public class Organiser {
    private int id;
    private String name;
    private String username;
    private String password;
    
    public Organiser(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

}
