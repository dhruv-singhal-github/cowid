package com.example.cowid;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent i, int flags, int startId) {


        RemoteViews view = new RemoteViews(getPackageName(), R.layout.new_app_widget);

        ComponentName theWidget = new ComponentName(this, NewAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] a=i.getIntArrayExtra("10");
        for(int j=0;j<a.length;j++) {
            int appw=a[j];
            new callApi(i.getStringExtra("1"), i.getStringExtra("2"), i.getStringExtra("3")
                    , i.getStringExtra("4"), i.getStringExtra("5"), i.getIntExtra("6", 0),
                    i.getIntExtra("7", 0), i.getStringExtra("8"), manager, appw, view, i.getStringExtra("12"),
                    i.getStringExtra("13"), i.getStringExtra("14"), i.getStringExtra("15"));
        }

        manager.updateAppWidget(theWidget, view);

        return super.onStartCommand(i, flags, startId);
    }
}
