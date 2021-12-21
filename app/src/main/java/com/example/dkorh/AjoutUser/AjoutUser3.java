package com.example.dkorh.AjoutUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.isradeleon.notify.Notify;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.dkorh.Dashadmin;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.R;

import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;


public class AjoutUser3 extends AppCompatActivity {
    String nom2 ;
    String prenom2 ;
    String email2 ;
    String role2 ;
    String telephone2 ;
    String fax2 ;
    String nompx;
    String numpx;
    String prenomp;
    String manageur;
    String manageurid;
    String poste;
    String role ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_user3);

        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
        TextView textView8;
        TextView textView9;
        TextView textView10;


        textView=findViewById(R.id.nom);
        textView1=findViewById(R.id.prenom);
        textView2=findViewById(R.id.mail);
        textView3=findViewById(R.id.role);
        textView4=findViewById(R.id.phone);
        textView5=findViewById(R.id.fax);
        textView6=findViewById(R.id.phonep);
        textView7=findViewById(R.id.nomp);
        textView8=findViewById(R.id.prenomp);
        textView9=findViewById(R.id.manageur);
        textView10=findViewById(R.id.poste);








        Toolbar toolbar;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences prefx = getApplicationContext().getSharedPreferences("MyPrefxx", 0);
        toolbar = findViewById(R.id.home_toolbar100);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));
        SpannableString firstPart = new SpannableString("JIS ");
        SpannableString lastPart = new SpannableString(" RH");
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        manageur = pref.getString("namemanageur","vide");
        manageurid = prefx.getString("namemanageurid","vide");

        Log.i("manageur",manageur);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nom2 = extras.getString("keyx");
            prenom2 = extras.getString("keyx1");
            email2 = extras.getString("keyx2");
            role2 = extras.getString("keyx3");
            telephone2 = extras.getString("keyx4");
            fax2 = extras.getString("keyx5");
            numpx = extras.getString("keyx6");
            nompx = extras.getString("keyx7");
            prenomp = extras.getString("keyx8");
            poste = extras.getString("keyx9");
          //  manageur = extras.getString("keyx9");
        }

        Log.i("role",role2);

        if (role2.equals("EMPLOYES")){
            role="1";
        }
        else  if (role2.equals("MANAGEUR")){
            role="2";
        }
        else  if (role2.equals("RH")){
            role="0";
        }
        else  if (role2.equals("GERANT")){
            role="3";
        }
        Log.i("rolef",role);

        textView.setText(nom2);
        textView1.setText(prenom2);
        textView2.setText(email2);
        textView3.setText(role2);
        textView4.setText(telephone2);
        textView5.setText(fax2);
        textView6.setText(numpx);
        textView7.setText(nompx);
        textView8.setText(prenomp);
        textView9.setText(manageur);
        textView10.setText(poste);


        Button button,button1;
        button=findViewById(R.id.validexx);
        button1=findViewById(R.id.annulerx);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(AjoutUser3.this);
                flatDialog.setTitle("JIS RH")
                        .setSubtitle("ANNULER")
                        .setFirstButtonText("ANNULER")
                        .setSecondButtonText("CONTINUER")
                        .setBackgroundColor(Color.parseColor("#77b5fe"))
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AjoutUser3.this, Dashadmin.class);
                                startActivity(intent);
                                finish();
                                Animatoo.animateSlideDown(v.getContext());


                            }
                        })
                        .show();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        Handler handler = new  Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[11];
                field[0] = "name";
                field[1] = "surname";
                field[2] = "email";
                field[3] = "role";
                field[4] = "telephone";
                field[5] = "fax";
                field[6] = "professionnal_name";
                field[7] = "professionnal_number";
                field[8] = "professionnal_surname";
                field[9] = "manager_id";
                field[10] = "role";
                String[] data = new String[11];
                data[0] = nom2;
                data[1] = prenom2;
                data[2] = email2;
                data[3] = role;
                data[4] = telephone2;
                data[5] = fax2;
                data[6] = numpx;
                data[7] = nompx;
                data[8] = prenomp;
                data[9] = manageurid;
                data[10] = poste;

                PutData putData = new PutData(config.lien+"?view=createEmployee&birth=&email="+email2+"&password=123456789&role="+role+"&name="+nom2+"&surname="+prenom2+"&telephone="+telephone2+"&fax="+fax2+"&professionnal_number="+numpx+"&professionnal_name="+nompx+"&professionnal_surname="+prenomp+"&manager_id="+manageurid+"&fonction="+poste, "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.i("ajout", result);
                        JSON json = new JSON(result);
                        String firstTag = json.key("codeTraitement").toString();
                        Log.i("tag200", firstTag);
                        if (firstTag.equals("0")){
                            Notify.build(getApplicationContext())
                                    .setTitle("JIS COMPUTING")
                                    .setContent("Utulisateur ajouter avec succes")
                                    .setSmallIcon(R.drawable.logo)
                                    .setColor(R.color.bleu)
                                    .largeCircularIcon()
                                    .show();
                            Toasty.success(AjoutUser3.this, "Vous avez bien  ajouter le user", Toast.LENGTH_SHORT, true).show();
                            final FlatDialog flatDialog = new FlatDialog(AjoutUser3.this);
                            flatDialog.setTitle("JIS RH")
                                    .setSubtitle("UTULISATEUR AJOUTER AVEC SUCCES ")
                                    .setFirstButtonText("ALLEZ A L'ACCEUIL")
                                    .setSecondButtonText("AJOUTEZ UN NOUVEAU UTILISATEUR")
                                    .setBackgroundColor(Color.parseColor("#77b5fe"))
                                    .setSecondButtonColor(Color.parseColor("#FF0000"))
                                    .setFirstButtonColor(Color.parseColor("#008000"))
                                    .withFirstButtonListner(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Handler handler = new  Handler();
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(AjoutUser3.this, Dashadmin.class);
                                                    startActivity(intent);
                                                    finish();
                                                    Animatoo.animateSlideDown(v.getContext());
                                                }
                                            });
                                        }
                                    })
                                    .withSecondButtonListner(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(AjoutUser3.this, ajoutuser.class);
                                            startActivity(intent);
                                            finish();
                                            Animatoo.animateSlideDown(v.getContext());
                                        }
                                    })
                                    .show();
                        }
                        else if  (firstTag.equals("1")){
                            Toasty.success(AjoutUser3.this, "Erreur serveur 1 ", Toast.LENGTH_SHORT, true).show();
                        }

                        else if  (firstTag.equals("2")){
                            Toasty.success(AjoutUser3.this, "Email existe deja dans le systeme", Toast.LENGTH_SHORT, true).show();
                        }
                        else {
                            Toasty.error(AjoutUser3.this, "Erreur seveur", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }
                else {
                    Toasty.error(AjoutUser3.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

    }
});

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