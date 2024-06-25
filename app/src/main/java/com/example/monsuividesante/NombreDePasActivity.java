package com.example.monsuividesante;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;

import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Random;

public class NombreDePasActivity extends AppCompatActivity {

    private TextView pas_journalier_fait, pas_hebdomadaire_fait, pas_mensuelle_fait;
    private TextView objectif_journalier, objectif_hebdomadaire, objectif_mensuelle;
    private TextView pourcent_journaier, pourcent_hebdomadaire, pourcent_mensuelle, msg_motivation;
    private ImageButton bouton_journalier, bouton_hebdomadaire, bouton_mensuelle;
    private int pourcentage_journalier, pourcentage_hebdomadaire, pourcentage_mensuelle;
    private int mois, annee;

    private static final String PAS_HEBDOMADAIRE = "pas_hebdo";
    private static final String PAS_JOURNALIERS = "pas_journaliers";
    private static final String PAS_MENSUELS = "pas_mensuels";


    private ProgressBar bar_journalier, bar_mensuelle, bar_hebdomadaire;

    //temporaire
    private final int user_id=1;

    private DatabaseAccess db;
    private DatabaseOpenhelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nombre_de_pas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_nb_pas), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = DatabaseAccess.getInstance(this);
        db_helper = new DatabaseOpenhelper(this);

        /*-----------Temporaire----------*/
        db_helper.deletePasHebdomadaire();
        db_helper.addLignePasHebdomadaire(user_id);
        db_helper.deletePasJournalier();
        db_helper.addLignePasJournaliers(user_id);
        db_helper.deletePasMensuelle();
        db_helper.addLignePasMensuelle(user_id);
        /*-------------------------------*/

        Random random = new Random();
        msg_motivation = findViewById(R.id.motivation).findViewById(R.id.textMotivation);
        db.open();
        //il y a 19 msg entre 1 et 20
        msg_motivation.setText(db.getMsgMotivation(random.nextInt(20) + 1));
        db.close();

        ConstraintLayout toolbar = findViewById(R.id.toolbar);
        ImageButton pas = toolbar.findViewById(R.id.pas).findViewById(R.id.bouton_pas);
        ImageButton calories = toolbar.findViewById(R.id.calories).findViewById(R.id.bouton_calories);
        ImageButton mes_info = toolbar.findViewById(R.id.mes_info).findViewById(R.id.bouton_mes_info);
        ImageButton sommeil = toolbar.findViewById(R.id.sommeil).findViewById(R.id.bouton_sommeil);

        pas.setOnClickListener(this::onClickListenerBoutonPas);
        mes_info.setOnClickListener(this::onClickListenerBoutonMesInfo);
        calories.setOnClickListener(this::onClickListenerBoutonCalorie);
        sommeil.setOnClickListener(this::onClickListenerBoutonSommeil);

        ConstraintLayout journ = findViewById(R.id.objectif_journalier).findViewById(R.id.rec);
        ConstraintLayout hebd = findViewById(R.id.objectif_hebdomadaire).findViewById(R.id.rec1);
        ConstraintLayout mens = findViewById(R.id.objectif_mensuelle).findViewById(R.id.rec2);

        bouton_journalier = journ.findViewById(R.id.bouton_journalier);
        bouton_hebdomadaire = hebd.findViewById(R.id.bouton_hebdomadaire);
        bouton_mensuelle = mens.findViewById(R.id.bouton_mensuelle);

        objectif_journalier = journ.findViewById(R.id.val_objectif_journalier);
        objectif_hebdomadaire = hebd.findViewById(R.id.val_objectif_hebdomadaire);
        objectif_mensuelle = mens.findViewById(R.id.val_objectif_mensuelle);

        pas_journalier_fait = findViewById(R.id.objectif_journalier).findViewById(R.id.nb_pas_journalier);
        pas_hebdomadaire_fait = findViewById(R.id.objectif_hebdomadaire).findViewById(R.id.nb_pas_hebdomadaire);
        pas_mensuelle_fait = findViewById(R.id.objectif_mensuelle).findViewById(R.id.nb_pas_mensuelle);

        bouton_journalier.setOnClickListener(this::onClickListenerObjectifJournalier);
        bouton_mensuelle.setOnClickListener(this::onClickListenerObjectifMensuelle);
        bouton_hebdomadaire.setOnClickListener(this::onClickListenerObjectifHebdomadaire);

        bar_journalier = findViewById(R.id.objectif_journalier).findViewById(R.id.progressBarJour);
        bar_hebdomadaire = findViewById(R.id.objectif_hebdomadaire).findViewById(R.id.progresshebdo);
        bar_mensuelle = findViewById(R.id.objectif_mensuelle).findViewById(R.id.progressmensuel);

        pourcent_hebdomadaire = findViewById(R.id.objectif_hebdomadaire).findViewById(R.id.progresstexthebd);
        pourcent_journaier = findViewById(R.id.objectif_journalier).findViewById(R.id.progressTextjour);
        pourcent_mensuelle = findViewById(R.id.objectif_mensuelle).findViewById(R.id.progressTextmens);

        /*Calcule des pourcentage :
        *
        * nombre_pas_effectue / objetcif_pas * 100
        *
        * Ensuite on cast le résultat en int et on met à jour la barre de progression le textView pourcent
        * */

        db.open();
        db_helper.updateNombrePasJournalier(user_id, db.getDateJournalier(user_id), 20);
        db_helper.updateNombrePasHebdomadaire(user_id, db.getSemaineHebdomadaire(user_id), 5, 60);
        db_helper.updateNombrePasMensuelle(user_id, db.getMoisMensuelle(user_id), 2024, 100);

        int j = db.getPas(user_id, PAS_JOURNALIERS);
        int h = db.getPas(user_id, PAS_HEBDOMADAIRE);;
        int m = db.getPas(user_id, PAS_MENSUELS);;
        db.close();
        setCouleurProgressBar(bar_hebdomadaire, j);
        setCouleurProgressBar(bar_mensuelle, h);
        setCouleurProgressBar(bar_journalier, m);

        pourcent_mensuelle.setText(j+"%");
        pourcent_journaier.setText(h+"%");
        pourcent_hebdomadaire.setText(m+"%");
    }


    public void setCouleurProgressBar(ProgressBar progressBar, int pourcentage){
        if(pourcentage<0 || pourcentage>100) return;

        Drawable barre_progression;

        if(pourcentage<36)
            barre_progression = ContextCompat.getDrawable(this, R.drawable.progress);
        else if (pourcentage<66)
            barre_progression = ContextCompat.getDrawable(this, R.drawable.progress1);
        else
            barre_progression = ContextCompat.getDrawable(this, R.drawable.progress2);

        progressBar.setProgress(pourcentage);
        progressBar.setProgressDrawable(barre_progression);
    }

    public void onClickListenerObjectifJournalier(View view){
        AlertDialog.Builder pop_up_objectif_journalier = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_objectif_journalier.setView(R.layout.pop_up_obj_jour);

        AlertDialog pop_up = pop_up_objectif_journalier.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            TextView choix = objectif_journalier.findViewById(R.id.val_objectif_journalier);
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            choix.setText(affichage);
            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    public void onClickListenerObjectifHebdomadaire(View view){
        AlertDialog.Builder pop_up_objectif_hedomadaire = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_objectif_hedomadaire.setView(R.layout.pop_up_obj_hebd);

        AlertDialog pop_up = pop_up_objectif_hedomadaire.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            TextView choix = objectif_hebdomadaire.findViewById(R.id.val_objectif_hebdomadaire);
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            choix.setText(affichage);
            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    public void onClickListenerObjectifMensuelle(View view){
        AlertDialog.Builder pop_up_objectif_mensuelle = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_objectif_mensuelle.setView(R.layout.pop_up_obj_mens);

        AlertDialog pop_up = pop_up_objectif_mensuelle.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            TextView choix = objectif_mensuelle.findViewById(R.id.val_objectif_mensuelle);
            EditText saisie = pop_up.findViewById(R.id.saisie_user);
            String affichage = saisie.getText().toString();

            choix.setText(affichage);
            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    public void onClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(NombreDePasActivity.this, NombreDePasActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonCalorie(View view){
        /*Soit on supprime ce listener soit on le garde*/
        Intent intent = new Intent(NombreDePasActivity.this, MainActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonMesInfo(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Mes informations)*/
        Intent intent = new Intent(NombreDePasActivity.this, MainActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Sommeil)*/
        Intent intent = new Intent(NombreDePasActivity.this, MainActivity.class);
        //intent.putExtra("user", user);
        startActivity(intent);
    }
}
