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


        if(!id_inscr.isEmpty() && !mdp_inscr.isEmpty()) {
            final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
            db.open();

            boolean exist = db.existUser(id_inscr);

            if(exist) {
                Toast.makeText(this, "utilisateur deja existant", Toast.LENGTH_SHORT).show();

            } else {
                db.addUser(id_inscr, mdp_inscr);
                db.close();
                Intent intent = new Intent(ActivityInscription.this, InfoActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "entrer un nom d'utilisateur et un mdp", Toast.LENGTH_SHORT).show();
        }
    }
}