package com.bac.tn.gestionusers.message;

public class ContactForm {

    private String nom;

    private String sujet;

    private String numTel;

    private String email;

    private String message;

    public ContactForm() {
    }

    public ContactForm(String nom, String sujet, String numTel, String email, String message) {
        this.nom = nom;
        this.sujet = sujet;
        this.numTel = numTel;
        this.email = email;
        this.message = message;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
