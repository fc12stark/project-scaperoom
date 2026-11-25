package edu.upc.dsa.models;

import java.util.UUID;

public class Item {
    private String id;
    private String nom;
    private String descripcio;
    private int preu;
    private String tipus;

    public Item() {
        this.id = UUID.randomUUID().toString();
    }

    public Item(String id, String nom, String descripcio, int preu, String tipus) {
        this();
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
        this.tipus = tipus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
