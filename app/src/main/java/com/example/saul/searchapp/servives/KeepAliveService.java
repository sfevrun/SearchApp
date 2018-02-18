package com.example.saul.searchapp.servives;

/**
 * Created by SAUL on 2/18/2018.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Empty service used by the custom tab to bind to, raising the application's importance.
 */
public class KeepAliveService extends Service {
    private static final Binder sBinder = new Binder();

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }
}
