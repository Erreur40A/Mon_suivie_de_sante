package com.example.monsuividesante;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class CaloriesActivity extends AppCompatActivity {

    private String calories_depense; //en kcal a afficher dans le rectangle vert
    private String calories_depense_reel; //en kcal a afficher dans le rectangle rouge
    private ConstraintLayout toolar;
    private ConstraintLayout entrer_calorie_consomme;
    private ConstraintLayout liste_deroulante_choix_activite;
    private ArrayList<String> items_choix_activite;
    private AlertDialog pop_up_choix_activite;
    private ConstraintLayout liste_deroulante_duree_activite;
    private ArrayList<String> items_duree_activite;
    private AlertDialog pop_up_duree_activite;
    private LinearLayout pas;
    private LinearLayout mes_info;
    private LinearLayout sommeil;
    private Button bouton_calories_consome_ok;
    private Button bouton_activite_ok;
    private ImageButton bouton_edit_calories_consomme;
    private ImageButton bouton_liste_choix_activite;
    private ImageButton bouton_liste_duree_activite;
    private ImageButton bouton_explication;
    private ImageButton bouton_mes_info;
    private ImageButton bouton_pas;
    private ImageButton bouton_calories;
    private ImageButton bouton_sommeil;

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

        TextView textViewCalDep = findViewById(R.id.calories_depense).findViewById(R.id.val_calories_depense).findViewById(R.id.text_calorie_depense_reel);
        calories_depense = "300 " + textViewCalDep.getText().toString();
        textViewCalDep.setText(calories_depense);

        TextView textViewCalDepReel = findViewById(R.id.calories_depense_reel).findViewById(R.id.val_calories_depense).findViewById(R.id.text_calorie_depense_reel);
        calories_depense_reel = "100 " + textViewCalDepReel.getText().toString();
        textViewCalDepReel.setText(calories_depense_reel);

        bouton_explication=findViewById(R.id.calories_depense_reel).findViewById(R.id.bouton_explication);
        bouton_explication.setOnClickListener(this::onClickListenerBoutonExplication);

        toolar = findViewById(R.id.toolbar);

        pas = toolar.findViewById(R.id.pas);
        mes_info = toolar.findViewById(R.id.mes_info);
        sommeil = toolar.findViewById(R.id.sommeil);

        ConstraintLayout tmp_CL = findViewById(R.id.demande_calorie_consomme);

        entrer_calorie_consomme = tmp_CL.findViewById(R.id.entrer_calorie_consomme);
        bouton_edit_calories_consomme = entrer_calorie_consomme.findViewById(R.id.bouton_modifications);

        bouton_edit_calories_consomme.setOnClickListener(this::onClickListenerCaloriesConsomme);
        entrer_calorie_consomme.setOnClickListener(this::onClickListenerCaloriesConsomme);

        bouton_calories_consome_ok = tmp_CL.findViewById(R.id.bouton_ok);
        bouton_calories_consome_ok.setOnClickListener(this::onClickListenerBoutonConsommeOK);

        tmp_CL = findViewById(R.id.demande_info_activite);
        bouton_activite_ok = tmp_CL.findViewById(R.id.bouton_ok_activite);
        bouton_activite_ok.setOnClickListener(this::onClickListenerBoutonActiviteOK);

        bouton_pas = pas.findViewById(R.id.bouton_pas);
        bouton_pas.setOnClickListener(this::onClickListenerBoutonPas);

        bouton_mes_info = mes_info.findViewById(R.id.bouton_mes_info);
        bouton_mes_info.setOnClickListener(this::onClickListenerBoutonMesInfo);

        bouton_calories = toolar.findViewById(R.id.calories).findViewById(R.id.bouton_calories);
        bouton_calories.setOnClickListener(this::onClickListenerBoutonCalorie);

        bouton_sommeil = sommeil.findViewById(R.id.bouton_sommeil);
        bouton_sommeil.setOnClickListener(this::onClickListenerBoutonSommeil);

        liste_deroulante_duree_activite = tmp_CL.findViewById(R.id.liste_deroulante_duree);
        bouton_liste_duree_activite = liste_deroulante_duree_activite.findViewById(R.id.bouton_liste_deroulante);

        /*La liste est à modifier*/
        items_duree_activite = new ArrayList<String>(Arrays.asList("00:15", "00:30", "1:00", "1:30", "2:00"));
        liste_deroulante_duree_activite.setOnClickListener(this::onClickListenerActiviteDuree);
        bouton_liste_duree_activite.setOnClickListener(this::onClickListenerActiviteDuree);

        liste_deroulante_choix_activite = tmp_CL.findViewById(R.id.liste_deroulante_activite);
        bouton_liste_choix_activite = liste_deroulante_choix_activite.findViewById(R.id.bouton_liste_deroulante);

        /*La liste est à modifier*/
        items_choix_activite = new ArrayList<String>(Arrays.asList("activité1", "activité2", "activité3", "activité4", "activité5"));
        liste_deroulante_choix_activite.setOnClickListener(this::onClickListenerChoixActivite);

        bouton_liste_choix_activite.setOnClickListener(this::onClickListenerChoixActivite);
    }

    public void onClickListenerBoutonExplication(View view){
        AlertDialog.Builder pop_up_explication = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_explication.setView(R.layout.pop_up_explication);

        AlertDialog pop_up = pop_up_explication.create();
        pop_up.show();

        TextView textExplication = pop_up.findViewById(R.id.text_pop_up_explication);

        String explication = "Si vous dépensez plus de " + calories_depense_reel + " vous allez maigrir.\nSi vous dépensez moins de " + calories_depense_reel + " vous allez grossir";
        if(textExplication!=null)
            textExplication.setText(explication);
    }

    public void onClickListenerCaloriesConsomme(View view){
        AlertDialog.Builder pop_up_calorie_consomme = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        pop_up_calorie_consomme.setView(R.layout.pop_up_calorie);

        AlertDialog pop_up = pop_up_calorie_consomme.create();
        pop_up.show();

        Button bouton_ok = pop_up.findViewById(R.id.bouton_ok);
        bouton_ok.setOnClickListener(v -> {
            TextView choix = entrer_calorie_consomme.findViewById(R.id.calorie_consome);
            EditText saisie = pop_up.findViewById(R.id.saisie_user);

            String affichage = saisie.getText() + " kcal";

            choix.setText(affichage);
            choix.setTextColor(Color.BLACK);

            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> { pop_up.dismiss(); });
    }

    public void onClickListenerActiviteDuree(View view){
        AlertDialog.Builder pop_up_activite_dure_builder = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        LayoutInflater inflater = getLayoutInflater();
        View view_pop_up = inflater.inflate(R.layout.pop_up_duree_activite, null);

        LinearLayout liste_deroulante = view_pop_up.findViewById(R.id.liste_duree_activite);

        for (String item : items_duree_activite) {
            View view_item = inflater.inflate(R.layout.background_item_liste_deroulante, liste_deroulante, false);

            TextView textViewItem = view_item.findViewById(R.id.item);
            textViewItem.setText(item);

            textViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListenerItemListeDeroulante(view, liste_deroulante_duree_activite);

                    pop_up_duree_activite.dismiss();
                }
            });

            liste_deroulante.addView(view_item);
        }

        pop_up_duree_activite = pop_up_activite_dure_builder.create();

        pop_up_duree_activite.setView(view_pop_up);
        pop_up_duree_activite.show();
    }

    public void onClickListenerChoixActivite(View view){
        AlertDialog.Builder pop_up_activite_choix_builder = new AlertDialog.Builder(this, R.style.PopUpArrondi);

        LayoutInflater inflater = getLayoutInflater();
        View view_pop_up = inflater.inflate(R.layout.pop_up_choix_activite, null);

        LinearLayout liste_deroulante = view_pop_up.findViewById(R.id.liste_choix_activite);

        for (String item : items_choix_activite) {
            View view_item = inflater.inflate(R.layout.background_item_liste_deroulante, liste_deroulante, false);

            TextView textViewItem = view_item.findViewById(R.id.item);
            textViewItem.setText(item);

            textViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListenerItemListeDeroulante(view, liste_deroulante_choix_activite);

                    pop_up_choix_activite.dismiss();
                }
            });

            liste_deroulante.addView(view_item);
        }

        pop_up_choix_activite = pop_up_activite_choix_builder.create();

        pop_up_choix_activite.setView(view_pop_up);
        pop_up_choix_activite.show();
    }

    public void onClickListenerItemListeDeroulante(View view, ViewGroup liste_deroulante){
        String choix = (String) ((TextView) view).getText();

        TextView explication = liste_deroulante.findViewById(R.id.explication_liste_deroulante);

        explication.setText(choix);
        explication.setTextColor(Color.BLACK);
    }

    public void onClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(CaloriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickListenerBoutonCalorie(View view){
        /*Soit on supprime ce listener soit on le garde*/
        Intent intent = new Intent(CaloriesActivity.this, CaloriesActivity.class);
        startActivity(intent);
    }

    public void onClickListenerBoutonMesInfo(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Mes informations)*/
        Intent intent = new Intent(CaloriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Sommeil)*/
        Intent intent = new Intent(CaloriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickListenerBoutonConsommeOK(View view){
        /*A MODIFIER*/
        Toast.makeText(getApplicationContext(), "bouton ok consomme clique", Toast.LENGTH_SHORT).show();
    }

    public void onClickListenerBoutonActiviteOK(View view){
        /*A MODIFIER*/
        Toast.makeText(getApplicationContext(), "bouton ok activite clique", Toast.LENGTH_SHORT).show();
    }
}