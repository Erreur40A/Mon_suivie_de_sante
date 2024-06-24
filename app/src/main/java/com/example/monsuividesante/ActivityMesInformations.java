package com.example.monsuividesante;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

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


    private DatabaseAccess databaseAccess;
    private DatabaseOpenhelper db_helper;
    private ConstraintLayout layout_nom, layout_prenom, layout_age, layout_taille, layout_poids;
    //private EditText editName, editFirstName, editAge, editWeight, editHeight;
    private TextView bienvenue, nom, prenom, age, taille, poids;
    //private User user;

    //a modifier
    private int user_id = 1;

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

        //enlever le commentaire lors de la fusion
        //user=getIntent().getSerializableExtra("user");

        // Initialiser l'accès à la base de données
        databaseAccess = DatabaseAccess.getInstance(this);
        db_helper = new DatabaseOpenhelper(this);

        /*------Temporaire----------------*/
        db_helper.deleteIdentite();
        db_helper.addLigneIdentite(user_id);
        /*--------------------------------*/

        /*------------------Avec pop-up-------------------*/

        layout_age = findViewById(R.id.layout_age);
        layout_nom = findViewById(R.id.layout_nom);
        layout_prenom = findViewById(R.id.layout_prenom);
        layout_taille = findViewById(R.id.layout_taille);
        layout_poids = findViewById(R.id.layout_poids);

        age = layout_age.findViewById(R.id.age);
        taille = layout_taille.findViewById(R.id.taille);
        nom = layout_nom.findViewById(R.id.nom);
        prenom = layout_prenom.findViewById(R.id.prenom);
        poids = layout_poids.findViewById(R.id.poids);

        bienvenue = findViewById(R.id.text_welcome);

        // Charger les informations utilisateur
        loadUserInfo();

        layout_age.setOnClickListener(this::onClickListenerAge);
        layout_nom.setOnClickListener(this::onClickListenerNom);
        layout_prenom.setOnClickListener(this::onClickListenerPrenom);
        layout_taille.setOnClickListener(this::onClickListenerTaille);
        layout_poids.setOnClickListener(this::onClickListenerPoids);

        /*------------------------------------------------*/

        /*-------Sans pop-up--------------------------*/
        // Initialiser les vues
        /*bienvenue = findViewById(R.id.text_welcome);
        editName = findViewById(R.id.edit_name);
        editFirstName = findViewById(R.id.edit_firstname);
        editAge = findViewById(R.id.edit_age);
        editWeight = findViewById(R.id.edit_weight);
        editHeight = findViewById(R.id.edit_height);

        // Charger les informations utilisateur
        loadUserInfo();

        // Ajouter les listeners pour les champs de texte
        //a modfifier Matthias à deja fait
        addTextWatchers();*/
        /*---------------------------------------------*/
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
                poids.setText(affichage);
                poids.setTextColor(Color.BLACK);
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
                age.setText(affichage);
                age.setTextColor(Color.BLACK);
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
                taille.setText(affichage);
                taille.setTextColor(Color.BLACK);
            }

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    /*Listener des boutons de la toolbar*/
    public void onClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(ActivityMesInformations.this, MainActivity.class);
        /*enlever le // du dessous*/
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonCalorie(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Calories*/
        Intent intent = new Intent(ActivityMesInformations.this, MainActivity.class);
        /*enlever le // du dessous*/
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonMesInfo(View view){
        /*Soit on supprime ce listener soit on le garde*/
        Intent intent = new Intent(ActivityMesInformations.this, ActivityMesInformations.class);
        /*enlever le // du dessous*/
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Sommeil)*/
        Intent intent = new Intent(ActivityMesInformations.this, MainActivity.class);
        /*enlever le // du dessous*/
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    private void loadUserInfo() {
        // Charger les informations de l'utilisateur depuis la base de données
        databaseAccess.open();

        String userLastName = databaseAccess.getUserLastName(user_id);
       // String userLastName = databaseAccess.getMsgMotivation(2);

        String userFirstName = databaseAccess.getUserFirstName(user_id);

        int userAge = databaseAccess.getUserAge(user_id);
        int userWeight = databaseAccess.getUserWeight(user_id);
        int userHeight = databaseAccess.getUserHeight(user_id);

        databaseAccess.close();

        String bienv="Bienvenue " + userFirstName;
        // Mettre à jour les champs TextView avec les informations récupérées
        nom.setText(userLastName);
        bienvenue.setText(bienv);
        prenom.setText(userFirstName);
        age.setText(String.valueOf(userAge));
        poids.setText(String.valueOf(userWeight));
        taille.setText(String.valueOf(userHeight));
    }

    /*private void addTextWatchers() {
        editName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updateUserInfo("nom", editName.getText().toString());
            }
        });

        editFirstName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updateUserInfo("prenom", editFirstName.getText().toString());
                bienvenue.setText("Bienvenue " + editFirstName.getText().toString());
            }
        });

        editAge.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updateUserInfo("age", editAge.getText().toString());
            }
        });

        editWeight.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updateUserInfo("poids", editWeight.getText().toString());
            }
        });

        editHeight.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updateUserInfo("taille", editHeight.getText().toString());
            }
        });
    }


    private void updateUserInfo(String column, String value) {
        databaseAccess.open();
        String query = "UPDATE identite SET " + column + " = ? WHERE id = 1";
        //modification a faire
        //databaseAccess.open();
        //SQLiteDatabase db = databaseAccess.getWritableDatabase();
        //db.execSQL(query, new String[]{value});
        databaseAccess.close(); // Ferme la connexion à la base de données
    }*/
}