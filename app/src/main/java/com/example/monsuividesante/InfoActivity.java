package com.example.monsuividesante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mesinfo_inscription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button boutton_confirmer = findViewById(R.id.boutton_conf_mesinfo);
        boutton_confirmer.setOnClickListener(this::setOnClickListenerContinuer);
    }

    public void setOnClickListenerContinuer(View view) {
        String nom = ((EditText) findViewById(R.id.nom_mesinfo_inscr)).getText().toString();
        String prenom = ((EditText) findViewById(R.id.prenom_mesinfo_inscr)).getText().toString();
        String editTextAge = ((EditText) findViewById(R.id.age_mesinfo_inscr)).getText().toString();
        int age = Integer.parseInt(editTextAge);
        String editTextPoids = ((EditText) findViewById(R.id.poids_mesinfo_inscr)).getText().toString();
        int poids = Integer.parseInt(editTextPoids);
        String editTextTaille = ((EditText) findViewById(R.id.taille_mesinfo_inscr)).getText().toString();
        int taille = Integer.parseInt(editTextTaille);
        String genre = ((Spinner) findViewById(R.id.spinner_genre)).getSelectedItem().toString();
        String activite = ((Spinner) findViewById(R.id.spinner_vous_etes)).getSelectedItem().toString();
        int int_activite = 0;
        if(activite.equals("Sédentaire")) {
            int_activite = 1;
        }
        if(activite.equals("Légerement actif")) {
            int_activite = 2;
        }
        if(activite.equals("Modéremment actif")) {
            int_activite = 3;
        }
        if(activite.equals("Actif")) {
            int_activite = 4;
        }
        if(activite.equals("Très actif")) {
            int_activite = 5;
        }


        if(!nom.isEmpty() && !prenom.isEmpty() && ((age >= 13) && (age <= 110)) && ((poids >= 10) && (poids <= 300)) && ((taille >= 50) && (taille <= 230)) && int_activite != 0 && !genre.equals("Genre")){
            final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
            db.open();
            db.addInfo(0, nom, prenom, age, poids, taille, genre, int_activite);
            db.close();
            /* A chager */
            Intent intent = new Intent(InfoActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"donnée invalide", Toast.LENGTH_SHORT).show();
        }





    }
}