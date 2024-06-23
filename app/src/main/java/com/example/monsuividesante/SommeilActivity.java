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

    private String heure_coucher_prevue;
    private String heure_coucher_reel;
    private String heure_reveil_prevue;
    private String heure_reveil_reel;

    private ConstraintLayout objectif_sommeil;
    private TextView msg_motivation;

    //private Utilisateur user;

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

        //user = getIntent().getSerializableExtra("user");

        msg_motivation = findViewById(R.id.msg_motivation).findViewById(R.id.msg_motiv);
        objectif_sommeil = findViewById(R.id.objectif_sommeil);
        ConstraintLayout toolbar = findViewById(R.id.toolbar);
        LinearLayout pas = toolbar.findViewById(R.id.pas);
        LinearLayout mes_info = toolbar.findViewById(R.id.mes_info);
        LinearLayout calories = toolbar.findViewById(R.id.calories);

        pas.setAlpha(0.4F);
        mes_info.setAlpha(0.4F);
        calories.setAlpha(0.4F);

        ImageButton bouton_mes_info = mes_info.findViewById(R.id.bouton_mes_info);
        bouton_mes_info.setOnClickListener(this::onClickListenerBoutonMesInfo);

        ImageButton bouton_pas = pas.findViewById(R.id.bouton_pas);
        bouton_pas.setOnClickListener(this::onClickListenerBoutonPas);

        ImageButton bouton_calories = calories.findViewById(R.id.bouton_calories);
        bouton_calories.setOnClickListener(this::onClickListenerBoutonCalories);

        ImageButton bouton_sommeil = toolbar.findViewById(R.id.sommeil).findViewById(R.id.bouton_sommeil);
        bouton_sommeil.setOnClickListener(this::onClickListenerBoutonSommeil);

        ImageButton bouton_heure_coucher = objectif_sommeil.findViewById(R.id.bouton_coucher);
        bouton_heure_coucher.setOnClickListener(this::onClickListenerHeureCoucher);

        ImageButton bouton_heure_reveil = objectif_sommeil.findViewById(R.id.bouton_reveil);
        bouton_heure_reveil.setOnClickListener(this::onClickListenerHeureReveil);

        ImageButton bouton_heure_coucher_reel = objectif_sommeil.findViewById(R.id.bouton_coucher_reel);
        bouton_heure_coucher_reel.setOnClickListener(this::onClickListenerHeureCoucherReel);

        ImageButton bouton_heure_reveil_reel = objectif_sommeil.findViewById(R.id.bouton_reveil_reel);
        bouton_heure_reveil_reel.setOnClickListener(this::onClickListenerHeureReveilReel);
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

            if(Regex.estHeureValide(affichage)){
                String[] horaire = affichage.split(":");

                Rappel.setRappel(this, Integer.parseInt(horaire[0]), Integer.parseInt(horaire[1]));
                heure_coucher.setText(affichage);
            }

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

            if(Regex.estHeureValide(affichage)){
                heure_reveil.setText(affichage);
            }

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        assert bouton_annuler != null;
        bouton_annuler.setOnClickListener(v -> { pop_up.dismiss(); });
    }

    public void onClickListenerHeureCoucherReel(View view){
        AlertDialog.Builder pop_up_objectif_coucher = new AlertDialog.Builder(this ,R.style.PopUpArrondi);
        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_coucher_reel);

        AlertDialog pop_up = pop_up_objectif_coucher.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        assert bouton_ok != null;
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            assert saisie != null;

            TextView heure_coucher = objectif_sommeil.findViewById(R.id.objectif_sommeil).findViewById(R.id.heure_coucher_reel);
            String affichage = String.valueOf(saisie.getText());

            if(Regex.estHeureValide(affichage)){
                heure_coucher.setText(affichage);
            }

            /*Mettre 'affichage' dans la DB*/

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        assert bouton_annuler != null;
        bouton_annuler.setOnClickListener(v -> { pop_up.dismiss(); });
    }

    public void onClickListenerHeureReveilReel(View view){
        AlertDialog.Builder pop_up_objectif_coucher = new AlertDialog.Builder(this ,R.style.PopUpArrondi);
        pop_up_objectif_coucher.setView(R.layout.pop_up_heure_reveil_reel);

        AlertDialog pop_up = pop_up_objectif_coucher.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        assert bouton_ok != null;
        bouton_ok.setOnClickListener(v -> {
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            assert saisie != null;

            TextView heure_reveil = objectif_sommeil.findViewById(R.id.objectif_sommeil).findViewById(R.id.heure_reveil_reel);
            String affichage = String.valueOf(saisie.getText());

            if(Regex.estHeureValide(affichage)){
                heure_reveil.setText(affichage);
            }

            /*Mettre 'affichage' dans la DB*/

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        assert bouton_annuler != null;
        bouton_annuler.setOnClickListener(v -> { pop_up.dismiss(); });
    }

    public void onClickListenerBoutonMesInfo(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Mes informations)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonCalories(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Calories)*/
        Intent intent = new Intent(SommeilActivity.this, MainActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        /*On pourra supprimer ce listener ou le garder*/
        Intent intent = new Intent(SommeilActivity.this, SommeilActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }
}