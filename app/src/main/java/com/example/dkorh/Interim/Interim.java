package com.example.dkorh.Interim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.dkorh.MainActivity;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class Interim extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    String info;
    String idx;
    private Spinner spinner;
    private ArrayList<String> students;
    private JSONArray result;

    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interim);
        Button button;
        Button button1;
        button =findViewById(R.id.buttonix);
        button1=findViewById(R.id.button1ix);
        TextView textView;
        TextView textView1;
        TextView textView2;
        textView=findViewById(R.id.nomdi);
        textView1=findViewById(R.id.prenomdi);
        textView2=findViewById(R.id.postei);
        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbarxdfsd);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        String nom = json.key("nom").toString();
        String prenom = json.key("prenoms").toString();
        String poste = json.key("poste").toString();
         idx = json.key("employee_id").toString();
        textView.setText("NOM  : "+nom);
        textView1.setText("PRENOM : "+prenom);
        textView2.setText("POSTE : "+poste);

        getData();

        students = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinneri);
        spinner.setOnItemSelectedListener(Interim.this);

        textViewName = (TextView) findViewById(R.id.textViewNamei);
        textViewCourse = (TextView) findViewById(R.id.textViewCoursei);
        textViewSession = (TextView) findViewById(R.id.textViewSessioni);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String id_employeer  = pref.getString("id_employeer","vide");

                if (id_employeer.isEmpty()){
                    Toasty.error(Interim.this, "Tous les champs sont obligatoire", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    Log.i("emp",id_employeer);
                    Intent i = new Intent(Interim.this, Interim2.class);
                    i.putExtra("keyx",id_employeer);
                    startActivity(i);
                    Animatoo.animateSlideRight(v.getContext());
                }
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Interim.this, MainActivity.class);
                startActivity(intent);
                finish();
                Animatoo.animateSlideDown(v.getContext());
            }
        });

    }


    private void getData(){
        StringRequest stringRequest = new StringRequest(Userinfo.DATA_URL+"&employee_id="+idx,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);
                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Userinfo.JSON_ARRAY);
                            //Calling method getStudents to get the students from the JSON Array
                            getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getStudents(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                students.add(json.getString(Userinfo.TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(Interim.this, android.R.layout.simple_spinner_dropdown_item, students));
    }
    private String getName(int position){
        String name="";
        try {
            JSONObject json = result.getJSONObject(position);
            name = json.getString(Userinfo.TAG_PRENOM);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String getCourse(int position){
        String course="";
        try {
            JSONObject json = result.getJSONObject(position);
            course = json.getString(Userinfo.TAG_DATE);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("id_employeer",course);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }
    public String getSession(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Userinfo.TAG_NUMERO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textViewName.setText(getName(position));
        textViewCourse.setText(getCourse(position));
        textViewSession.setText(getSession(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewName.setText("");
        textViewCourse.setText("");
        textViewSession.setText("");
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}