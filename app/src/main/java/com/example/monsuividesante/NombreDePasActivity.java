package com.example.monsuividesante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;

import androidx.core.view.WindowInsetsCompat;

public class NombreDePasActivity extends AppCompatActivity {

    private Button buttonStylo, buttonInfos, buttonPas, buttonCalories, buttonSommeil;
    private TextView textViewObjectifs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_de_pas);

        // Initialiser les composants UI
        buttonStylo = findViewById(R.id.button_stylo);
        buttonInfos = findViewById(R.id.button_infos);
        buttonPas = findViewById(R.id.button_pas);
        buttonCalories = findViewById(R.id.button_calories);
        buttonSommeil = findViewById(R.id.button_sommeil);
        textViewObjectifs = findViewById(R.id.text_view_objectifs);

        // Listener pour le bouton Stylo
        buttonStylo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Afficher la zone de texte pour entrer l'objectif
                textViewObjectifs.setVisibility(View.VISIBLE);
                Toast.makeText(NombreDePasActivity.this, "Entrez votre objectif.", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener pour le bouton Mes Informations
        buttonInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer de menu ou d'activité
                Intent intent = new Intent(NombreDePasActivity.this, NombreDePasActivity.class);
                startActivity(intent);
            }
        });

        // Listener pour le bouton Pas
        buttonPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer de menu ou d'activité
                Toast.makeText(NombreDePasActivity.this, "Affichage des pas.", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener pour le bouton Calories
        buttonCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer de menu ou d'activité
                Toast.makeText(NombreDePasActivity.this, "Affichage des calories.", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener pour le bouton Sommeil
        buttonSommeil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer de menu ou d'activité
                Toast.makeText(NombreDePasActivity.this, "Affichage du sommeil.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
