package com.example.dkorh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import com.baoyz.widget.PullRefreshLayout;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.dkorh.Event.Evennement;
import com.example.dkorh.Fiche.Fiche;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.dkorh.AjoutUser.ajoutuser;
import com.example.dkorh.ListeDemande.Mdemande;
import com.example.jisrh.R;
import com.example.dkorh.Rapport.generapport;
import com.example.dkorh.getAllUser.ListeUser;


import java.util.Date;

import eu.amirs.JSON;

public class Dashadmin extends AppCompatActivity {
    TextView textView ;
  //  TextView textView2;
    Boolean login;
    String info;
    String nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashadmin);
        Toolbar toolbar;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));
        SpannableString firstPart = new SpannableString("DKO ");
        SpannableString lastPart = new SpannableString(" RH");
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));
        textView=findViewById(R.id.meteo);
      //  textView2=findViewById(R.id.namex);
        info = pref.getString("token","vide");
        Log.i("info", info);
        JSON json = new JSON(info);
        String firstTag = json.key("role").toString();
        nom = json.key("username").toString();
        Log.i("name", nom);
        //textView2.setText(nom);
        Date d = new Date();
        CharSequence s  = DateFormat.format("yyyy-MM-d H:m", d.getTime());
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        textView.setText("Tableau de Bord: "+currentDateTimeString+"  "+": "+nom);
        if (firstTag.equals("1")){
            startActivity(new Intent(Dashadmin.this,login.class));
            finish();
        }
        login =  pref.getBoolean("key_name", false);
        if (login==false){
            startActivity(new Intent(Dashadmin.this,login.class));
            finish();
        }



        CardView cardView1;
        CardView cardView2;
        CardView cardView3;
        CardView cardView4;
        CardView cardView5;
        CardView cardView6;
        CardView cardView7;
        CardView cardView8;
        cardView1 =findViewById(R.id.cad1x);
        cardView2 =findViewById(R.id.cad2x);
        cardView3 =findViewById(R.id.cad3x);
        cardView4 =findViewById(R.id.cad4x);
        cardView5 =findViewById(R.id.ajoute);
        cardView7 =findViewById(R.id.even);
        cardView6 =findViewById(R.id.listeuser);
        cardView8=findViewById(R.id.fiche);




        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Evennement.class);
                startActivity(intent);
                Animatoo.animateSlideUp(v.getContext());
            }
        });


        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fiche.class);
                startActivity(intent);
                Animatoo.animateSlideUp(v.getContext());
            }
        });


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profil.class);
                startActivity(intent);
                Animatoo.animateSlideUp(v.getContext());
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), generapport.class);
                startActivity(intent);
                Animatoo.animateShrink(v.getContext());
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Mdemande.class);
                startActivity(intent);
                Animatoo.animateDiagonal(v.getContext());

            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter un users
                Intent intent = new Intent(Dashadmin.this, ajoutuser.class);
                startActivity(intent);
             //  Animatoo.animateSlideLeft(v.getContext()); // gauche
               Animatoo.animateCard(v.getContext()); // gauche foncer good
              //   Animatoo.animateShrink(v.getContext()); // foncer
              //  Animatoo.animateDiagonal(v.getContext()); // dialog
               //  Animatoo.animateSlideUp(v.getContext());
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListeUser.class);
                startActivity(intent);
                Animatoo.animateShrink(v.getContext());
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(Dashadmin.this);
                flatDialog.setTitle("JIS RH")
                        .setSubtitle("DECONNEXION")
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

                                Intent intent = new Intent(getApplicationContext(), login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Animatoo.animateSlideLeft(v.getContext());
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
    public  String date(){
        String time = java.text.DateFormat.getDateTimeInstance().format(new Date());
        return time;
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
                Intent i = new Intent(Dashadmin.this, profil.class);
                startActivity(i);
                Animatoo.animateSwipeRight(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}