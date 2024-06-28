package com.example.monsuividesante;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class NombreDePasActivity extends AppCompatActivity implements SensorEventListener {

    //a utiliser pour mettre a jour le nb de pas avec le capteur
    private TextView pas_journalier_textView, pas_hebdomadaire_textView, pas_mensuelle_textView;

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private TextView objectif_journalier, objectif_hebdomadaire, objectif_mensuelle;
    private TextView pourcent_journalier, pourcent_hebdomadaire, pourcent_mensuelle;
    private ProgressBar bar_journalier, bar_mensuelle, bar_hebdomadaire;
    private int pourcentage_journalier, pourcentage_hebdomadaire, pourcentage_mensuelle;
    private int pas_journalier_fait, pas_hebdomadaire_fait, pas_mensuelle_fait;
    private int pas_journalier_objectif, pas_hebdomadaire_objectif, pas_mensuelle_objectif;
    private int compteur = 0;
    private String date;
    private int semaine;
    private int mois;
    private Runnable runner;
    private Handler handler;
    private final long delai = 5000; //quand ca va marcher on remplace 5000 par 60000

    private User user;

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

        user = (User) getIntent().getSerializableExtra("user");

        db = DatabaseAccess.getInstance(this);
        db_helper = new DatabaseOpenhelper(this);

        setNouveauObjectif();
        setMsgMotivation();
        setToolBar();

        ConstraintLayout layout_journalier = findViewById(R.id.objectif_journalier);
        ConstraintLayout layout_hebdomadaire = findViewById(R.id.objectif_hebdomadaire);
        ConstraintLayout layout_mensuelle = findViewById(R.id.objectif_mensuelle);

        bar_journalier = layout_journalier.findViewById(R.id.progressBarJour);
        bar_hebdomadaire = layout_hebdomadaire.findViewById(R.id.progresshebdo);
        bar_mensuelle = layout_mensuelle.findViewById(R.id.progressmensuel);

        pourcent_hebdomadaire = layout_hebdomadaire.findViewById(R.id.progresstexthebd);
        pourcent_journalier = layout_journalier.findViewById(R.id.progressTextjour);
        pourcent_mensuelle = layout_mensuelle.findViewById(R.id.progressTextmens);

        pas_journalier_textView = layout_journalier.findViewById(R.id.nb_pas_journalier);
        pas_hebdomadaire_textView = layout_hebdomadaire.findViewById(R.id.nb_pas_hebdomadaire);
        pas_mensuelle_textView = layout_mensuelle.findViewById(R.id.nb_pas_mensuelle);

        ConstraintLayout journ = layout_journalier.findViewById(R.id.rec);
        ConstraintLayout hebd = layout_hebdomadaire.findViewById(R.id.rec1);
        ConstraintLayout mens = layout_mensuelle.findViewById(R.id.rec2);

        ImageButton bouton_journalier = journ.findViewById(R.id.bouton_journalier);
        ImageButton bouton_hebdomadaire = hebd.findViewById(R.id.bouton_hebdomadaire);
        ImageButton bouton_mensuelle = mens.findViewById(R.id.bouton_mensuelle);

        objectif_journalier = journ.findViewById(R.id.val_objectif_journalier);
        objectif_hebdomadaire = hebd.findViewById(R.id.val_objectif_hebdomadaire);
        objectif_mensuelle = mens.findViewById(R.id.val_objectif_mensuelle);

        bouton_journalier.setOnClickListener(this::onClickListenerObjectifJournalier);
        bouton_mensuelle.setOnClickListener(this::onClickListenerObjectifMensuelle);
        bouton_hebdomadaire.setOnClickListener(this::onClickListenerObjectifHebdomadaire);

        setObjectif();

        pourcentage_hebdomadaire=setProgressBar(bar_hebdomadaire, pas_hebdomadaire_fait, pas_hebdomadaire_objectif);
        pourcentage_mensuelle=setProgressBar(bar_mensuelle, pas_mensuelle_fait, pas_mensuelle_objectif);
        pourcentage_journalier=setProgressBar(bar_journalier, pas_journalier_fait, pas_journalier_objectif);

        setTextViewPourcentage();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounterSensor == null) {
            String msg="Le capteur de pas n'est pas disponible";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            TextView txtJour = (TextView) layout_journalier.findViewById(R.id.textPasFaitJournalier);
            TextView txtHebd = (TextView) layout_hebdomadaire.findViewById(R.id.editHebdo);
            TextView txtMens = (TextView) layout_mensuelle.findViewById(R.id.editmensuel);

            txtMens.setText(msg);
            txtMens.setTextColor(Color.RED);
            txtJour.setText(msg);
            txtJour.setTextColor(Color.RED);
            txtHebd.setText(msg);
            txtHebd.setTextColor(Color.RED);
        }

        handler = new Handler();
        runner = this::callBack;
    }

    public void setObjectif(){
        db.open();

        pas_journalier_fait = db.getPasJournalier(user.getId());
        pas_hebdomadaire_fait = db.getPasHebdomadaire(user.getId());
        pas_mensuelle_fait = db.getPasMensuelle(user.getId());

        pas_journalier_objectif = db.getObjectifJournalier(user.getId());
        pas_hebdomadaire_objectif = db.getObjectifHedbomadaire(user.getId());
        pas_mensuelle_objectif = db.getObjectifMensuelle(user.getId());

        db.close();
    }

    public void setToolBar(){
        ConstraintLayout toolbar = findViewById(R.id.toolbar);
        ImageButton pas = toolbar.findViewById(R.id.pas).findViewById(R.id.bouton_pas);
        ImageButton calories = toolbar.findViewById(R.id.calories).findViewById(R.id.bouton_calories);
        ImageButton mes_info = toolbar.findViewById(R.id.mes_info).findViewById(R.id.bouton_mes_info);
        ImageButton sommeil = toolbar.findViewById(R.id.sommeil).findViewById(R.id.bouton_sommeil);

        pas.setOnClickListener(this::onClickListenerBoutonPas);
        mes_info.setOnClickListener(this::onClickListenerBoutonMesInfo);
        calories.setOnClickListener(this::onClickListenerBoutonCalorie);
        sommeil.setOnClickListener(this::onClickListenerBoutonSommeil);

    }

    public void setMsgMotivation(){
        Random random = new Random();
        TextView msg_motivation = findViewById(R.id.motivation).findViewById(R.id.textMotivation);
        db.open();
        msg_motivation.setText(db.getMsgMotivation(random.nextInt(20) + 1));
        db.close();
    }

    public void setNouveauObjectif(){
        db.open();

        date = db.getDateJournalier(user.getId());
        mois = db.getMoisMensuelle(user.getId());
        semaine = db.getSemaineHebdomadaire(user.getId());

        if(date==null){
            db_helper.addLignePasJournaliers(user.getId(), 0);
            date = db.getDateJournalier(user.getId());
        }
        if(mois==-1){
            db_helper.addLignePasMensuelle(user.getId(), 0);
            mois = db.getMoisMensuelle(user.getId());
        }
        if(semaine==-1){
            db_helper.addLignePasHebdomadaire(user.getId(), 0);
            semaine = db.getSemaineHebdomadaire(user.getId());
        }

        if(Regex.estDateDuJour(date)){
            db_helper.updateLigneJournalier(user.getId(),date);
            date = db.getDateJournalier(user.getId());
        }

        if(Regex.estMoisCourant(mois)){
            db_helper.updateLigneMensuelle(user.getId(),mois);
            mois = db.getMoisMensuelle(user.getId());
        }

        if(Regex.estSemaineCourante(semaine)){
            db_helper.updateLigneHebdomadaire(user.getId(),semaine);
            semaine = db.getSemaineHebdomadaire(user.getId());
        }

        db.close();
    }

    public void setTextViewPourcentage(){
        pourcent_mensuelle.setText(String.format(Locale.FRANCE, "%d%%", pourcentage_mensuelle));
        pourcent_journalier.setText(String.format(Locale.FRANCE, "%d%%", pourcentage_journalier));
        pourcent_hebdomadaire.setText(String.format(Locale.FRANCE, "%d%%", pourcentage_hebdomadaire));
    }

    public int setProgressBar(ProgressBar progressBar, int pasEffectue, int objectif){
        int pourcentage = (int) ((float)pasEffectue / objectif * 100);

        if(pourcentage < 0) pourcentage = 0;
        if(pourcentage > 100) pourcentage = 100;

        Drawable barre_progression;

        if(pourcentage<36)
            barre_progression = ContextCompat.getDrawable(this, R.drawable.progress);
        else if (pourcentage<66)
            barre_progression = ContextCompat.getDrawable(this, R.drawable.progress1);
        else
            barre_progression = ContextCompat.getDrawable(this, R.drawable.progress2);


        progressBar.setProgress(pourcentage);
        progressBar.setProgressDrawable(barre_progression);

        return pourcentage;
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
            pas_journalier_objectif = Integer.parseInt(affichage);

            db_helper.updateObjectifJournalier(user.getId(), Integer.parseInt(affichage));

            pourcentage_journalier=setProgressBar(bar_journalier, pas_journalier_fait, pas_journalier_objectif);
            setTextViewPourcentage();

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
            pas_hebdomadaire_objectif = Integer.parseInt(affichage);

            db_helper.updateObjectifHebdomadaire(user.getId(), Integer.parseInt(affichage));

            pourcentage_hebdomadaire = setProgressBar(bar_hebdomadaire, pas_hebdomadaire_fait, pas_hebdomadaire_objectif);
            setTextViewPourcentage();

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
            pas_mensuelle_objectif = Integer.parseInt(affichage);

            db_helper.updateObjectifMensuelle(user.getId(), Integer.parseInt(affichage));

            pourcentage_mensuelle = setProgressBar(bar_mensuelle, pas_mensuelle_fait, pas_mensuelle_objectif);
            setTextViewPourcentage();

            choix.setText(affichage);
            pop_up.dismiss();
        });

        Button bouton_annuler = pop_up.findViewById(R.id.bouton_annuler);
        bouton_annuler.setOnClickListener(v -> pop_up.dismiss());
    }

    //A supprimer ou a laisser
    public void onClickListenerBoutonPas(View view){
        Intent intent = new Intent(NombreDePasActivity.this, NombreDePasActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonCalorie(View view){
        Intent intent = new Intent(NombreDePasActivity.this, CaloriesActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonMesInfo(View view){
        Intent intent = new Intent(NombreDePasActivity.this, ActivityMesInformations.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        Intent intent = new Intent(NombreDePasActivity.this, SommeilActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void callBack(){
        db_helper.updateNombrePasJournalier(user.getId(), date, pas_journalier_fait);
        db_helper.updateNombrePasHebdomadaire(user.getId(), semaine, pas_hebdomadaire_fait);
        db_helper.updateNombrePasMensuelle(user.getId(), mois, pas_mensuelle_fait);

        pourcentage_journalier=setProgressBar(bar_journalier, pas_journalier_fait, pas_journalier_objectif);
        pourcentage_hebdomadaire=setProgressBar(bar_hebdomadaire, pas_hebdomadaire_fait, pas_hebdomadaire_objectif);
        pourcentage_mensuelle=setProgressBar(bar_mensuelle, pas_mensuelle_fait, pas_mensuelle_objectif);
        setTextViewPourcentage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this, stepCounterSensor);
        }
        callBack();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        callBack();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            // Le capteur de pas renvoie le nombre total de pas depuis le dernier redémarrage de l'appareil.
            if (compteur == 0) {
                compteur = (int) event.values[0];
            }

            int steps = (int) event.values[0] - compteur;

            pas_journalier_fait += steps;
            pas_hebdomadaire_fait += steps;
            pas_mensuelle_fait += steps;

            pas_journalier_textView.setText(String.format(Locale.FRANCE, "%d", pas_journalier_fait));
            pas_hebdomadaire_textView.setText(String.format(Locale.FRANCE, "%d", pas_hebdomadaire_fait));
            pas_mensuelle_textView.setText(String.format(Locale.FRANCE, "%d", pas_mensuelle_fait));

            compteur = (int) event.values[0];

            pourcentage_journalier = setProgressBar(bar_journalier, pas_journalier_fait, pas_journalier_objectif);
            pourcentage_hebdomadaire = setProgressBar(bar_hebdomadaire, pas_hebdomadaire_fait, pas_hebdomadaire_objectif);
            pourcentage_mensuelle = setProgressBar(bar_mensuelle, pas_mensuelle_fait, pas_mensuelle_objectif);

            setTextViewPourcentage();

            handler.removeCallbacks(runner);
            handler.postDelayed(runner, delai);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
