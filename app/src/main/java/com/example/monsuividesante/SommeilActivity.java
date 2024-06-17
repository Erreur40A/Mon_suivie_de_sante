package com.example.monsuividesante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SommeilActivity extends AppCompatActivity {

    private ConstraintLayout toolbar;
    private ConstraintLayout objectif_sommeil;
    private LinearLayout pas;
    private LinearLayout mes_info;
    private LinearLayout calories;
    private ImageButton bouton_mes_info;
    private ImageButton bouton_pas;
    private ImageButton bouton_calories;
    private ImageButton bouton_sommeil;
    private ImageButton bouton_heure_coucher;
    private ImageButton bouton_heure_reveil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sommeil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_sommeil), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        objectif_sommeil = findViewById(R.id.objectif_sommeil);
        toolbar = findViewById(R.id.toolbar);
        pas = toolbar.findViewById(R.id.pas);
        mes_info = toolbar.findViewById(R.id.mes_info);
        calories = toolbar.findViewById(R.id.calories);

        pas.setAlpha(0.4F);
        mes_info.setAlpha(0.4F);
        calories.setAlpha(0.4F);

        bouton_mes_info = mes_info.findViewById(R.id.bouton_mes_info);
        bouton_mes_info.setOnClickListener(this::onClickListenerBoutonMesInfo);

        bouton_pas = pas.findViewById(R.id.bouton_pas);
        bouton_pas.setOnClickListener(this::onClickListenerBoutonPas);

        bouton_calories = calories.findViewById(R.id.bouton_calories);
        bouton_calories.setOnClickListener(this::onClickListenerBoutonCalories);

        bouton_sommeil = toolbar.findViewById(R.id.sommeil).findViewById(R.id.bouton_sommeil);
        bouton_sommeil.setOnClickListener(this::onClickListenerBoutonSommeil);

        bouton_heure_coucher = objectif_sommeil.findViewById(R.id.bouton_coucher);
        bouton_heure_coucher.setOnClickListener(this::onClickListenerHeureCoucher);

        bouton_heure_reveil = objectif_sommeil.findViewById(R.id.bouton_reveil);
        bouton_heure_reveil.setOnClickListener(this::onClickListenerHeureReveil);
    }

    public void onClickListenerHeureCoucher(View view){
        AlertDialog.Builder pop_up_objectif_coucher = new AlertDialog.Builder(this, R.style.PopUpArrondi);
        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_coucher);

        AlertDialog pop_up = pop_up_objectif_coucher.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        assert bouton_ok != null;
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            assert saisie != null;

            TextView heure_coucher = objectif_sommeil.findViewById(R.id.objectif_sommeil).findViewById(R.id.heure_coucher);
            String affichage = String.valueOf(saisie.getText());

            heure_coucher.setText(affichage);

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        assert bouton_annuler != null;
        bouton_annuler.setOnClickListener(v -> { pop_up.dismiss(); });
    }

    public void onClickListenerHeureReveil(View view){
        AlertDialog.Builder pop_up_objectif_coucher = new AlertDialog.Builder(this ,R.style.PopUpArrondi);
        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_reveil);

        AlertDialog pop_up = pop_up_objectif_coucher.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        assert bouton_ok != null;
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            assert saisie != null;

            TextView heure_reveil = objectif_sommeil.findViewById(R.id.objectif_sommeil).findViewById(R.id.heure_reveil);
            String affichage = String.valueOf(saisie.getText());

            heure_reveil.setText(affichage);

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        assert bouton_annuler != null;
        bouton_annuler.setOnClickListener(v -> { pop_up.dismiss(); });
    }

    public void onClickListenerBoutonMesInfo(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Mes informations)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickListenerBoutonCalories(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Calories)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        /*On pourra supprimer ce listener ou le garder*/
        Intent intent = new Intent(SommeilActivity.this, SommeilActivity.class);
        startActivity(intent);
    }
}