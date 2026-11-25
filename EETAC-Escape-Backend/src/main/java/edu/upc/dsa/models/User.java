package edu.upc.dsa.models;

import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private int ects;
    private int nivellActual;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.nivellActual = 1;
    }

    public User(String username, String password, String email, int ects) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.ects = ects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public int getNivellActual() {
        return nivellActual;
    }

    public void setNivellActual(int nivellActual) {
        this.nivellActual = nivellActual;
    }
}
