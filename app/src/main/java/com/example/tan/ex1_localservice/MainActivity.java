package com.example.tan.ex1_localservice;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textViewMain;
    private LocalService mBoundService;
    Button btnConnect;
    public boolean isBound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMain = findViewById(R.id.textViewMain);
        btnConnect = findViewById(R.id.btnConnect);
    }

    public void onDate(View view) {
        if(isMyServiceRunning(LocalService.class) && isBound) {
            textViewMain.setText(LocalService.getDate());
        }
    }

    public void onTime(View view) {
        if(isMyServiceRunning(LocalService.class) && isBound) {
            textViewMain.setText(LocalService.getTime());
        }
    }

    public void onConnect(View view) {
        if(isMyServiceRunning(LocalService.class)) {
            onDestroy();
            textViewMain.setText(R.string.disconnected);
            btnConnect.setText(R.string.connect);
            isBound = false;
        } else {
            Intent serviceToggle = new Intent(MainActivity.this, LocalService.class);
            bindService(serviceToggle, mConnection, Context.BIND_AUTO_CREATE);
            textViewMain.setText(R.string.connected);
            btnConnect.setText(R.string.disconnect);
            isBound = true;
        }
    }

    public ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((LocalService.LocalBinder)service).getService();
            Toast.makeText(MainActivity.this, R.string.local_service_connected,
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
            Toast.makeText(MainActivity.this, R.string.local_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        @SuppressLint("ServiceCast") ActivityManager manager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    void doUnbindService() {
        if (isBound) {
            unbindService(mConnection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
}
