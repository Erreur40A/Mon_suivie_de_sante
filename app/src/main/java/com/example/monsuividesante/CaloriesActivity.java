package com.example.monsuividesante;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
    private CaloriesActivity activity_calories;
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

        pop_up_duree_activite = null;
        pop_up_choix_activite = null;
        activity_calories = this;

        TextView tmp_textView;
        tmp_textView = findViewById(R.id.calories_depense).findViewById(R.id.text_calorie_depense);
        calories_depense = tmp_textView.getText().toString();

        tmp_textView=findViewById(R.id.calories_depense_reel).findViewById(R.id.text_calorie_depense_reel);
        calories_depense = tmp_textView.getText().toString();

        toolar = findViewById(R.id.toolbar);

        pas = toolar.findViewById(R.id.pas);
        mes_info = toolar.findViewById(R.id.mes_info);
        sommeil = toolar.findViewById(R.id.sommeil);

        pas.setAlpha(0.4F);
        mes_info.setAlpha(0.4F);
        sommeil.setAlpha(0.4F);

        ConstraintLayout tmp_CL = findViewById(R.id.demande_calorie_consomme);

        ConstraintLayout enter_calorie_consomme = tmp_CL.findViewById(R.id.entrer_calorie_consomme);
        bouton_edit_calories_consomme = enter_calorie_consomme.findViewById(R.id.bouton_modifications);

        bouton_edit_calories_consomme.setOnClickListener(this::setOnClickListenerCaloriesConsomme);
        enter_calorie_consomme.setOnClickListener(this::setOnClickListenerCaloriesConsomme);

        bouton_calories_consome_ok = tmp_CL.findViewById(R.id.bouton_ok);
        bouton_calories_consome_ok.setOnClickListener(this::setOnClickListenerBoutonConsommeOK);

        tmp_CL = findViewById(R.id.demande_info_activite);
        bouton_activite_ok = tmp_CL.findViewById(R.id.bouton_ok_activite);
        bouton_activite_ok.setOnClickListener(this::setOnClickListenerBoutonActiviteOK);

        bouton_pas = pas.findViewById(R.id.bouton_pas);
        bouton_pas.setOnClickListener(this::setOnClickListenerBoutonPas);

        bouton_mes_info = mes_info.findViewById(R.id.bouton_mes_info);
        bouton_mes_info.setOnClickListener(this::setOnClickListenerBoutonMesInfo);

        bouton_calories = toolar.findViewById(R.id.calories).findViewById(R.id.bouton_calories);
        bouton_calories.setOnClickListener(this::setOnClickListenerBoutonCalorie);

        bouton_sommeil = sommeil.findViewById(R.id.bouton_sommeil);
        bouton_sommeil.setOnClickListener(this::setOnClickListenerBoutonSommeil);

        liste_deroulante_duree_activite = tmp_CL.findViewById(R.id.liste_deroulante_duree);
        bouton_liste_duree_activite = liste_deroulante_duree_activite.findViewById(R.id.bouton_liste_deroulante);
        /*La liste est à modifier
        Peut-être créer une classe Utilitaire qui récupère tout les durée présent dans la db*/
        items_duree_activite = new ArrayList<String>(Arrays.asList("00:15", "00:30", "1:00", "1:30", "2:00"));
        liste_deroulante_duree_activite.setOnClickListener(this::setOnClickListenerActiviteDuree);
        bouton_liste_duree_activite.setOnClickListener(this::setOnClickListenerActiviteDuree);

        liste_deroulante_choix_activite = tmp_CL.findViewById(R.id.liste_deroulante_activite);
        bouton_liste_choix_activite = liste_deroulante_choix_activite.findViewById(R.id.bouton_liste_deroulante);
        /*La liste est à modifier
        Peut-être créer une classe Utilitaire qui récupère tout les activité présent dans la db*/
        items_choix_activite = new ArrayList<String>(Arrays.asList("activité1", "activité2", "activité3", "activité4", "activité5"));
        liste_deroulante_choix_activite.setOnClickListener(this::setOnClickListenerChoixActivite);
        bouton_liste_choix_activite.setOnClickListener(this::setOnClickListenerChoixActivite);

    }

    public void setOnClickListenerCaloriesConsomme(View view){
        AlertDialog.Builder pop_up_calorie_consomme = new AlertDialog.Builder(activity_calories, R.style.PopUpArrondi);

        pop_up_calorie_consomme.setView(R.layout.pop_up_calorie);

        AlertDialog pop_up = pop_up_calorie_consomme.create();
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

    public void setOnClickListenerActiviteDuree(View view){
        AlertDialog.Builder pop_up_activite_dure_builder = new AlertDialog.Builder(activity_calories, R.style.PopUpArrondi);

        LayoutInflater inflater = getLayoutInflater();
        View view_pop_up = inflater.inflate(R.layout.pop_up_duree_activite, null);

        LinearLayout liste_deroulante = view_pop_up.findViewById(R.id.items_duree_activite_liste);

        for (String item : items_duree_activite) {
            View view_item = inflater.inflate(R.layout.background_item_liste_deroulante, null, false);

            TextView textViewItem = view_item.findViewById(R.id.item);
            textViewItem.setText(item);

            textViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnClickListenerItemListeDeroulante(view);

                    if(pop_up_duree_activite != null && pop_up_duree_activite.isShowing())
                        pop_up_duree_activite.dismiss();
                }
            });

            liste_deroulante.addView(view_item);
        }

        pop_up_duree_activite = pop_up_activite_dure_builder.create();

        pop_up_duree_activite.setView(view_pop_up);
        pop_up_duree_activite.show();
    }

    public void setOnClickListenerChoixActivite(View view){
        AlertDialog.Builder pop_up_activite_choix_builder = new AlertDialog.Builder(activity_calories, R.style.PopUpArrondi);

        LayoutInflater inflater = getLayoutInflater();
        View view_pop_up = inflater.inflate(R.layout.pop_up_choix_activite, null);

        LinearLayout liste_deroulante = view_pop_up.findViewById(R.id.items_choix_activite_liste);

        for (String item : items_choix_activite) {
            View view_item = inflater.inflate(R.layout.background_item_liste_deroulante, null, false);

            TextView textViewItem = view_item.findViewById(R.id.item);
            textViewItem.setText(item);

            textViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnClickListenerItemListeDeroulante(view);

                    if(pop_up_choix_activite != null && pop_up_choix_activite.isShowing())
                        pop_up_choix_activite.dismiss();
                }
            });

            liste_deroulante.addView(view_item);
        }

        pop_up_choix_activite = pop_up_activite_choix_builder.create();

        pop_up_choix_activite.setView(view_pop_up);
        pop_up_choix_activite.show();
    }

    public void setOnClickListenerItemListeDeroulante(View view){
        Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
    }

    public void setOnClickListenerBoutonPas(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(CaloriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setOnClickListenerBoutonCalorie(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(CaloriesActivity.this, CaloriesActivity.class);
        startActivity(intent);
    }

    public void setOnClickListenerBoutonMesInfo(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(CaloriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setOnClickListenerBoutonSommeil(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Pas)*/
        Intent intent = new Intent(CaloriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setOnClickListenerBoutonConsommeOK(View view){
        /*A MODIFIER*/
        Toast.makeText(getApplicationContext(), "bouton ok consomme clique", Toast.LENGTH_SHORT).show();
    }

    public void setOnClickListenerBoutonActiviteOK(View view){
        /*A MODIFIER*/
        Toast.makeText(getApplicationContext(), "bouton ok activite clique", Toast.LENGTH_SHORT).show();
    }

    public void setOnSelectedItemSpinner(View view){
        Toast.makeText(getApplicationContext(), "bouton ok activite clique", Toast.LENGTH_SHORT).show();
    }
}