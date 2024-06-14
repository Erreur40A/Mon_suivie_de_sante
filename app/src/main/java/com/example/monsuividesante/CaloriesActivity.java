package com.example.monsuividesante;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CaloriesActivity extends AppCompatActivity {

    private String calories_depense; //en kcal a afficher dans le rectangle vert
    private String calories_depense_reel; //en kcal a afficher dans le rectangle rouge
    private ConstraintLayout toolar;
    private ImageButton bouton_edit_calories_consome;
    private Spinner liste_deroulante_choix_activite;
    private Spinner liste_deroulante_duree_activite;
    private Button bouton_calories_consome_ok;
    private Button bouton_activite_ok;
    private Button mes_info;
    private Button pas;
    private Button calories;
    private Button sommeil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_calories), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tmp;
        tmp = findViewById(R.id.calories_depense).findViewById(R.id.text_calorie_depense);
        calories_depense = tmp.getText().toString();

        tmp=findViewById(R.id.calories_depense_reel).findViewById(R.id.calories_depense_reel);
        calories_depense = tmp.getText().toString();

        toolar = findViewById(R.id.toolbar);

        bouton_edit_calories_consome = findViewById(R.id.demande_calorie_comsomme)
                                       .findViewById(R.id.entrer_calorie_consomme)
                                       .findViewById(R.id.bouton_modifications);


    }
}