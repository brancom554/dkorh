package com.example.dkorh.Rapport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jisrh.R;
import com.example.dkorh.model.AdapterFish;
import com.example.dkorh.model.DataFish;
import com.example.dkorh.model.config;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eu.amirs.JSON;

public class rapport extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFishPrice;
    private AdapterFish mAdapter;
    String info;
    String nom;
    String ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        nom = json.key("username").toString();
        ids = json.key("employee_id").toString();


        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        SpannableString firstPart = new SpannableString("RAPPORT : ");
        SpannableString lastPart = new SpannableString(nom);
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));


        //Make call to AsyncTask
        new AsyncLogin().execute();
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(rapport.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("Chargement en cours ...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                config config=new config();
                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL(config.lien+"?view=userGetTimeSheet&employee_id="+ids);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<DataFish> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataFish fishData = new DataFish();
                    fishData.date= json_data.getString("jour");
                    fishData.dj= json_data.getString("debut_de_journee");
                    fishData.dp= json_data.getString("debut_de_pause");
                    fishData.fp= json_data.getString("fin_de_pause");
                    fishData.fj= json_data.getString("fin_de_journee");
                    data.add(fishData);
                }

                // Setup and Handover data to recyclerview
                mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                mAdapter = new AdapterFish(rapport.this, data);
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(rapport.this));

            } catch (JSONException e) {
                Toast.makeText(rapport.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
