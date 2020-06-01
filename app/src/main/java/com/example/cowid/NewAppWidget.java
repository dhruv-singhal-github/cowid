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
    static  String state;
    static String countryconfirmed;
    static String countryActive="";
    static String countryRecovered="";
    static String countryDeaths="";

    static String dActive="";
    static String dRecovered="";
    static String dDeaths="";
    static String dConfirmed;

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

        new callApi(country,appWidgetManager,appWidgetId,views,countryActive,countryconfirmed,countryDeaths,countryRecovered).execute();

//        if(butt==0&&page==0){
//
//            HttpHandler sh = new HttpHandler();
//            // Making a request to url and getting response
//            String url = "https://api.covid19india.org/state_district_wise.json";
//            String jsonStr = sh.makeServiceCall(url);
//
//            Log.e(TAG, "Response from url: " + jsonStr);
//            if (jsonStr != null) {
//                try {
//
//                    JSONObject obj=new JSONObject(jsonStr);
//                    // Getting JSON Array node
//                    JSONArray states = obj.getJSONArray("statewise");
//                    Log.e(TAG, "got the JSONArray: " + jsonStr);
//                    // looping through All Contacts
//                    for (int i = 0; i < states.length(); i++) {
//
//
//                        JSONObject c = states.getJSONObject(i);
//                        if (i == 0) {
//                            countryActive =Integer.toString( (c.getInt("active")));
//                            countryRecovered = Integer.toString(c.getInt("recovered"));
//                            countryDeaths = Integer.toString(c.getInt("deaths"));
//                            countryconfirmed= Integer.toString(c.getInt("confirmed"));
////                             = c.getInt("deltaconfirmed");
////                            totaldd = c.getInt("deltadeaths");
////                            totalrd = c.getInt("deltarecovered");
////                            totalad = totalcd - totalrd - totaldd;
////                            last = c.getString("lastupdatedtime");
//
//                            break;
//                        }
//
//                    }
//
//
//
//
//
//
//
//
//
//
//
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//
//
//                }
//
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//
//            }
//
//
//
//            //India
//        }
//
//
//        else if((butt==0&&page==0)||(butt==1&&page==0)){
//
//
//            //state
//        }
//
//
//            else {
//                                                            //district
//        }
//
//
//

//        views.setTextViewText(R.id.wactive, countryActive);
//        views.setTextViewText(R.id.wrecovered, countryRecovered);
//        views.setTextViewText(R.id.wdeaths, countryDeaths);
       // views.setTextViewText(R.id.subState, state);
       // views.setTextViewText(R.id.wsActive, stateActive);



        // Instruct the widget manager to update the widget

    }

    @Override
    public void onReceive(Context context, Intent intent) {
         if(intent.getStringExtra("space")!=null) {
             country = intent.getStringExtra("space");
             intent.getIntExtra("page", page);
             intent.getIntExtra("butt",butt);

             if(page==1&&butt==1){
                state= intent.getStringExtra("state");
             }


         }
        super.onReceive(context, intent);


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
                intent.putExtra("place",country);
                Log.d("placeww",country);
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

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//        new MainActivity.GetContacts().execute();
        views.setTextViewText(R.id.wstate, "India");
        views.setTextViewText(R.id.wactive, countryActive);
        views.setTextViewText(R.id.wrecovered, countryRecovered);
        views.setTextViewText(R.id.wdeaths, countryDeaths);
        // views.setTextViewText(R.id.subState, state);

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

