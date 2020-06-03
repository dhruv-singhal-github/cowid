package com.example.cowid;

import android.appwidget.AppWidgetManager;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.cowid.NewAppWidget.TAG;


public class callApi extends AsyncTask<String,String,String> {
    private RemoteViews views;
    String countryActive;
    String countryRecovered;
    String countryDeaths;
    String countryconfirmed;
    String dActive,dDeaths,dConfirmed,dRecovered;
    String state;
    AppWidgetManager appWidgetManager;
    int appWidgetId;
    String place;
    int page;
    int butt;



    callApi(String dActive,String dConfirmed,String dRecovered,String dDeaths, String state, int page, int butt, String place, AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews views, String countryActive, String countryconfirmed, String countryDeaths, String countryRecovered) {
        this.page=page;
        this.butt=butt;
        this.views = views;
        this.countryActive=countryActive;
        this.countryconfirmed=countryconfirmed;
        this.countryRecovered=countryRecovered;
        this.countryDeaths=countryDeaths;
        this.appWidgetManager=appWidgetManager;
        this.appWidgetId=appWidgetId;
        this.place=place;
        this.state=state;
        this.dActive=dActive;
        this.dDeaths=dDeaths;
        this.dRecovered=dRecovered;
        this.dConfirmed=dConfirmed;

    }

    @Override
    protected String doInBackground(String... strings) {

             if(butt==0&&page==0) {

                 HttpHandler sh = new HttpHandler();
                 // Making a request to url and getting response
                 String url = "https://api.covid19india.org/data.json";
                 String jsonStr = sh.makeServiceCall(url);


                 if (jsonStr != null) {
                     try {

                         JSONObject obj = new JSONObject(jsonStr);
                         // Getting JSON Array node
                         JSONArray states = obj.getJSONArray("statewise");

                         // looping through All Contacts
                         for (int i = 0; i < states.length(); i++) {


                             JSONObject c = states.getJSONObject(i);
                             if (i == 0) {
                                 countryActive = Integer.toString((c.getInt("active")));
                                 countryRecovered = Integer.toString(c.getInt("recovered"));
                                 countryDeaths = Integer.toString(c.getInt("deaths"));
                                 countryconfirmed = Integer.toString(c.getInt("confirmed"));
                                 dConfirmed="^ "+c.getString("deltaconfirmed");
                                 dDeaths="^ "+c.getString("deltadeaths");
                                 dRecovered="^ "+c.getString("deltarecovered");
                                 dActive="^ "+Integer.toString(c.getInt("deltaconfirmed")-c.getInt("deltadeaths")-c.getInt("deltarecovered"));



//                             = c.getInt("deltaconfirmed");
//                            totaldd = c.getInt("deltadeaths");
//                            totalrd = c.getInt("deltarecovered");
//                            totalad = totalcd - totalrd - totaldd;
//                            last = c.getString("lastupdatedtime");

                                 break;
                             }

                         }


                     } catch (final JSONException e) {

                     }

                 } else {

                 }
                 //India
             }


             if((butt==0&&page==1)||(butt==1&&page==0)){


                    String code="MH";
                 HttpHandler sh = new HttpHandler();
                 // Making a request to url and getting response
                 String url = "https://api.covid19india.org/data.json";
                 String jsonStr = sh.makeServiceCall(url);



                 HttpHandler shi = new HttpHandler();
                 // Making a request to url and getting response
                 String uri = "https://api.covid19india.org/states_daily.json";
                 String jsonStri = shi.makeServiceCall(uri);


                 if (jsonStr != null) {
                     try {

                         JSONObject obj = new JSONObject(jsonStr);
                         // Getting JSON Array node
                         JSONArray states = obj.getJSONArray("statewise");

                         // looping through All Contacts
                         for (int i = 0; i < states.length(); i++) {


                             JSONObject c = states.getJSONObject(i);
                             if (place.equals(c.getString("state"))) {
                                 code=c.getString("statecode");
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


                         JSONObject delta=new JSONObject(jsonStri);
                         JSONArray arr=delta.getJSONArray("states_daily");
                         int a=0,b=0,c=0;
                         for(int i=arr.length()-4;i<arr.length();i++){

                             JSONObject today=arr.getJSONObject(i);
                             if(today.getString("status").equals("Confirmed"))
                             {
                                 a =today.getInt(code.toLowerCase());
                             }
                             else if(today.getString("status").equals("Recovered")){

                                 b=today.getInt(code.toLowerCase());
                             }

                             else if(today.getString("status").equals("Deceased")){

                                 c=today.getInt(code.toLowerCase());
                             }




                         }
                         dConfirmed="^ "+a;
                         dRecovered="^ "+b;
                         dDeaths="^ "+c;
                         dActive="^ "+Integer.toString(a-b-c);










                     } catch (final JSONException e) {

                     }

                 } else {

                 }




             }

             if(butt==1&&page==1){
                 HttpHandler sh = new HttpHandler();
                 // Making a request to url and getting response
                 String url = "https://api.covid19india.org/state_district_wise.json";
                 String jsonStr = sh.makeServiceCall(url);


                 HttpHandler shi = new HttpHandler();
                 // Making a request to url and getting response
                 String uri = "https://api.covid19india.org/districts_daily.json";
                 String jsonStri = shi.makeServiceCall(uri);


                 if (jsonStr != null&&jsonStri!=null) {
                     try {

                         JSONObject obj=new JSONObject(jsonStr);
                         // Getting JSON Array node
                         JSONObject states = obj.getJSONObject(state);
                         JSONObject districts=states.getJSONObject("districtData");

                         // looping through All Contacts
                         Iterator<String> iter = districts.keys();
                         while (iter.hasNext()) {

                             String key = iter.next();
                             try {

                                if(key.equals(place)) {
                                    JSONObject c = districts.getJSONObject(key);

                                     countryconfirmed = c.getString("confirmed");

                                     countryRecovered = c.getString("recovered");

                                   countryDeaths = c.getString("deceased");

                                    countryActive = c.getString("active");


                                }



                             } catch (JSONException e) {

                             }
                         }

                         JSONObject  nb=new JSONObject(jsonStri);
                         JSONObject nb1=nb.getJSONObject("districtsDaily");
                         JSONObject nb2=nb1.getJSONObject(state);
                         JSONArray nb3=nb2.getJSONArray(place);
                         dConfirmed="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-1).getInt("confirmed")-nb3.getJSONObject(nb3.length()-2).getInt("confirmed"));
                         dActive="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-1).getInt("active")-nb3.getJSONObject(nb3.length()-2).getInt("active"));
                         dRecovered="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-1).getInt("recovered")-nb3.getJSONObject(nb3.length()-2).getInt("recovered"));
                         dDeaths="^ "+Integer.toString(nb3.getJSONObject(nb3.length()-1).getInt("deceased")-nb3.getJSONObject(nb3.length()-2).getInt("deceased"));



                     } catch (final JSONException e) {



                     }

                 } else {


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
        views.setTextViewText(R.id.wdeltaactive,dActive);
        views.setTextViewText(R.id.wdeltadeaths,dDeaths);
        views.setTextViewText(R.id.wdeltarecovered,dRecovered);
        views.setTextViewText(R.id.wdeltaconformed,dConfirmed);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
