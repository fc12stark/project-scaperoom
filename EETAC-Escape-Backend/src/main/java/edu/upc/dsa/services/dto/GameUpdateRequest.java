package edu.upc.dsa.services.dto;

public class GameUpdateRequest {
    private Integer errors;
    private Boolean completat;
    private Integer puntuacio;
    private Integer tempsEmprat;
    private Integer pistesUsades;

    public GameUpdateRequest() {
    }

    public Integer getErrors() {
        return errors;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public Boolean getCompletat() {
        return completat;
    }

    public void setCompletat(Boolean completat) {
        this.completat = completat;
    }

    public Integer getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(Integer puntuacio) {
        this.puntuacio = puntuacio;
    }

    public Integer getTempsEmprat() {
        return tempsEmprat;
    }

    public void setTempsEmprat(Integer tempsEmprat) {
        this.tempsEmprat = tempsEmprat;
    }

    public Integer getPistesUsades() {
        return pistesUsades;
    }

    public void setPistesUsades(Integer pistesUsades) {
        this.pistesUsades = pistesUsades;
    }
}
