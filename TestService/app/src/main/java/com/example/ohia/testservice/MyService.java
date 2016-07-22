package com.example.ohia.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service
{
    public MyService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "service started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service stoped", Toast.LENGTH_LONG).show();
    }
}
