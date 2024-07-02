package com.example.monsuividesante;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationsBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notifications notif = new Notifications(context);

        notif.showNotification();
    }
}
