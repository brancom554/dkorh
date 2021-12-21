package com.example.dkorh.Interim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.application.isradeleon.notify.Notify;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.dkorh.MainActivity;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.R;
import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class Interim2 extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    private Spinner spinner;
    private ArrayList<String> students;
    private JSONArray result;
    String id_em;
    String idem;
    String id_absence;
    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;
    SharedPreferences prefx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interim2);
        String info;
        Button button;
        Button button1;
        EditText editText;


        editText=findViewById(R.id.raisonxi);
        button =findViewById(R.id.buttonix2);
        button1=findViewById(R.id.button1ix2);

        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbarxdfsddfdf);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefx  = getApplicationContext().getSharedPreferences("MyPrefx", 0);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        String nom = json.key("nom").toString();
        String prenom = json.key("prenoms").toString();
        String poste = json.key("poste").toString();
        idem = json.key("employee_id").toString();

        Log.i("employee_id",idem);
        students = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinnerixx);
        spinner.setOnItemSelectedListener(Interim2.this);

        textViewName = (TextView) findViewById(R.id.textViewNamexx);
        textViewCourse = (TextView) findViewById(R.id.textViewCoursexx);
        textViewSession = (TextView) findViewById(R.id.textViewSessionxx);
        getData();
        id_absence=prefx.getString("id_absence","vide");
        id_em = pref.getString("id_employeer","vide");



        Log.i("MSG",id_absence);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tache=editText.getText().toString();
                if (tache.isEmpty()){
                    Toasty.error(Interim2.this, "ENUMERER LES TACHES", Toast.LENGTH_SHORT, true).show();
                }
               else if (id_absence.isEmpty() || id_absence==null || id_absence.equals("vide")){
                    Toasty.error(Interim2.this, "VOUS N'AVEZ AUCUNE DEMANDE D'ABSENCE VALIDER", Toast.LENGTH_SHORT, true).show();
                }
               else if (id_em.isEmpty() || id_em==null || id_em.equals("vide")){
                    Toasty.error(Interim2.this, "CHOISSISSEZ UN INTERIMAIRE", Toast.LENGTH_SHORT, true).show();
                }

                else {
                    Handler handler = new  Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "employee_id";
                            field[1] = "interim_id";
                            field[2] = "absence_id";
                            field[3] = "tasks";
                            String[] data = new String[4];
                            data[0] = idem;
                            data[1] = id_em;
                            data[2] = id_absence;
                            data[3] = tache;
                            PutData putData = new PutData(config.lien+"?view=setInterim&employee_id="+idem+"&interim_id="+id_em+"&absence_id="+id_absence+"&tasks="+tache+"", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("ajout", result);
                                    JSON json = new JSON(result);
                                    String firstTag = json.key("code").toString();
                                    Log.i("tag", firstTag);
                                    if (firstTag.equals("0")){
                                        Notify.build(getApplicationContext())
                                                .setTitle("JIS COMPUTING")
                                                .setContent("Interim ajouter avec succes")
                                                .setSmallIcon(R.drawable.logo)
                                                .setColor(R.color.bleu)
                                                .largeCircularIcon()
                                                .show();
                                        Toasty.success(Interim2.this, "Consultez votre boite mail pour voir la fiche d'interim", Toast.LENGTH_SHORT, true).show();
                                        Intent intent = new Intent(Interim2.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Animatoo.animateSlideUp(v.getContext());
                                    }
                                    else {
                                        Toasty.error(Interim2.this, "Erreur seveur", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                            else {
                                Toasty.error(Interim2.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });

                }


            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Interim2.this, MainActivity.class);
                startActivity(intent);
                finish();
                Animatoo.animateSlideDown(v.getContext());
            }
        });
    }
    private void getData(){
        StringRequest stringRequest = new StringRequest(config.lien+"?view=getAbsences2&employee_id="+idem+"+&type=1",
                new Response.Listener<String>() {
                    @Override  // idem
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(ListeAbsenceValide.JSON_ARRAY);
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
                students.add(json.getString(ListeAbsenceValide.TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(Interim2.this, android.R.layout.simple_spinner_dropdown_item, students));
    }
    private String getName(int position){
        String name="";
        try {
            JSONObject json = result.getJSONObject(position);
            name = json.getString(ListeAbsenceValide.TAG_PRENOM);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String getCourse(int position){
        String course="";
        try {
            JSONObject json = result.getJSONObject(position);
            course = json.getString(ListeAbsenceValide.TAG_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }
    public String getSession(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(ListeAbsenceValide.TAG_NUMERO);
            SharedPreferences.Editor editor = prefx.edit();
            editor.putString("id_absence",session);
            editor.commit();

            Log.i("IDXX",session);

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