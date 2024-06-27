package com.example.monsuividesante;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

    //Enlever commentaire de la ligne en dessous apres la fusion avec main
    //private User user;

    //temporaire
    private final int user_id=1;

    private DatabaseAccess db;
    private DatabaseOpenhelper db_helper;


    /*Faire en sorte de recuperer la date la plus récente (methode getDateJournalier()) puis,
    * verifier si la data correspond a la date courante(grace à Regex.estDateDuJour) sinon on met a j
    * our la ligne de l'user d'id user.getId() et de date getDateJournalier(), grace a la methode
    * updateLigneJournalier de db_helper*/

    /*Faire en sorte de recuperer la semaine la plus récente (methode getSemaineHebdomadaire()) puis,
    * verifier si la semaine correspond a la semaine courante (grace à Regex.estSemaineCourante) sinon
    * on verifie si cette semaine est dans la table pas_hedbo si oui on met a jour cette ligne sinon on l'ajoute*/

    /*Faire en sorte de recuperer le mois le plus récente (methode getMoisMensuelle()) puis,
    * verifier si le mois correspond au mois courante (grace à Regex.estMoisCourant) sinon
    * on verifie si ce mois est dans la table pas_mensuels si oui on met a jour cette ligne sinon on l'ajout*/

    /*Utilise les constantes qu'il y a dans DatabaseAccess et DatabaseOpenHelper*/
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

        //Enlever commentaire de la ligne en dessous apres la fusion avec main
        //user=getIntent().getSerializableExtra("user");

        db = DatabaseAccess.getInstance(this);
        db_helper = new DatabaseOpenhelper(this);

        db.open();

        /*db_helper.deletePasHebdomadaire();
        db_helper.addLignePasHebdomadaire(user_id, 100);
        db_helper.deletePasJournalier();
        db_helper.addLignePasJournaliers(user_id, 10);
        db_helper.deletePasMensuelle();
        db_helper.addLignePasMensuelle(user_id, 1000);*/

        date = db.getDateJournalier(user_id);
        mois = db.getMoisMensuelle(user_id);
        semaine = db.getSemaineHebdomadaire(user_id);

        if(Regex.estDateDuJour(date)){
            db_helper.updateLigneJournalier(user_id,date);
            date = db.getDateJournalier(user_id);
        }

        if(Regex.estMoisCourant(mois)){
            db_helper.updateLigneMensuelle(user_id,mois);
            mois = db.getMoisMensuelle(user_id);
        }

        if(Regex.estSemaineCourante(semaine)){
            db_helper.updateLigneHebdomadaire(user_id,semaine);
            semaine = db.getSemaineHebdomadaire(user_id);
        }

        db.close();

        Random random = new Random();
        TextView msg_motivation = findViewById(R.id.motivation).findViewById(R.id.textMotivation);
        db.open();
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

        db.open();

        pas_journalier_fait = db.getPasJournalier(user_id);
        pas_hebdomadaire_fait = db.getPasHebdomadaire(user_id);
        pas_mensuelle_fait = db.getPasMensuelle(user_id);

        pas_journalier_objectif = db.getObjectifJournalier(user_id);
        pas_hebdomadaire_objectif = db.getObjectifHedbomadaire(user_id);
        pas_mensuelle_objectif = db.getObjectifMensuelle(user_id);

        db.close();

        pourcentage_hebdomadaire=setProgressBar(bar_hebdomadaire, pas_hebdomadaire_fait, pas_hebdomadaire_objectif);
        pourcentage_mensuelle=setProgressBar(bar_mensuelle, pas_mensuelle_fait, pas_mensuelle_objectif);
        pourcentage_journalier=setProgressBar(bar_journalier, pas_journalier_fait, pas_journalier_objectif);

        setTextViewPourcentage();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounterSensor == null) {
            pas_journalier_textView.setText("Le capteur de pas n'est pas disponible");
            pas_hebdomadaire_textView.setText("Le capteur de pas n'est pas disponible");
            pas_mensuelle_textView.setText("Le capteur de pas n'est pas disponible");
        }

        handler = new Handler();
        runner = this::callBack;
    }

    public void callBack(){
        db_helper.updateNombrePasJournalier(user_id, date, pas_journalier_fait);
        db_helper.updateNombrePasHebdomadaire(user_id, semaine, pas_hebdomadaire_fait);
        db_helper.updateNombrePasMensuelle(user_id, mois, pas_mensuelle_fait);

        pourcentage_journalier=setProgressBar(bar_journalier, pas_journalier_fait, pas_journalier_objectif);
        pourcentage_hebdomadaire=setProgressBar(bar_hebdomadaire, pas_hebdomadaire_fait, pas_hebdomadaire_objectif);
        pourcentage_mensuelle=setProgressBar(bar_mensuelle, pas_mensuelle_fait, pas_mensuelle_objectif);
        setTextViewPourcentage();
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

            db_helper.updateObjectifJournalier(user_id, Integer.parseInt(affichage));

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

            db_helper.updateObjectifHebdomadaire(user_id, Integer.parseInt(affichage));

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

            db_helper.updateObjectifMensuelle(user_id, Integer.parseInt(affichage));

            pourcentage_mensuelle = setProgressBar(bar_mensuelle, pas_mensuelle_fait, pas_mensuelle_objectif);
            setTextViewPourcentage();

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
        //Enlever commentaire de la ligne en dessous apres la fusion avec main
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonMesInfo(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Mes informations)*/
        Intent intent = new Intent(NombreDePasActivity.this, MainActivity.class);
        //Enlever commentaire de la ligne en dessous apres la fusion avec main
        //intent.putExtra("user", user);
        startActivity(intent);
    }

    public void onClickListenerBoutonSommeil(View view){
        /*Modifier MainActivity.class par la classe java de l'activity Sommeil)*/
        Intent intent = new Intent(NombreDePasActivity.this, MainActivity.class);
        //Enlever commentaire de la ligne en dessous apres la fusion avec main
        //intent.putExtra("user", user);
        startActivity(intent);
    }


    /*Faire en sorte que si le capteur ne donne pas de signal on met à jour la db avec les
    * variables pas_journalier_fait, pas_hebdomadaire_fait et pas_mensuelle_fait
    *
    * Faire en sorte que si le capteur donne un signal on rajoute le nombre de pas aux
    * variables pas_journalier_fait, pas_hebdomadaire_fait et pas_mensuelle_fait et on appelle
    * setProgressBar sur toute les ProgressBar.
    * */
    @Override
    protected void onResume() {
        super.onResume();
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this, stepCounterSensor);
        }
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
