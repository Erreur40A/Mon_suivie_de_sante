package com.example.monsuividesante;

import java.io.Serializable;

public class User implements Serializable {

    private final int id;
    private String prenom;
    private String nom;
    private int age;
    private int poids;
    private int taille;
    private String genre;
    private int type_de_personne;

    public User(int id, String prenom, String nom, int age, int poids, int taille, String genre, int type_de_personne){
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.poids = poids;
        this.taille = taille;
        this.genre = genre;
        this.type_de_personne = type_de_personne;
    }

    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

    public int getPoids() {
        return poids;
    }

    public int getTaille() {
        return taille;
    }

    public String getGenre() {
        return genre;
    }

    public int getType_de_personne() {
        return type_de_personne;
    }


    public void setPrenom(String prenom, DatabaseOpenhelper openhelper) {
        this.prenom = prenom;
        openhelper.updatePrenom(prenom, this.id);
    }

    public void setNom(String nom, DatabaseOpenhelper openhelper) {
        this.nom = nom;
        openhelper.updateNom(nom, id);
    }

    public void setAge(int age, DatabaseOpenhelper openhelper) {
        this.age = age;
        openhelper.updateAge(age, this.id);
    }

    public void setPoids(int poids, DatabaseOpenhelper openhelper) {
        this.poids = poids;
        openhelper.updatePoids(poids, this.id);
    }

    public void setTaille(int taille, DatabaseOpenhelper openhelper) {
        this.taille = taille;
        openhelper.updateTaille(taille, this.id);
    }

    public void setGenre(String genre, DatabaseOpenhelper openhelper) {
        this.genre = genre;
        openhelper.updateGenre(genre, this.id);
    }

    public void setType_de_personne(int type_de_personne, DatabaseOpenhelper openhelper) {
        this.type_de_personne = type_de_personne;
        openhelper.updateTypeDePersonne(type_de_personne, this.id);
    }
}
