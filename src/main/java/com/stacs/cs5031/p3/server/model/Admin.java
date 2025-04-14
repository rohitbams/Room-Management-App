package com.stacs.cs5031.p3.server.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
    }
    
    public Admin(String name, String username, String password) {
        super(name, username, password);
    }
}
