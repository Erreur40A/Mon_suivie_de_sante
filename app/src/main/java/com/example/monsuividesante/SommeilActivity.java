package com.example.monsuividesante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
    private SommeilActivity activity_sommeil;
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

        activity_sommeil = this;
        objectif_sommeil = findViewById(R.id.objectif_sommeil);
        toolbar = findViewById(R.id.toolbar);

        bouton_mes_info = toolbar.findViewById(R.id.mes_info).findViewById(R.id.bouton_mes_info);
        bouton_mes_info.setOnClickListener(this::setOnClickListenerBoutonMesInfo);

        bouton_pas = toolbar.findViewById(R.id.pas).findViewById(R.id.bouton_pas);
        bouton_pas.setOnClickListener(this::setOnClickListenerBoutonPas);

        bouton_calories = toolbar.findViewById(R.id.calories).findViewById(R.id.bouton_calories);
        bouton_calories.setOnClickListener(this::setOnClickListenerBoutonCalories);

        bouton_sommeil = toolbar.findViewById(R.id.sommeil).findViewById(R.id.bouton_sommeil);
        bouton_sommeil.setOnClickListener(this::setOnClickListenerBoutonSommeil);

        bouton_heure_coucher = objectif_sommeil.findViewById(R.id.bouton_coucher);
        bouton_heure_coucher.setOnClickListener(this::setOnClickListenerBoutonHeureCoucher);

        bouton_heure_reveil = objectif_sommeil.findViewById(R.id.bouton_reveil);
        bouton_heure_reveil.setOnClickListener(this::setOnClickListenerBoutonHeureReveil);
    }

    public void setOnClickListenerBoutonHeureCoucher(View view){
        AlertDialog.Builder pop_up_objectif_coucher = new AlertDialog.Builder(activity_sommeil, R.style.PopUpArrondi);
        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_coucher);

        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_coucher);

        AlertDialog pop_up = pop_up_objectif_coucher.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        assert bouton_ok != null;
        bouton_ok.setOnClickListener(v -> {
            /*A compléter*/

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        assert bouton_annuler != null;
        bouton_annuler.setOnClickListener(v -> {
            /*A compléter*/

            pop_up.dismiss();
        });
    }

    public void setOnClickListenerBoutonHeureReveil(View view){
        AlertDialog.Builder pop_up_objectif_coucher = new AlertDialog.Builder(activity_sommeil ,R.style.PopUpArrondi);
        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_reveil);

        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_reveil);

        AlertDialog pop_up = pop_up_objectif_coucher.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        assert bouton_ok != null;
        bouton_ok.setOnClickListener(v -> {
            /*A compléter*/

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        assert bouton_annuler != null;
        bouton_annuler.setOnClickListener(v -> {
            /*A compléter*/

            pop_up.dismiss();
        });
    }

    public void setOnClickListenerBoutonMesInfo(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Mes informations)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setOnClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setOnClickListenerBoutonCalories(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Calories)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setOnClickListenerBoutonSommeil(View view){
        Intent intent = new Intent(SommeilActivity.this, SommeilActivity.class);
        startActivity(intent);
    }
}