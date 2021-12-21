package com.example.dkorh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.application.isradeleon.notify.Notify;
import com.baoyz.widget.PullRefreshLayout;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.dkorh.Event.Evennement;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.dkorh.Demande.DemandeAbsence;

import com.example.dkorh.Interim.Interim;
import com.example.dkorh.ListeDemande.Mdemande;
import com.example.jisrh.R;
import com.example.dkorh.Rapport.rapport;
import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Date;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;


public class MainActivity extends AppCompatActivity {
    TextView textView ;

    Boolean login;
    String info;
    String nom;
    String idem;
    String firstTagX;
    Boolean interim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.meteo);

        Toolbar toolbar;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));
        SpannableString firstPart = new SpannableString("JIS ");
        SpannableString lastPart = new SpannableString(" RH");
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));

        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        final String[] firstTag = {json.key("role").toString()
        };
        nom = json.key("username").toString();
        idem = json.key("employee_id").toString();
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        Date d = new Date();
        CharSequence s  = DateFormat.format("yyyy-MM-d H:m", d.getTime());
        textView.setText("Tableau de Bord: "+currentDateTimeString+"  "+": "+nom);
        CardView cardView1;
        CardView cardView2;
        CardView cardView3;
        CardView cardView4;
        CardView cardView5;
        CardView cardView6;
        CardView cardView7;

        cardView1 =findViewById(R.id.cad1);
        cardView2 =findViewById(R.id.cad2);
        cardView3 =findViewById(R.id.cad3);
        cardView4 =findViewById(R.id.cad4);
        cardView5 =findViewById(R.id.cad5);
        cardView6=findViewById(R.id.interim);
        cardView7=findViewById(R.id.even);



        if (firstTag[0].equals("0")){
            startActivity(new Intent(MainActivity.this,Dashadmin.class));
            finish();
        }

        else if(firstTag[0].equals("2")){
            startActivity(new Intent(MainActivity.this,Dashadmin.class));
            finish();
        }
        else if(firstTag[0].equals("3")){
            startActivity(new Intent(MainActivity.this,Dashadmin.class));
            finish();
        }

        else if(firstTag[0].equals("4")){
            startActivity(new Intent(MainActivity.this,Dashadmin.class));
            finish();
        }
        login =  pref.getBoolean("key_name", false);
        if (login==false){
            startActivity(new Intent(MainActivity.this,login.class));
            finish();
        }
        Handler handler = new  Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "employee_id";
                String[] data = new String[1];
                data[0] = idem;
                PutData putData = new PutData(config.lien+"?view=getAbsences3&employee_id="+idem, "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.i("ajout", result);
                        JSON json = new JSON(result);
                        firstTagX= json.key("codeTraitement").toString();
                        Log.i("tag", firstTagX);
                        if (firstTagX.equals("1")){
                          //  cardView6.setEnabled(true);
                            interim=true;
                        }
                        else {
                          //  cardView6.setEnabled(false);
                            interim=false;
                        }
                    }
                }
                else {
                    Toasty.error(MainActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), presence.class);
                startActivity(intent);
                Animatoo.animateSlideUp(v.getContext());
            }
        });









        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), presence.class);
                startActivity(intent);
                Animatoo.animateSlideUp(v.getContext());
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DemandeAbsence.class);
                startActivity(intent);
                Animatoo.animateWindmill(v.getContext());
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), rapport.class);
                startActivity(intent);
                Animatoo.animateSlideRight(v.getContext());
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        if (interim){
              Intent intent = new Intent(getApplicationContext(), Interim.class);
               startActivity(intent);
               Animatoo.animateSlideRight(v.getContext());
              }
        if (!interim){
              Toasty.error(MainActivity.this, "IMPOSSIBLE D'ACCEDER AU MENU INTERIM, VOUS N'AVEZ AUCUNE DEMANDE D'ABSENCE VALIDER", Toast.LENGTH_LONG, true).show();
                }
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mdemande.class);
                startActivity(intent);
                Animatoo.animateSlideUp(v.getContext());
            }
        });

        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Evennement.class);
                startActivity(intent);
                Animatoo.animateSlideRight(v.getContext());
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(MainActivity.this);
                flatDialog.setTitle("JIS RH")
                        .setSubtitle("JOUR ET HEURE DE DECONNEXION  :"+currentDateTimeString)
                        .setFirstButtonText("DECONNEXION")
                        .setSecondButtonText("ANNULER")
                        .setBackgroundColor(Color.parseColor("#77b5fe"))
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferences.Editor editor = pref.edit();
                                editor.remove("key_name");
                                editor.remove("token");
                                editor.clear();
                                editor.commit();
                                login=false;
                                startActivity(new Intent(MainActivity.this,login.class));
                                finish();
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.profil:
                Intent i = new Intent(MainActivity.this, profil.class);
                startActivity(i);
                Animatoo.animateSwipeRight(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
        public  String date(){
        String time = java.text.DateFormat.getDateTimeInstance().format(new Date());
        return time;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Veuillez cliquer à nouveau sur RETOUR pour quitter", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}