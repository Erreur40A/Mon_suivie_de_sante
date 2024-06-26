package com.example.monsuividesante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Connexion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connexion);
        setupUI();
    }
    private void setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.page_de_connexion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button boutton_connexion = findViewById(R.id.login_button);
        boutton_connexion.setOnClickListener(this::setOnCLickListenerConnexion);

        Button boutton_inscription = findViewById(R.id.inscription1);
        boutton_inscription.setOnClickListener(this::setOnCLickListenerInscription);
    }

    public void setOnCLickListenerConnexion(View view) {
        String id = ((EditText) findViewById(R.id.identifiant_connexion)).getText().toString();
        String mdp = ((EditText) findViewById(R.id.password_connexion)).getText().toString();


        if(!id.isEmpty() && !mdp.isEmpty()) {
            final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
            db.open();
            String hashMdp = Hashage.hasherMdpHexa(mdp);

            boolean exist = db.existUser(id);
            String user_hashmdp = db.getHashMdp(id);

            if(exist && Hashage.isEquals(hashMdp, user_hashmdp)) {
                int user_id = db.getIdUtilisateur(id);
                String user_nom = db.getNomUtilisateur(user_id);
                String user_prenom = db.getPrenomUtilisateur(user_id);
                int user_age = db.getAge(user_id);
                int user_taille = db.getTaille(user_id);
                int user_poids = db.getPoids(user_id);
                String user_genre = db.getGenre(user_id);
                Genre genre;
                if(user_genre.equals(Genre.HOMME.getGenre())) genre = Genre.HOMME;
                else genre = Genre.FEMME;

                int user_type = db.getTypeDePersonne(user_id);

                User user = new User(user_id, user_nom, user_prenom, user_age, user_taille, user_poids, genre, user_type);

                Intent intent = new Intent(Connexion.this, ActivityMesInformations.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                setContentView(R.layout.error);
                Button boutton_erreur = findViewById(R.id.btnDismiss);
                boutton_erreur.setOnClickListener(this::setOnClickListenerErreur);
            }
            db.close();
        } else {
            Toast.makeText(this, "entrer un nom d'utilisateur et un mdp", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnCLickListenerInscription(View view) {
        Intent intent = new Intent(Connexion.this, ActivityInscription.class);
        startActivity(intent);
    }

    public void setOnClickListenerErreur(View view) {
        setContentView(R.layout.activity_connexion);
        setupUI();
    }
}