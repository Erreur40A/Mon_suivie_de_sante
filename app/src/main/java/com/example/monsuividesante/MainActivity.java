package com.example.monsuividesante;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private TextView textPasFaitJournalier;
    private TextView editHebdo;
    private TextView editmensuel;

    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_de_pas);

        textPasFaitJournalier = findViewById(R.id.textPasFaitJournalier);
        editHebdo = findViewById(R.id.editHebdo);
        editmensuel = findViewById(R.id.editmensuel);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounterSensor == null) {
            textPasFaitJournalier.setText("Step Counter Sensor not available!");
            editHebdo.setText("Step Counter Sensor not available!");
            editmensuel.setText("Step Counter Sensor not available!");

        }
    }

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
            // Si vous voulez le nombre de pas depuis que l'application a commencé à fonctionner, il faut stocker la valeur de départ.
            if (stepCount == 0) {
                stepCount = (int) event.values[0];
            }
            int steps = (int) event.values[0] - stepCount;
            textPasFaitJournalier.setText("Nombre de pas : " + steps);
            editmensuel.setText("Nombre de pas : " + steps);
            editHebdo.setText("Nombre de pas : " + steps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}