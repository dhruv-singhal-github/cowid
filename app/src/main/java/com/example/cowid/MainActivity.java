package com.example.cowid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements cardClickListener,btClickListener {
    int area=0;
    private String TAG = MainActivity.class.getSimpleName();

    RecyclerView recyclerView;
    TextView tvconfirmed,tvactive,tvdeaths,tvrecovered,
            location,namest,ddeaths,drecovered,dactive,dconfirmed,lastupdated;
    ImageButton back,mainbt;
    ArrayList<String> statesa=new ArrayList<String>();

    ArrayList<String> confirmeda=new ArrayList<String>();

    String stopp;
    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.this.finish();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent imnp=getIntent();
        stopp=imnp.getStringExtra("place");
        if(imnp!=null){


            if(stopp!=null){
                Log.d("hello there",stopp);
                if(!(stopp).equals("India")){
                    new GetDistricts(imnp.getStringExtra("place")).execute();

                }
                else new GetContacts().execute();
            }

            else{
                new GetContacts().execute();
            }
        }

        else{
            new GetContacts().execute();
        }


        final RippleBackground rippleBackground=findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        recyclerView =  findViewById(R.id.statesRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter(MainActivity.this,statesa,confirmeda,this,this));
        tvactive=findViewById(R.id.active);
        tvconfirmed=findViewById(R.id.confirmed);
        tvdeaths=findViewById(R.id.deaths);
        tvrecovered=findViewById(R.id.recovered);
        location=findViewById((R.id.location));
        namest=findViewById(R.id.state);
        back=findViewById(R.id.back);
        mainbt=findViewById(R.id.mainbt);
        ddeaths=findViewById(R.id.deltadeaths);
        dactive=findViewById(R.id.deltaactive);
        dconfirmed=findViewById(R.id.deltaconfirmed);
        drecovered=findViewById(R.id.deltarecovered);
        lastupdated=findViewById(R.id.updated);
        mainbt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, NewAppWidget.class);
                        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                        intent.putExtra("space", namest.getText().toString());
                        intent.putExtra("flag", area);
                        intent.putExtra("butt",0);
                        Log.d("btt and page",Integer.toString(area));
                        int[] ids = AppWidgetManager.getInstance(MainActivity.this).getAppWidgetIds(new ComponentName(MainActivity.this, NewAppWidget.class));
                        if (ids != null && ids.length > 0) {
                            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                            MainActivity.this.sendBroadcast(intent);
                        }

                        MainActivity.this.finish();
                    }
                }
        );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(area==1){
                    new GetContacts().execute();
                }

                else
                {
                    MainActivity.this.finish();
                    System.exit(0);
                }
            }
        });

