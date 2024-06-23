package com.example.monsuividesante;

import android.database.Cursor;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityMesInformations extends AppCompatActivity {


    private DatabaseAccess databaseAccess;
    private EditText editName, editFirstName, editAge, editWeight, editHeight;
    private TextView viewName;
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

        // Initialiser les vues
       // editName = findViewById(R.id.edit_name);
       viewName = findViewById(R.id.edit_name);
        viewName.setText("Dieunel");
        editFirstName = findViewById(R.id.edit_firstname);
        editAge = findViewById(R.id.edit_age);
        editWeight = findViewById(R.id.edit_weight);
        editHeight = findViewById(R.id.edit_height);

        // Initialiser l'accès à la base de données
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        // Charger les informations utilisateur
        loadUserInfo();

        // Ajouter les listeners pour les champs de texte
        addTextWatchers();


    }

    private void loadUserInfo() {
        // Charger les informations de l'utilisateur depuis la base de données

        //a modifier
        int user_id = 1;

        String userLastName = databaseAccess.getUserLastName(user_id);
       // String userLastName = databaseAccess.getMsgMotivation(2);

        String userFirstName = databaseAccess.getUserFirstName(user_id);

        int userAge = databaseAccess.getUserAge(user_id);
        int userWeight = databaseAccess.getUserWeight(userAge);
        int userHeight = databaseAccess.getUserHeight(user_id);

        // Mettre à jour les champs EditText avec les informations récupérées
        editName.setText(userLastName);
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
        String query = "UPDATE identite SET " + column + " = ? WHERE id = 1";
        //modification a faire
        //databaseAccess.open();
        //SQLiteDatabase db = databaseAccess.getWritableDatabase();
        //db.execSQL(query, new String[]{value});
        databaseAccess.close(); // Ferme la connexion à la base de données
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseAccess.close();
    }


}