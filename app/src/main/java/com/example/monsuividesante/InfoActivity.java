package com.example.monsuividesante;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        int id=getIntent().getIntExtra("user_id", 0);
        String nom = ((EditText) findViewById(R.id.nom_mesinfo_inscr)).getText().toString();
        if(nom.isEmpty()) return;

        String prenom = ((EditText) findViewById(R.id.prenom_mesinfo_inscr)).getText().toString();
        if(prenom.isEmpty()) return;

        String editTextAge = ((EditText) findViewById(R.id.age_mesinfo_inscr)).getText().toString();
        if(editTextAge.isEmpty()) return;

        int age = Integer.parseInt(editTextAge);

        String editTextPoids = ((EditText) findViewById(R.id.poids_mesinfo_inscr)).getText().toString();
        if(editTextPoids.isEmpty()) return;

        int poids = Integer.parseInt(editTextPoids);

        String editTextTaille = ((EditText) findViewById(R.id.taille_mesinfo_inscr)).getText().toString();
        if(editTextTaille.isEmpty()) return;

        int taille = Integer.parseInt(editTextTaille);

        int int_genre = ((Spinner) findViewById(R.id.spinner_genre)).getSelectedItemPosition();
        Genre genre;
        if(int_genre == 0) genre = Genre.HOMME;
        else genre = Genre.FEMME;

        int int_activite = ((Spinner) findViewById(R.id.spinner_vous_etes)).getSelectedItemPosition();
        int_activite ++;

        if(Regex.estAgeValide(editTextAge) && Regex.estPoidsValide(editTextPoids) && Regex.estTailleValide(editTextTaille)){
            final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
            db.open();
            db.addInfo(id, nom, prenom, age, poids, taille, genre.getGenre(), int_activite);
            db.close();

            User user = new User(id, prenom, nom, age, poids, taille, genre, int_activite);
            Intent intent = new Intent(InfoActivity.this, ActivityMesInformations.class);
            intent.putExtra("user", user);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Certaines données saisie ne peuvent pas correspondre à la réalité", Toast.LENGTH_SHORT).show();
        }
    }
}