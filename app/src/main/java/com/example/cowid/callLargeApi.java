package com.example.cowid;

import android.appwidget.AppWidgetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class callLargeApi extends AsyncTask<String,String,String> {


    private  RemoteViews views;
    private  AppWidgetManager appWidgetManager;
    private String name;
    private  String state;
    String dConfirmed;
    String dActive;
    String dRecovered;
    String dDeaths;
    int appWidgetId;


    callLargeApi(String state, String name, AppWidgetManager appWidgetManager, RemoteViews views,int appWidgetId){
        this.state=state;
        this.name=name;
        this.appWidgetManager=appWidgetManager;
        this.views=views;
        this.appWidgetId=appWidgetId;
    }
    @Override
    protected String doInBackground(String... strings) {
        int i=2;


        HttpHandler shi = new HttpHandler();
        // Making a request to url and getting response
        String uri = "https://api.covid19india.org/districts_daily.json";
        String jsonStri = shi.makeServiceCall(uri);



        if (jsonStri!=null) {

            try {

                JSONObject  nb=new JSONObject(jsonStri);
                JSONObject nb1=nb.getJSONObject("districtsDaily");
                JSONObject nb2=nb1.getJSONObject(state);
                JSONArray nb3=nb2.getJSONArray(name);
                dConfirmed="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-i).getInt("confirmed")-nb3.getJSONObject(nb3.length()-i-1).getInt("confirmed"));
                dActive="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-i).getInt("active")-nb3.getJSONObject(nb3.length()-i-1).getInt("active"));
                dRecovered="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-i).getInt("recovered")-nb3.getJSONObject(nb3.length()-i-1).getInt("recovered"));
                dDeaths="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-i).getInt("deceased")-nb3.getJSONObject(nb3.length()-i-1).getInt("deceased"));



            } catch (final JSONException e) {



            }

        } else {


        }



        return null;
    }


    @Override
    protected void onPostExecute(String s) {

        views.setTextViewText(R.id.wdeltaactive,dActive);
        views.setTextViewText(R.id.wdeltadeaths,dDeaths);
        views.setTextViewText(R.id.wdeltarecovered,dRecovered);
        views.setTextViewText(R.id.wdeltaconformed,dConfirmed);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
