package com.example.monsuividesante;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActivityMesInformations extends AppCompatActivity {


    private DatabaseOpenhelper db_helper;
    private TextView bienvenue, nom, prenom, age, taille, poids;

    private Spinner spinner_genre;
    private Spinner spinner_type;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mes_informations);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_mes_informations), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = (User) getIntent().getSerializableExtra("user");

        // Initialiser l'accès à la base de données
        db_helper = new DatabaseOpenhelper(this);

        /*------------------Avec pop-up-------------------*/

        ConstraintLayout layout_age = findViewById(R.id.layout_age);
        ConstraintLayout layout_nom = findViewById(R.id.layout_nom);
        ConstraintLayout layout_prenom = findViewById(R.id.layout_prenom);
        ConstraintLayout layout_taille = findViewById(R.id.layout_taille);
        ConstraintLayout layout_poids = findViewById(R.id.layout_poids);

        // Référence au Spinner
        spinner_genre = findViewById(R.id.spinner_genre);
        spinner_type = findViewById(R.id.spinner_type_de_personne);

        // Configuration de l'adaptateur pour le Spinner_genre
        ArrayAdapter<CharSequence> adapter_genre = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
        adapter_genre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_genre.setAdapter(adapter_genre);

        // Configuration de l'adaptateur pour le Spinner_type
        ArrayAdapter<CharSequence> adapter_type = ArrayAdapter.createFromResource(this,
                R.array.type_de_personne_array, android.R.layout.simple_spinner_item);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter_type);

        int position = 0;

        if(user.getGenre().equals(Genre.FEMME))
            position=1;

        spinner_genre.setSelection(position);

        // OnItemSelectedListener sur le Spinner_genre
        spinner_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer l'élément sélectionné
                Genre genre;
                if(position == 0) {
                    genre = Genre.HOMME;
                } else {
                    genre = Genre.FEMME;
                }
                user.setGenre(genre, db_helper);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int selection=0;

                if(user.getGenre().equals(Genre.FEMME))
                    selection=1;

                spinner_genre.setSelection(selection);
            }
        });

        spinner_type.setSelection(user.getType_de_personne()-1);

        // Définir un OnItemSelectedListener sur le Spinner_type
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer l'élément sélectionné
                position++;
                user.setType_de_personne(position, db_helper);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int selection = user.getType_de_personne();
                spinner_type.setSelection(selection-1);
            }
        });

        age = layout_age.findViewById(R.id.age);
        ImageButton bouton_age = layout_age.findViewById(R.id.bouton_age);
        taille = layout_taille.findViewById(R.id.taille);
        ImageButton bouton_taille = layout_taille.findViewById(R.id.bouton_taille);
        nom = layout_nom.findViewById(R.id.nom);
        ImageButton bouton_nom = layout_nom.findViewById(R.id.bouton_nom);
        prenom = layout_prenom.findViewById(R.id.prenom);
        ImageButton bouton_prenom = layout_prenom.findViewById(R.id.bouton_prenom);
        poids = layout_poids.findViewById(R.id.poids);
        ImageButton bouton_poids = layout_poids.findViewById(R.id.bouton_poids);

        bienvenue = findViewById(R.id.text_welcome).findViewById(R.id.welcome);

        // Charger les informations utilisateur
        loadUserInfo();

        layout_age.setOnClickListener(this::onClickListenerAge);
        layout_nom.setOnClickListener(this::onClickListenerNom);
        layout_prenom.setOnClickListener(this::onClickListenerPrenom);
        layout_taille.setOnClickListener(this::onClickListenerTaille);
        layout_poids.setOnClickListener(this::onClickListenerPoids);

        bouton_age.setOnClickListener(this::onClickListenerAge);
        bouton_nom.setOnClickListener(this::onClickListenerNom);
        bouton_prenom.setOnClickListener(this::onClickListenerPrenom);
        bouton_taille.setOnClickListener(this::onClickListenerTaille);
        bouton_poids.setOnClickListener(this::onClickListenerPoids);

        //boutton de la toolbar
        ConstraintLayout toolbar = findViewById(R.id.toolbar);
        LinearLayout pas = toolbar.findViewById(R.id.pas);
        LinearLayout mes_info = toolbar.findViewById(R.id.mes_info);
        LinearLayout calories = toolbar.findViewById(R.id.calories);
        LinearLayout sommeil = toolbar.findViewById(R.id.sommeil);

        pas.setAlpha(0.4F);
        sommeil.setAlpha(0.4F);
        calories.setAlpha(0.4F);

        ImageButton bouton_pas = pas.findViewById(R.id.bouton_pas);
        bouton_pas.setOnClickListener(this::onClickListenerBoutonPas);

        ImageButton bouton_mes_info = mes_info.findViewById(R.id.bouton_mes_info);
        bouton_mes_info.setOnClickListener(this::onClickListenerBoutonMesInfo);

        ImageButton bouton_calories = calories.findViewById(R.id.bouton_calories);
        bouton_calories.setOnClickListener(this::onClickListenerBoutonCalorie);

        ImageButton bouton_sommeil = sommeil.findViewById(R.id.bouton_sommeil);
        bouton_sommeil.setOnClickListener(this::onClickListenerBoutonSommeil);
    }

    public void onClickListenerNom(View view){
        AlertDialog.Builder pop_up_calorie_consomme = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_calorie_consomme.setView(R.layout.pop_up_nom);

        AlertDialog pop_up = pop_up_calorie_consomme.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            if(Regex.estNomPrenomValide(affichage)) {
                nom.setText(affichage);
                nom.setTextColor(Color.BLACK);
                user.setNom(affichage, db_helper);
            }

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    public void onClickListenerPrenom(View view){
        AlertDialog.Builder pop_up_calorie_consomme = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_calorie_consomme.setView(R.layout.pop_up_prenom);

        AlertDialog pop_up = pop_up_calorie_consomme.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            if(Regex.estNomPrenomValide(affichage)) {
                prenom.setText(affichage);
                prenom.setTextColor(Color.BLACK);
                String bienv = "Bienvenue " + affichage;
                bienvenue.setText(bienv);
                user.setPrenom(affichage, db_helper);
            }

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    public void onClickListenerPoids(View view){
        AlertDialog.Builder pop_up_calorie_consomme = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_calorie_consomme.setView(R.layout.pop_up_poids);

        AlertDialog pop_up = pop_up_calorie_consomme.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            if(Regex.estPoidsValide(affichage)) {
                poids.setTextColor(Color.BLACK);
                user.setPoids(Integer.parseInt(affichage), db_helper);
                affichage += " kg";
                poids.setText(affichage);
            }

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    public void onClickListenerAge(View view){
        AlertDialog.Builder pop_up_calorie_consomme = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_calorie_consomme.setView(R.layout.pop_up_age);

        AlertDialog pop_up = pop_up_calorie_consomme.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            if(Regex.estAgeValide(affichage)) {
                age.setTextColor(Color.BLACK);
                user.setAge(Integer.parseInt(affichage), db_helper);
                affichage += " ans";
                age.setText(affichage);
            }

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    public void onClickListenerTaille(View view){
        AlertDialog.Builder pop_up_calorie_consomme = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_calorie_consomme.setView(R.layout.pop_up_taille);

        AlertDialog pop_up = pop_up_calorie_consomme.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            if(Regex.estTailleValide(affichage)) {
                taille.setTextColor(Color.BLACK);
                user.setTaille(Integer.parseInt(affichage), db_helper);
                affichage += " cm";
                taille.setText(affichage);
            }

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    /*Listener des boutons de la toolbar*/
    public void onClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(ActivityMesInformations.this, NombreDePasActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonCalorie(View view){
        Intent intent = new Intent(ActivityMesInformations.this, CaloriesActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonMesInfo(View view){
        /*Soit on supprime ce listener soit on le garde*/
        Intent intent = new Intent(ActivityMesInformations.this, ActivityMesInformations.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        Intent intent = new Intent(ActivityMesInformations.this, SommeilActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void loadUserInfo() {
        String userLastName = user.getNom();
        String userFirstName = user.getPrenom();
        int userAge = user.getAge();
        int userWeight = user.getPoids();
        int userHeight = user.getTaille();

        String bienv = "Bienvenue " + userFirstName;
        String poids_afficher = userWeight + " kg";
        String taille_afficher = userHeight + " cm";
        String age_afficher = userAge + " ans";

        nom.setText(userLastName);
        bienvenue.setText(bienv);
        prenom.setText(userFirstName);
        age.setText(age_afficher);
        poids.setText(poids_afficher);
        taille.setText(taille_afficher);
    }
}