package com.example.cowid;

import android.appwidget.AppWidgetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.cowid.NewAppWidget.TAG;
import static com.example.cowid.NewAppWidget.butt;
import static com.example.cowid.NewAppWidget.page;

public class callApi extends AsyncTask<String,String,String> {
    private RemoteViews views;
    String countryActive;
    String countryRecovered;
    String countryDeaths;
    String countryconfirmed;
    AppWidgetManager appWidgetManager;
    int appWidgetId;
    String place;



    callApi(String place, AppWidgetManager appWidgetManager,int appWidgetId, RemoteViews views, String countryActive, String countryconfirmed, String countryDeaths, String countryRecovered) {
        this.views = views;
        this.countryActive=countryActive;
        this.countryconfirmed=countryconfirmed;
        this.countryRecovered=countryRecovered;
        this.countryDeaths=countryDeaths;
        this.appWidgetManager=appWidgetManager;
        this.appWidgetId=appWidgetId;
        this.place=place;
    }

    @Override
    protected String doInBackground(String... strings) {

             if(butt==0&&page==0) {

                 HttpHandler sh = new HttpHandler();
                 // Making a request to url and getting response
                 String url = "https://api.covid19india.org/data.json";
                 String jsonStr = sh.makeServiceCall(url);

                 Log.e(TAG, "Response from url: " + jsonStr);
                 if (jsonStr != null) {
                     try {

                         JSONObject obj = new JSONObject(jsonStr);
                         // Getting JSON Array node
                         JSONArray states = obj.getJSONArray("statewise");
                         Log.e(TAG, "got the JSONArray: " + jsonStr);
                         // looping through All Contacts
                         for (int i = 0; i < states.length(); i++) {


                             JSONObject c = states.getJSONObject(i);
                             if (i == 0) {
                                 countryActive = Integer.toString((c.getInt("active")));
                                 countryRecovered = Integer.toString(c.getInt("recovered"));
                                 countryDeaths = Integer.toString(c.getInt("deaths"));
                                 countryconfirmed = Integer.toString(c.getInt("confirmed"));
//                             = c.getInt("deltaconfirmed");
//                            totaldd = c.getInt("deltadeaths");
//                            totalrd = c.getInt("deltarecovered");
//                            totalad = totalcd - totalrd - totaldd;
//                            last = c.getString("lastupdatedtime");

                                 break;
                             }

                         }


                     } catch (final JSONException e) {
                         Log.e(TAG, "Json parsing error: " + e.getMessage());
                     }

                 } else {
                     Log.e(TAG, "Couldn't get json from server.");
                 }
                 //India
             }


             if((butt==0&&page==1)||(butt==1&&page==0)){

                 HttpHandler sh = new HttpHandler();
                 // Making a request to url and getting response
                 String url = "https://api.covid19india.org/data.json";
                 String jsonStr = sh.makeServiceCall(url);

                 Log.e(TAG, "Response from url: " + jsonStr);
                 if (jsonStr != null) {
                     try {

                         JSONObject obj = new JSONObject(jsonStr);
                         // Getting JSON Array node
                         JSONArray states = obj.getJSONArray("statewise");
                         Log.e(TAG, "got the JSONArray: " + jsonStr);
                         // looping through All Contacts
                         for (int i = 0; i < states.length(); i++) {


                             JSONObject c = states.getJSONObject(i);
                             if (place.equals(c.getString("state"))) {
                                 countryActive = Integer.toString((c.getInt("active")));
                                 countryRecovered = Integer.toString(c.getInt("recovered"));
                                 countryDeaths = Integer.toString(c.getInt("deaths"));
                                 countryconfirmed = Integer.toString(c.getInt("confirmed"));
//                             = c.getInt("deltaconfirmed");
//                            totaldd = c.getInt("deltadeaths");
//                            totalrd = c.getInt("deltarecovered");
//                            totalad = totalcd - totalrd - totaldd;
//                            last = c.getString("lastupdatedtime");

                                 break;
                             }

                         }


                     } catch (final JSONException e) {
                         Log.e(TAG, "Json parsing error: " + e.getMessage());
                     }

                 } else {
                     Log.e(TAG, "Couldn't get json from server.");
                 }




             }



         return null;

    }

    @Override
    protected void onPostExecute(String s) {


        views.setTextViewText(R.id.wstate, place);
        views.setTextViewText(R.id.wactive, countryActive);
        views.setTextViewText(R.id.wrecovered, countryRecovered);
        views.setTextViewText(R.id.wdeaths, countryDeaths);
        views.setTextViewText(R.id.wconfirm,countryconfirmed);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
