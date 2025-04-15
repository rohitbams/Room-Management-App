package com.stacs.cs5031.p3.server.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "organisers")
public class Organiser extends User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String name;
    private String username;
    private String password;
    
    public Organiser() {
    }
    public Organiser(String name, String username, String password) {
        super(name, username, password);
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String userName){
        this.username = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }


}
