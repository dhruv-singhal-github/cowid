package com.example.cowid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
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

    private  PendingIntent service;
    static String TAG = NewAppWidget.class.getSimpleName();
    static String country="India";
    static  String state;
    static String countryconfirmed;
    static String countryActive="";
    static String countryRecovered="";
    static String countryDeaths="";
    AppWidgetManager mawm;
    int[] appwi;

    static String dActive="";
    static String dRecovered="";
    static String dDeaths="";
    static String dConfirmed="";

    static int page=0;
    static int butt=0;
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

        new callApi(dActive,dConfirmed,dRecovered,dDeaths,state,page, butt, country, appWidgetManager, appWidgetId, views, countryActive, countryconfirmed, countryDeaths, countryRecovered).execute();

    }
    @Override
    public void onReceive(Context context, Intent intent) {




         if(intent.getStringExtra("space")!=null) {
             country = intent.getStringExtra("space");
             Log.d("what's my place",country);
             page=intent.getIntExtra("flag", page);
             butt=intent.getIntExtra("butt",butt);



             if(page==1&&butt==1){
                state= intent.getStringExtra("state");
             }


         }


        super.onReceive(context, intent);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
   // callApi(String dActive,String dConfirmed,String dRecovered,String dDeaths, String state, int page, int butt, String place, AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews views, String countryActive, String countryconfirmed, String countryDeaths, String countryRecovered) {
//        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        final Intent i = new Intent(context, UpdateService.class);
//        i.putExtra("1",dActive);
//        i.putExtra("2",dConfirmed);
//        i.putExtra("3",dRecovered);
//        i.putExtra("4",dDeaths);
//        i.putExtra("5",state);
//        i.putExtra("6",page);
//        i.putExtra("7",butt);
//        i.putExtra("8",country);
//        i.putExtra("10",appWidgetIds);
//        i.putExtra("12",countryActive);
//        i.putExtra("13",countryconfirmed);
//        i.putExtra("14",countryDeaths);
//        i.putExtra("15",countryRecovered);
//
//
//        if (service == null) {
//            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
//        }
//        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, service);
        appwi=appWidgetIds;
        mawm=appWidgetManager;
        for(int j = 0; j < appWidgetIds.length; j++)
        {
            int appWidgetId = appWidgetIds[j];
            updateAppWidget(context, appWidgetManager, appWidgetId);
            try {
                Intent intent = new Intent("android.intent.action.MAIN");
                Log.d("this place",country);

                intent.putExtra("place",country);
                if(butt==1&&page==1){
                    intent.putExtra("place",state);
                }

                intent.addCategory("android.intent.category.LAUNCHER");

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setComponent(new ComponentName(context.getPackageName(),
                        MainActivity.class.getName()));

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
                Log.d("placeww",country);
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

        // Construct the RemoteViews object
       super.onEnabled(context);
        // views.setTextViewText(R.id.subState, state);

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

