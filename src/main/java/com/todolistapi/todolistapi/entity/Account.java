package com.todolistapi.todolistapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    public Account() {
    }

    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
