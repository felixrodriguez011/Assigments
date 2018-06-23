package com.example.felix.dialogapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

    private static final String CHANNEL_ID = "ANY_ID";

    public MyService() {
    }

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    @Override
    public void onCreate() {
        // Start up the handlerThread running the service.  Note that we create a
        // separate handlerThread because the service normally runs in the process's
        // main handlerThread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread handlerThread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();


        // Instead of handling many threads, in this model one thread (HandlerThread) is kept alive
        // and reused for many tasks.
        // The Looper keeps a thread alive. It goes through a MessageQueue,
        // retrieves a message and assigns it to a Handler for processing.
        // Handlers queue messages and execute their task when instructed to do so by the looper.
        // Get the HandlerThread's Looper and use it for our Handler (otherwise we use the looper for UI thread)
        mServiceLooper = handlerThread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // When the service is started, push message to the queue to start a job
        //Message will be received in handleMessage()
        Message msg = mServiceHandler.obtainMessage();
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    // Handler that sends and receives messages to/from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            Toast.makeText(MyService.this, "Service is Handling task", Toast.LENGTH_SHORT).show();
            Log.d("MyService", "Service is handling task");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "ACTION_NOTIFY_ART_PIECE_CLOSE", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("ACTION_NOTIFY_ART_PIECE_CLOSE");
                channel.setLightColor(Color.CYAN);
                channel.canShowBadge();
                channel.setShowBadge(true);
                // Register the channel with the system
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
            }

            PendingIntent pi = PendingIntent.getActivity(MyService.this, 0, new Intent(MyService.this, MainActivity.class), 0);

            Notification notification = new NotificationCompat.Builder(MyService.this, CHANNEL_ID)
                    .setTicker("App Notification")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("Notification #1")
                    .setContentText("Alert!!!!!!!!!")
                    .setContentIntent(pi)
                    .setAutoCancel(false)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Brief description of what could be this notification"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyService.this);
            notificationManager.notify(0, notification);

        }
    }

}