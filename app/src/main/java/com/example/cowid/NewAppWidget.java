package com.example.cowid;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.KeyEventDispatcher;

import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static String TAG = NewAppWidget.class.getSimpleName();
    static String country="India";
    static String state="Delhi";
    static String countryActive="100000";
    static String countryRecovered="214320";
    static String countryDeaths="20000";
    static String stateActive="214400";
    static String stateRecovered="3245";
    static String stateDeaths="10";
//    static TextView tvconfirmed,tvactive,tvdeaths,tvrecovered,location,namest;
//    static ArrayList<String> statesa=new ArrayList<String>();
//    static ArrayList<String> confirmeda=new ArrayList<String>();
//    static ArrayList<HashMap<String,String>> statelist=new ArrayList<HashMap<String, String>>();
   // NewAppWidget(String place,String subplace ,String pConfirmed, String pActive,String pRecoverd,String pDeaths, String  ){


 //   }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//        new MainActivity.GetContacts().execute();
        views.setTextViewText(R.id.wstate, country);
        views.setTextViewText(R.id.wactive, countryActive);
        views.setTextViewText(R.id.wrecovered, countryRecovered);
        views.setTextViewText(R.id.wdeaths, countryDeaths);
        views.setTextViewText(R.id.subState, state);
        views.setTextViewText(R.id.wsActive, stateActive);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for(int j = 0; j < appWidgetIds.length; j++)
        {
            int appWidgetId = appWidgetIds[j];
            updateAppWidget(context, appWidgetManager, appWidgetId);
            try {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setComponent(new ComponentName(context.getPackageName(),
                        MainActivity.class.getName()));
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        context, 0, intent, 0);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
                views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context.getApplicationContext(),
                        "There was a problem loading the application: ",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

