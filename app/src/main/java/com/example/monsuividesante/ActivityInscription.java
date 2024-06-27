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

public class ActivityInscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.page_inscription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button boutton_inscrire = findViewById(R.id.inscription2);
        boutton_inscrire.setOnClickListener(this::setOnClickListenerInfo);
    }

    public void setOnClickListenerInfo(View view) {

        String id_inscr = ((EditText) findViewById(R.id.identifiant_inscription)).getText().toString();
        String mdp_inscr = ((EditText) findViewById(R.id.password_inscription)).getText().toString();

        //on supprime les espaces au début et à la fin
        id_inscr=id_inscr.trim();

        if(!id_inscr.isEmpty() && !mdp_inscr.isEmpty()) {
            final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());

            db.open();
            boolean exist = db.existUser(id_inscr);
            db.close();

            if (exist) {
                Toast.makeText(this, "Identifiant deja existant", Toast.LENGTH_SHORT).show();
            } else if (!Regex.estMdpValide(mdp_inscr)) {
                Toast.makeText(this, "Le mot de passe n'est pas assez sécuriser.\nVeuillez utiliser au moins un chiffre, une lettre minuscule et une lettre majuscule", Toast.LENGTH_LONG).show();
            } else {
                String hashMpd = Hashage.hasherMdpHexa(mdp_inscr);

                db.open();
                db.addUser(id_inscr, hashMpd);
                int user_id=db.getIdUtilisateur(id_inscr);
                db.close();

                Intent intent = new Intent(ActivityInscription.this, InfoActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Entrer un identifiant et un mot de passe", Toast.LENGTH_SHORT).show();
        }
    }
}