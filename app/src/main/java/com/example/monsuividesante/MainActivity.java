package com.example.monsuividesante;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();

        /* Ajout ligne dans table activites_calories*/

        /*try (DatabaseOpenhelper db_helper = new DatabaseOpenhelper(this)) {
            db_helper.deleteApportEnEnergie();
            db_helper.addLigneActiviteCalorie("userTest");
        } catch (Exception e) {
            Log.e("MainActivity", "addLigneActiviteCalorie de DatabaseOpenhelper", e);
        }*/

        /*Mettre a jour les calorie consommé/depense

        String date = db.getDateApportEnEnergie("userTest");

        try (DatabaseOpenhelper db_helper = new DatabaseOpenhelper(this)) {
            db_helper.updateCalorieDepense(5F, date);
            db_helper.updateCalorieConsomme(5F, date);
        } catch (Exception e) {
            Log.e("MainActivity", "addLigneActiviteCalorie de DatabaseOpenhelper", e);
        }*/

        /* Récuperer la date la plus récente de l'utilisateur d'identifiant userTest
         *
         * String res = db.getDateApportEnEnergie("userTest");
         */

        /* Récuperer les calories consomme/depenser de l'utilisateur d'identifiant userTest
         * float res = db.getCalorieConsomme("userTest");
         * float res = db.getCalorieDepense("userTest");
         */

        db.close();
    }
}