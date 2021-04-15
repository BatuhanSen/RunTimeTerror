package com.example.howtosurvive;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Bildirim extends Application {
    public static final String CHANNEL_ID = "Deprem Bildirim";

    @Override
    public void onCreate() {
        super.onCreate();
        bildirimOlustur();
    }

    private void bildirimOlustur(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,"Deprem Bildirim",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
