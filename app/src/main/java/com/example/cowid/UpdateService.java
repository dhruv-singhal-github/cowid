package com.example.cowid;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.net.InetAddress;

public class UpdateService extends Service {

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());
        Log.d("updated","silly");
            Log.d("internet",isInternetAvailable()?"1":"0");
        if (isInternetAvailable()==true) {
            int[] allWidgetIds = intent
                    .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

//      ComponentName thisWidget = new ComponentName(getApplicationContext(),
//              MyWidgetProvider.class);
//      int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

            for (int widgetId : allWidgetIds) {

                RemoteViews remoteViews = new RemoteViews(this
                        .getApplicationContext().getPackageName(),
                        R.layout.new_app_widget);

                String a = "0";
                new callApi(a, a, a, a, intent.getStringExtra("state"),
                        intent.getIntExtra("page", 0), intent.getIntExtra("butt", 0),
                        intent.getStringExtra("place"), appWidgetManager, widgetId, remoteViews, a, a, a, a).execute();
                // Register an onClickListener
//            Intent clickIntent = new Intent(this.getApplicationContext(),
//                    MyWidgetProvider.class);
//
//            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
//                    allWidgetIds);
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                    getApplicationContext(), 0, clickIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }

        }
        stopSelf();




        return super.onStartCommand(intent, flags, startId);
    }
}
