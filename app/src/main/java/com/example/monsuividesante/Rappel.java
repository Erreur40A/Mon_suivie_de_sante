package com.example.monsuividesante;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import java.util.Calendar;

public class Rappel {
    public static final String explication = "Pour s'assurer que la notification s'active à la bonne heure, veuillez autoriser l'application à utiliser les alarmes exactes";

    //on utilise @SuppressLint("ScheduleExactAlarm") car on vérifie les permissions dans SommeilActivity
    @SuppressLint("ScheduleExactAlarm")
    public static void setRappel(Context context, int heure, int minute){
        Intent intent = new Intent(context, NotificationsBroadcast.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE);

        Calendar calandrier = setAlarm(heure, minute);
        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarme.cancel(pendingIntent);

        alarme.setRepeating(AlarmManager.RTC_WAKEUP, calandrier.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static Calendar setAlarm(int heure, int minute){
        Calendar calendrier = Calendar.getInstance();

        calendrier.setTimeInMillis(System.currentTimeMillis());

        calendrier.set(Calendar.MINUTE, minute);
        calendrier.set(Calendar.HOUR_OF_DAY, heure);
        calendrier.set(Calendar.SECOND, 0);
        calendrier.set(Calendar.MILLISECOND, 0);

        return calendrier;
    }
}
