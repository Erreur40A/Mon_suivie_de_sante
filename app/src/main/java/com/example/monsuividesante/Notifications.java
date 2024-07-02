package com.example.monsuividesante;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class Notifications {
    private final Context context;
    public static final String CHANNEL_ID = "reveil";
    public static int NOTIFICATION_ID = 1;
    private Notification notification;
    private NotificationManager manager;

    public Notifications(Context c){
        context = c;
    }

    private void createNotificationChannel() {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    Notifications.CHANNEL_ID,
                    "Rappel coucher",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("Utiliser pour rappeler à l'utilisateur de ce coucher");

            manager.createNotificationChannel(channel);
        }
    }

    public void showNotification() {
        createNotificationChannel();

        Intent intent = new Intent(context, Connexion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntentActivity = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE);

        RemoteViews layout_notif = new RemoteViews(context.getPackageName(), R.layout.notifications);

        NotificationCompat.Builder builder_notif = new NotificationCompat.Builder(context, CHANNEL_ID);

        builder_notif.setSmallIcon(R.drawable.logo_app)
                .setContentTitle("Mon Suivie de Santé")
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(pendingIntentActivity)
                .setCustomContentView(layout_notif)
                .setAutoCancel(true);

        notification = builder_notif.build();
        manager.notify(NOTIFICATION_ID, notification);
    }
}
