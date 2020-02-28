package com.example.remindme;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class RemindMe extends Application {
    public static final String CHANNEL_1_ID = "alarm";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel(){
        NotificationChannel alarm = new NotificationChannel(
                CHANNEL_1_ID,
                "Alarm",
                NotificationManager.IMPORTANCE_HIGH
        );
        alarm.setLightColor(R.color.colorPrimary);

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(alarm);
            manager.createNotificationChannel(alarm);
        }
    }
}
