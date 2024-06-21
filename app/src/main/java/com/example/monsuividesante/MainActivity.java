package com.example.monsuividesante;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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

        final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
        db.open();

        /*Récupérer la liste de toute les durées
        *
        * ArrayList<String> lesDurees = db.getDuree();*/

        /*Récupérer la liste de toute les activités avec les calories dépensées associer
        *
        * HashMap<String, Float> activiteCalorie = db.getActiviteCalories();
        * Set<String> lesActivites = res.keySet();*/

        /*Récupérer le message de motivation d'identifiant id
        * int id = 4;
        * String msg_motivation = db.getMsgMotivation(id);
        * */
        db.close();
    }
}