package ro.pub.cs.systems.eim.practicaltest01var07;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Objects;

public class PracticalTest01Var07Service extends Service {

    ProcessingThread processingThread = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("Service", "onCreate");
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) Objects.requireNonNull(getSystemService(Context.NOTIFICATION_SERVICE))).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int firstNumber = intent.getIntExtra(Constants.RANDOM_INPUT1, -1);
        int secondNumber = intent.getIntExtra(Constants.RANDOM_INPUT2, -1);
        int thirdNumber = intent.getIntExtra(Constants.RANDOM_INPUT3, -1);
        int fourthNumber = intent.getIntExtra(Constants.RANDOM_INPUT4, -1);

        Log.d("Service", "first number " + firstNumber + " second number "
                        + secondNumber + " third number " + thirdNumber + " fourth number " + fourthNumber );
        processingThread = new ProcessingThread(this, firstNumber, secondNumber, thirdNumber, fourthNumber);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
