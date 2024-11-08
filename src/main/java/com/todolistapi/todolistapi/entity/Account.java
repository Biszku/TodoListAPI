package com.todolistapi.todolistapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

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
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Todo> posts;

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
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Todo> getPosts() {
        return posts;
    }

    public void setPosts(List<Todo> posts) {
        this.posts = posts;
    }

    public void addPost(Todo post) {
        this.posts.add(post);
        post.setAccount(this);
    }

    public void hashPassword() {
        this.password = BCrypt.hashpw(this.password, this.salt);
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
