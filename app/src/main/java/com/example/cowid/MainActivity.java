package com.example.cowid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    TextView tvconfirmed,tvactive,tvdeaths,tvrecovered,tvrincrease,tvaincrease,tvdincrease,tvcincrease;
    ArrayList<String> statesa=new ArrayList<String>();

    ArrayList<String> confirmeda=new ArrayList<String>();
    ArrayList<HashMap<String,String>> statelist=new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RippleBackground rippleBackground=findViewById(R.id.content);
        ImageView imageView=findViewById(R.id.centerImage);
        rippleBackground.startRippleAnimation();
        recyclerView =  findViewById(R.id.statesRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter(MainActivity.this,statesa,confirmeda));
        tvactive=findViewById(R.id.active);
        tvconfirmed=findViewById(R.id.confirmed);
        tvdeaths=findViewById(R.id.deaths);
        tvrecovered=findViewById(R.id.recovered);
        tvrincrease=findViewById(R.id.rincrease);
        tvaincrease=findViewById(R.id.aincrease);
        tvdincrease=findViewById(R.id.dincrease);
        tvcincrease=findViewById(R.id.cincrease);

        new GetContacts().execute();


    }



 private class GetContacts extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialogue;
        int totalc=0;
        int totald=0;
        int totala=0;
        int totalr=0;
        int totalai=0;
        int totalri=0;
        int totalci=0;
        int totaldi=0;

    @Override
    protected void onPreExecute() {

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
        String url = "https://api.covidindiatracker.com/state_data.json";
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {


                // Getting JSON Array node
                JSONArray states = new JSONArray(jsonStr);
                Log.e(TAG, "got the JSONArray: " + jsonStr);
                // looping through All Contacts
                for (int i = 0; i < states.length(); i++) {
                    JSONObject c = states.getJSONObject(i);

                    String name = c.getString("state");

                    int confirmed = c.getInt("confirmed");
                    int recovered = c.getInt("recovered");
                    int deaths = c.getInt("deaths");
                    int active=c.getInt("active");
                    int ai=c.getInt("aChanges");
                    int ci=c.getInt("cChanges");
                    int di=c.getInt("dChanges");
                    int ri=c.getInt("rChanges");
                    Log.e(TAG, "looping " + confirmed);

                    // Phone node is JSON Object


                    // tmp hash map for single contact


                    // adding each child node to HashMap key => value
                    totalr+=recovered;
                    totalc+=confirmed;
                    totald+=deaths;
                    totala+=active;
                    totalai+=ai;
                    totaldi+=di;
                    totalci+=ci;
                    totalri+=ri;
                    statesa.add(name);

                    confirmeda.add( Integer.toString(confirmed));
                    Log.e(TAG, "got array " + confirmeda.get(i));


                    // adding contact to contact list

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

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        dialogue.dismiss();
       tvactive.setText(Integer.toString(totala));
        tvconfirmed.setText(Integer.toString(totalc));
        tvrecovered.setText(Integer.toString(totalr));
        tvdeaths.setText(Integer.toString(totald));
        tvaincrease.setText("^ "+Integer.toString(totalai));
        tvcincrease.setText("^ "+Integer.toString(totalci));
        tvrincrease.setText("^ "+Integer.toString(totalri));
        tvdincrease.setText("^ "+Integer.toString(totaldi));
        recyclerView.setAdapter(new Adapter(MainActivity.this,statesa,confirmeda));
    }
}
}
