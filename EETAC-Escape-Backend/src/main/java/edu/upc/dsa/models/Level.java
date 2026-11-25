package edu.upc.dsa.models;

public class Level {
    private String id;
    private String nom;
    private String assignatura;
    private int ordre;
    private int recompensaEcts;
    private String fragmentId;

    public Level() {
    }

    public Level(String id, String nom, String assignatura, int ordre, int recompensaEcts, String fragmentId) {
        this.id = id;
        this.nom = nom;
        this.assignatura = assignatura;
        this.ordre = ordre;
        this.recompensaEcts = recompensaEcts;
        this.fragmentId = fragmentId;
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

    public String getAssignatura() {
        return assignatura;
    }

    public void setAssignatura(String assignatura) {
        this.assignatura = assignatura;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public int getRecompensaEcts() {
        return recompensaEcts;
    }

    public void setRecompensaEcts(int recompensaEcts) {
        this.recompensaEcts = recompensaEcts;
    }

    public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }
}