//        ccv = (CircularCompletionView) findViewById(R.id.ccv);
//        ccv.setCompletionPercentage(66);
//        ccv.setTextSize(16);
//        ccv.setStrokeSize(20);




    }

    @Override
    public void onClick(View view, String name) {
        Log.e(TAG,"clicked" +name);
        if(area==0) {
            view.setBackgroundColor(0x61ffffff);
            new GetDistricts(name).execute();
        }
    }


    @Override
    public void onBackPressed() {

        if(area==1){
            new GetContacts().execute();
        }
        else
            MainActivity.this.finish();
    }

    @Override
    public void onBTClick(View view, String name) {

        Intent intent = new Intent(MainActivity.this, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        intent.putExtra("space",name);
        intent.putExtra("flag", area);
        intent.putExtra("butt",1);
        if(area==1){
            intent.putExtra("state",namest.getText().toString());
        }
        Log.d("btt and page",Integer.toString(area));
        int[] ids = AppWidgetManager.getInstance(MainActivity.this).getAppWidgetIds(new ComponentName(MainActivity.this, NewAppWidget.class));
        if (ids != null && ids.length > 0) {
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            MainActivity.this.sendBroadcast(intent);
        }

        MainActivity.this.finish();

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialogue;
        int totalc=0;
        int totald=0;
        int totala=0;
        int totalr=0;
        int totalcd=0;
        int totaldd=0;
        int totalad=0;
        int totalrd=0;
        String last;
    @Override
    protected void onPreExecute() {
        area=0;
        statesa.clear();
        confirmeda.clear();
        dialogue = new ProgressDialog(MainActivity.this);

        dialogue.show();

        dialogue.setContentView(R.layout.progress);
        dialogue.setCancelable(false);
        dialogue.setMessage("Loading Data....");


        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String url = "https://api.covid19india.org/data.json";
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {

                JSONObject obj=new JSONObject(jsonStr);
                // Getting JSON Array node
                JSONArray states = obj.getJSONArray("statewise");
                Log.e(TAG, "got the JSONArray: " + jsonStr);
                // looping through All Contacts
                for (int i = 0; i < states.length(); i++) {


                    JSONObject c = states.getJSONObject(i);
                    if(i==0){
                        totala=c.getInt("active");
                        totalr=c.getInt("recovered");
                        totald=c.getInt("deaths");
                        totalc=c.getInt("confirmed");
                        totalcd=c.getInt("deltaconfirmed");
                        totaldd=c.getInt("deltadeaths");
                        totalrd=c.getInt("deltarecovered");
                        totalad=totalcd-totalrd-totaldd;
                        last=c.getString("lastupdatedtime");

                        continue;
                    }

                    String name = c.getString("state");

                    int confirmed = c.getInt("confirmed");
                    int recovered = c.getInt("recovered");
                    int deaths = c.getInt("deaths");
                    int active=c.getInt("active");

                    Log.e(TAG, "looping " + confirmed);

                    // Phone node is JSON Object
                    // tmp hash map for single contact
                    // adding each child node to HashMap key => value
                    if(!name.equals("State Unassigned")) {
                        statesa.add(name);
                        confirmeda.add(Integer.toString(confirmed));

                    }
                    // adding contact to contact list
                }
            } catch (final JSONException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get data from server",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        dialogue.dismiss();
        location.setText("State");
        namest.setText("India");
        tvactive.setText(Integer.toString(totala));
        tvconfirmed.setText(Integer.toString(totalc));
        tvrecovered.setText(Integer.toString(totalr));
        tvdeaths.setText(Integer.toString(totald));
        dactive.setText("^ "+Integer.toString(totalad));
        dconfirmed.setText("^ "+Integer.toString(totalcd));
        ddeaths.setText("^ "+Integer.toString(totaldd));
        drecovered.setText("^ "+Integer.toString(totalrd));
        lastupdated.setText("updated: "+last);

        recyclerView.setAdapter(new Adapter(MainActivity.this,statesa,confirmeda,MainActivity.this,MainActivity.this));
    }
}

    private class GetDistricts extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialogue;

        int totalc=0;
        int totald=0;
        int totala=0;
        int totalr=0;
        int totalad=0;
        int totalcd=0;
        int totalrd=0;
        int totaldd=0;
        String code;
        String name;
        GetDistricts(String name){
            this.name=name;
        }

        @Override
        protected void onPreExecute() {
            area=1;
            dialogue = new ProgressDialog(MainActivity.this);
            confirmeda.clear();
            statesa.clear();

            dialogue.show();

            dialogue.setContentView(R.layout.progress);
            dialogue.setCancelable(false);
            dialogue.setMessage("Loading Data....");


            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://api.covid19india.org/state_district_wise.json";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {

                    JSONObject obj=new JSONObject(jsonStr);
                    // Getting JSON Array node

                    JSONObject states = obj.getJSONObject(name);
                    code=states.getString("statecode");
                    JSONObject districts=states.getJSONObject("districtData");
                    Log.e(TAG, "got the JSONArray: " + jsonStr);
                    // looping through All Contacts
                    Iterator<String> iter = districts.keys();
                    while (iter.hasNext()) {

                        String key = iter.next();
                        try {

                            statesa.add(key);
                            JSONObject c = districts.getJSONObject(key);

                            int confirmed = c.getInt("confirmed");

                            int recovered = c.getInt("recovered");

                            int deaths = c.getInt("deceased");

                            int active=c.getInt("active");



                            Log.e(TAG, "looping " + confirmed);


                            totala+=active;
                            totalc+=confirmed;
                            totalr+=recovered;
                            totald+=deaths;




                            confirmeda.add( Integer.toString(confirmed));


                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get data from server",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }



            HttpHandler shs = new HttpHandler();
            // Making a request to url and getting response
            String urls = "https://api.covid19india.org/states_daily.json";
            String jsonStrs = sh.makeServiceCall(urls);

            Log.e(TAG, "Response from url: " + jsonStrs);
            if (jsonStr != null) {
                try {
                        JSONObject delta=new JSONObject(jsonStrs);
                        JSONArray arr=delta.getJSONArray("states_daily");

                        for(int i=arr.length()-4;i<arr.length();i++){

                            JSONObject today=arr.getJSONObject(i);
                            if(today.getString("status").equals("Confirmed"))
                            {
                                totalcd = today.getInt(code.toLowerCase());
                            }
                            else if(today.getString("status").equals("Recovered")){

                                totalrd=today.getInt(code.toLowerCase());
                            }

                             else if(today.getString("status").equals("Deceased")){

                                totaldd=today.getInt(code.toLowerCase());
                            }





                        }

                        totalad=totalcd-totaldd-totalrd;




                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get data from server",
                                Toast.LENGTH_LONG).show();
                    }
                });
                }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialogue.dismiss();
            location.setText("District");
            namest.setText(name);
            tvactive.setText(Integer.toString(totala));
            tvconfirmed.setText(Integer.toString(totalc));
            tvrecovered.setText(Integer.toString(totalr));
            tvdeaths.setText(Integer.toString(totald));
            dactive.setText("^ "+totalad);
            dconfirmed.setText("^ "+totalcd);
            drecovered.setText("^ "+totalrd);
            ddeaths.setText("^ "+totaldd);



            recyclerView.setAdapter(new Adapter(MainActivity.this,statesa,confirmeda,MainActivity.this,MainActivity.this));
        }
    }

}

