package com.example.monsuividesante;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
        db.open();

        ArrayList<String> res = db.getDuree();
        TextView textView_id = (TextView) findViewById(R.id.ici);
        textView_id.setText(res.get(1));
        db.close();

        /*final DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
        db.open();

        String id = db.gettkt("2");
        TextView textView_id = (TextView) findViewById(R.id.ici);
        textView_id.setText(id);

        db.close();*/
    }
}