package com.example.dkorh.AjoutUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.example.dkorh.getAllUser.info;
import com.example.jisrh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AjoutUser2 extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    Button button;
    private Spinner spinner;
    private ArrayList<String> students;
    private JSONArray result;
    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;

    String nom1 ;
    String prenom1 ;
    String email1 ;
    String role1 ;
    String telephone1 ;
    String fax1 ;
    String numeromanageur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_user2);
        EditText editText;
        EditText editText1;
        EditText editText2;
        EditText editText3;
        Toolbar toolbar;

        toolbar = findViewById(R.id.home_toolbar5x);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));
        SpannableString firstPart = new SpannableString("JIS ");
        SpannableString lastPart = new SpannableString(" RH");
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        editText=findViewById(R.id.numerop);
        editText1=findViewById(R.id.nomp);
        editText2=findViewById(R.id.prenomp);
        editText3=findViewById(R.id.poste);
        button =findViewById(R.id.buttonx);
        getData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             nom1 = extras.getString("key");
             prenom1 = extras.getString("key1");
             email1 = extras.getString("key2");
             role1 = extras.getString("key3");
             telephone1 = extras.getString("key4");
             fax1 = extras.getString("key5");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numpx=editText.getText().toString();
                String nompx=editText1.getText().toString();
                String prenomp=editText2.getText().toString();
                String poste=editText3.getText().toString();
                if (numpx.isEmpty() || nompx.isEmpty() || prenomp.isEmpty() || poste.isEmpty()){
                    Toasty.error(AjoutUser2.this, "Tous les champs sont obligatoire", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    Intent i = new Intent(AjoutUser2.this, AjoutUser3.class);
                    i.putExtra("keyx",nom1);
                    i.putExtra("keyx1",prenom1);
                    i.putExtra("keyx2",email1);
                    i.putExtra("keyx3",role1);
                    i.putExtra("keyx4",telephone1);
                    i.putExtra("keyx5",fax1);
                    i.putExtra("keyx6",numpx);
                    i.putExtra("keyx7",nompx);
                    i.putExtra("keyx8",prenomp);
                    i.putExtra("keyx9",poste);
                    startActivity(i);
                    Animatoo.animateSlideRight(v.getContext());
                }
            }
        });
        students = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewCourse = (TextView) findViewById(R.id.textViewCourse);
        textViewSession = (TextView) findViewById(R.id.textViewSession);
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(info.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);
                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(info.JSON_ARRAY);
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
                students.add(json.getString(info.TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(AjoutUser2.this, android.R.layout.simple_spinner_dropdown_item, students));
    }
    private String getName(int position){
        String name="";
        try {
            JSONObject json = result.getJSONObject(position);
            name = json.getString(info.TAG_PRENOM);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("namemanageur",name);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String getCourse(int position){
        String course="";
        try {
            JSONObject json = result.getJSONObject(position);
            course = json.getString(info.TAG_DATE);

            SharedPreferences prefx = getApplicationContext().getSharedPreferences("MyPrefxx", 0);
            SharedPreferences.Editor editor = prefx.edit();
            editor.putString("namemanageurid",course);
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
            session = json.getString(info.TAG_NUMERO);
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

