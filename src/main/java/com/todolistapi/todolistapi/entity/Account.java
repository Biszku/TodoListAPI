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
    @Column(name = "salt")
    private String salt = BCrypt.gensalt();

    public Account() {
    }

    public Account(String email, String password) {
        this(email, null, password);
    }

    public Account(String email, String password, String name) {
        this.email = email;
        this.name = name;
        this.password = password;
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
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void hashPassword() {
        this.password = BCrypt.hashpw(this.password, this.salt);
    }
}
