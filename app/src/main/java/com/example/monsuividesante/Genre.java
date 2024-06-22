package com.example.monsuividesante;

public enum Genre {
    HOMME("Homme"),
    FEMME("Femme");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
