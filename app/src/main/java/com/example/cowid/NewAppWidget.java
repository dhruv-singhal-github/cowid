package com.example.cowid;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

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
    static String countryActive="10000";
    static String countryRecovered="21432";
    static String countryDeaths="200";
    static String stateActive="2144";
    static String stateRecovered="3245";
    static String stateDeaths="10";
//    static TextView tvconfirmed,tvactive,tvdeaths,tvrecovered,location,namest;
//    static ArrayList<String> statesa=new ArrayList<String>();
//    static ArrayList<String> confirmeda=new ArrayList<String>();
//    static ArrayList<HashMap<String,String>> statelist=new ArrayList<HashMap<String, String>>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//        new MainActivity.GetContacts().execute();
        views.setTextViewText(R.id.state, country);
        views.setTextViewText(R.id.active, countryActive);
        views.setTextViewText(R.id.recovered, countryRecovered);
        views.setTextViewText(R.id.deaths, countryDeaths);
        views.setTextViewText(R.id.stateName, state);
        views.setTextViewText(R.id.stateConfirmedCases, stateActive);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

