package com.example.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class ReceiverActivity extends BroadcastReceiver {

    public static final String NOTIFY_CHANNEL_ID = "channel_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("mess");
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(NOTIFY_CHANNEL_ID,"Received Message Channel",importance);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,NOTIFY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Received message")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(0, builder.build());
    }
}
