package edu.estudiantat.upc.EETACEscape;

public class GameSession {
    private String id;
    private String userId;
    private int errors;
    private boolean completat;
    private int puntuacio;
    private int tempsEmprat;
    private String data;
    private int pistesUsades;

    public GameSession() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public boolean isCompletat() {
        return completat;
    }

    public void setCompletat(boolean completat) {
        this.completat = completat;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public int getTempsEmprat() {
        return tempsEmprat;
    }

    public void setTempsEmprat(int tempsEmprat) {
        this.tempsEmprat = tempsEmprat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPistesUsades() {
        return pistesUsades;
    }

    public void setPistesUsades(int pistesUsades) {
        this.pistesUsades = pistesUsades;
    }
}
