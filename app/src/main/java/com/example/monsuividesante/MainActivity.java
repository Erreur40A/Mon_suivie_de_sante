package com.example.monsuividesante;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    private TextView textPasFaitJournalier;
    private TextView editHebdo;
    private TextView editMensuel;

    private int objectif1 = 5000;
    private int objectif2 = 10000;
    private int objectif3 = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_de_pas);

        textPasFaitJournalier = findViewById(R.id.textPasFaitJournalier);
        editHebdo = findViewById(R.id.editHebdo);
        editMensuel = findViewById(R.id.editmensuel);

        updateObjectifsTextView();

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulation de nouvelles valeurs d'objectifs
                objectif1 = 6000;
                objectif2 = 12000;
                objectif3 = 18000;

                updateObjectifsTextView();
            }
        });

        // Simulation de mise à jour à partir de données de capteurs (exemple : nombre de pas)
        simulateSensorUpdate();
    }

    private void updateObjectifsTextView() {
        textPasFaitJournalier.setText("Objectif 1 : " + objectif1);
        editHebdo.setText("Objectif 2 : " + objectif2);
        editMensuel.setText("Objectif 3 : " + objectif3);
    }

    // Simulation de mise à jour à partir de données de capteurs (par exemple, nombre de pas)
    private void simulateSensorUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) { // Simule 20 mises à jour de capteurs
                        Thread.sleep(2000); // Attente de 2 secondes (simulant la mise à jour)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Simulation des progrès actuels
                                int pasActuels = i * 500; // Exemple : chaque itération représente 500 pas

                                // Mettre à jour les objectifs avec les progrès actuels
                                textPasFaitJournalier.setText("Objectif 1 : " + (objectif1 - pasActuels));
                                editHebdo.setText("Objectif 2 : " + (objectif2 - pasActuels));
                                editMensuel.setText("Objectif 3 : " + (objectif3 - pasActuels));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}