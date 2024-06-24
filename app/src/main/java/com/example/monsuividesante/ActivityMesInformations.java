package com.example.monsuividesante;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.EditText;
import android.widget.TextView;

public class ActivityMesInformations extends AppCompatActivity {


    private DatabaseAccess databaseAccess;
    private DatabaseOpenhelper db_helper;
    private EditText editName, editFirstName, editAge, editWeight, editHeight;
    private TextView bienvenue;

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

        // Initialiser l'accès à la base de données
        databaseAccess = DatabaseAccess.getInstance(this);
        db_helper = new DatabaseOpenhelper(this);

        /*------Temporaire----------------*/
        db_helper.deleteIdentite();
        db_helper.addLigneIdentite(user_id);
        /*--------------------------------*/

        // Initialiser les vues
        bienvenue = findViewById(R.id.text_welcome);
        editName = findViewById(R.id.edit_name);
        editFirstName = findViewById(R.id.edit_firstname);
        editAge = findViewById(R.id.edit_age);
        editWeight = findViewById(R.id.edit_weight);
        editHeight = findViewById(R.id.edit_height);

        // Charger les informations utilisateur
        loadUserInfo();

        // Ajouter les listeners pour les champs de texte
        //a modfifier Matthias à deja fait
        addTextWatchers();
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
        // Mettre à jour les champs EditText avec les informations récupérées
        editName.setText(userLastName);
        bienvenue.setText(bienv);
        editFirstName.setText(userFirstName);
        editAge.setText(String.valueOf(userAge));
        editWeight.setText(String.valueOf(userWeight));
        editHeight.setText(String.valueOf(userHeight));
    }

    private void addTextWatchers() {
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
    }
}