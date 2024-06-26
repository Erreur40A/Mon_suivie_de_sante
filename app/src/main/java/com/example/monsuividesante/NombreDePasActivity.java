package com.example.monsuividesante;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.health.connect.client.HealthConnectClient;
import androidx.health.connect.client.PermissionController;
import androidx.health.connect.client.request.AggregateRequest;
import androidx.health.connect.client.time.TimeRangeFilter;



import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NombreDePasActivity extends AppCompatActivity {

    private TextView pas_journalier_fait, pas_hebdomadaire_fait, pas_mensuelle_fait;
    private TextView objectif_journalier, objectif_hebdomadaire, objectif_mensuelle;
    private TextView pourcent_journaier, pourcent_hebdomadaire, pourcent_mensuelle, msg_motivation;
    private ImageButton bouton_journalier, bouton_hebdomadaire, bouton_mensuelle;
    private ProgressBar bar_journalier, bar_mensuelle, bar_hebdomadaire;

    //temporaire
    private final int user_id=1;

    private DatabaseAccess db;
    private DatabaseOpenhelper db_helper;
    private HealthConnectClient healthConnectClient;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

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

        db_helper.updateObjectifMensuelle(user_id, 20);
        db.open();
        //il y a 19 msg entre 1 et 20
        msg_motivation.setText(""+db.getObjectifMensuelle(user_id));
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

        bar_journalier = journ.findViewById(R.id.progressBarJour);
        bar_hebdomadaire = hebd.findViewById(R.id.progresshebdo);
        bar_mensuelle = mens.findViewById(R.id.progressmensuel);

        bouton_journalier.setOnClickListener(this::onClickListenerObjectifJournalier);
        bouton_mensuelle.setOnClickListener(this::onClickListenerObjectifMensuelle);
        bouton_hebdomadaire.setOnClickListener(this::onClickListenerObjectifHebdomadaire);

        healthConnectClient = HealthConnectClient.getOrCreate(this);

        checkAndRequestPermissions();

        checkAndRequestPermissions();
    }

    private ActivityResultLauncher<Intent> permissionRequestLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Vérifier le résultat de la demande de permission
                if (result.getResultCode() == RESULT_OK) {
                    // Permissions accordées, lire les données de pas
                    fetchAndDisplayStepsData();
                } else {
                    // Permissions refusées
                    Log.e(TAG, "Permissions refusées par l'utilisateur");
                }
            }
    );


    private void checkAndRequestPermissions() {
        executorService.execute(() -> {
            try {
                // Vérifier si les permissions nécessaires sont accordées
                if (!hasRequiredPermissions()) {
                    // Demander les permissions si elles ne sont pas accordées
                    requestPermissions();
                } else {
                    // Permissions déjà accordées, lire les données de pas
                    fetchAndDisplayStepsData();
                }
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la vérification/demande des permissions", e);
            }
        });
    }

    private boolean hasRequiredPermissions() {
        // Obtenir le nom complet de la classe StepsRecord
        String stepsRecordPermission = androidx.health.connect.client.records.StepsRecord.class.getName();

        // Créer un ensemble contenant la permission requise
        Set<String> requiredPermissions = Collections.singleton(stepsRecordPermission);

        try {
            // Obtenir les permissions accordées par Health Connect
            Set<String> grantedPermissions = healthConnectClient.getPermissionController()
                    .getGrantedPermissions()
                    .get();

            // Vérifier si toutes les permissions requises sont accordées
            return grantedPermissions.containsAll(requiredPermissions);
        } catch (Exception e) {
            Log.e("HealthConnect", "Erreur lors de la vérification des permissions", e);
            return false;
        }
    }



    private void requestPermissions() {
        // Liste des permissions nécessaires pour Health Connect
        List<String> permissions = Collections.singletonList(PermissionController.Permission.READ_RECORDS);

        // Créer une intention pour la demande de permissions
        Intent intent = healthConnectClient.getPermissionController()
                .createRequestPermissionIntent(permissions);

        // Lancer l'intention pour demander les permissions
        permissionRequestLauncher.launch(intent);
    }

    private void fetchAndDisplayStepsData() {
        executorService.execute(() -> {
            try {
                // Lire les pas journaliers
                ZonedDateTime now = ZonedDateTime.now();
                ZonedDateTime startOfDay = now.toLocalDate().atStartOfDay(now.getZone());
                long startTime = startOfDay.toInstant().toEpochMilli();
                long endTime = now.toInstant().toEpochMilli();

                AggregateRequest request = new AggregateRequest.Builder()
                        .setTimeRangeFilter(TimeRangeFilter.between(Instant.ofEpochMilli(startTime), Instant.ofEpochMilli(endTime)))
                        .addMetric(AggregationType.STEP_COUNT_TOTAL)
                        .build();

                AggregationResults results = healthConnectClient.aggregate(request).get();
                AggregationResult stepCountResult = results.getAggregateResult(AggregationType.STEP_COUNT_TOTAL);

                int stepCount = stepCountResult != null ? (int) stepCountResult.getValue() : 0;
                runOnUiThread(() -> updateUiWithStepData(stepCount));
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la lecture des données de pas", e);
            }
        });
    }
    private void updateUiWithStepData(int stepCount) {
        // Mettre à jour l'affichage du nombre de pas et la barre de progression
        pas_journalier_fait.setText(String.valueOf(stepCount));

        // Supposons un objectif journalier par défaut de 10000 pas
        int objectifJournalier = 10000;

        // Calculer le pourcentage atteint
        int pourcentage = (int) ((stepCount / (float) objectifJournalier) * 100);
        pourcent_journalier.setText(pourcentage + "%");

        // Mettre à jour la barre de progression
        bar_journalier.setProgress(pourcentage);
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
