package com.example.tan.ex1_localservice;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocalService extends Service {

    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    public static String getTime(){
        SimpleDateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return mDateFormat.format(new Date());
    }

    public static String getDate() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return mDateFormat.format(new Date());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();
}