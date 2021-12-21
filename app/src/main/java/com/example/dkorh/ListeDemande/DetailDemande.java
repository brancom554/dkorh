package com.example.dkorh.ListeDemande;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.dkorh.Dashadmin;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.R;
import com.example.dkorh.model.config;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class DetailDemande extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_demande);
        Button valider, rejecter;
        TextView nameTxt,usernameTxt;
        TextView textViewdd,textViewdf,textViewhd,textViewhf,poste,raison,typed,type2d,email;
        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbar1000x);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        nameTxt = (TextView) findViewById(R.id.nomd);
        usernameTxt = (TextView) findViewById(R.id.prenomd);
        textViewdd = (TextView) findViewById(R.id.datedebutd);
        textViewdf = (TextView) findViewById(R.id.datefind);
        textViewhd = (TextView) findViewById(R.id.heuredebutd);
        textViewhf = (TextView) findViewById(R.id.heurefind);
        poste = (TextView) findViewById(R.id.posted);
        //raison = (TextView) findViewById(R.id.raisond);
        typed = (TextView) findViewById(R.id.typed);
        type2d = (TextView) findViewById(R.id.type2d);
        email=findViewById(R.id.emaix);
        valider=findViewById(R.id.validerd);
        rejecter=findViewById(R.id.annulerd);



        Intent i=this.getIntent();






        String nom=i.getExtras().getString("NAME_KEY");
        String prenom=i.getExtras().getString("USERNAME_KEY");
        String emailx=i.getExtras().getString("EMAIL");
        String dd=i.getExtras().getString("DD");
        String df=i.getExtras().getString("DF");
        String hd=i.getExtras().getString("HD");
        String hf=i.getExtras().getString("HF");
        String role=i.getExtras().getString("RO");
      //  String raison2=i.getExtras().getString("RR");
        String type=i.getExtras().getString("TY1");
        String type2=i.getExtras().getString("TY2");
        String ID=i.getExtras().getString("ID");




        Log.i("ID",ID);



        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

         String info = pref.getString("token","vide");
         JSON json = new JSON(info);
         String firstTag = json.key("role").toString();
         String id = json.key("employee_id").toString();

        nameTxt.setText(nom);
        usernameTxt.setText(prenom);
        textViewdd.setText("DATE DE DEBUT :"+dd);
        textViewdf.setText("DATE DE FIN :"+df);
        textViewhd.setText("HEURE DE DEBUT :"+hd);
        textViewhf.setText("HEURE DE FIN :"+hf);
        poste.setText("RAISON :"+role);
        typed.setText("TYPE  :"+type);
        type2d.setText("SOUS TYPE :"+type2);
        email.setText("EMAIL :"+emailx);
        if (firstTag.equals("1")){

            valider.setEnabled(false);
            rejecter.setEnabled(false);
        }
            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Handler handler = new  Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[3];
                            field[0] = "employee_id";
                            field[1] = "absence_id";
                            field[2] = "status";
                            String[] data = new String[3];
                            data[0] = id;
                            data[1] = ID;
                            data[2] = "1";
                            PutData putData = new PutData(config.lien+"?view=updateStatus&employee_id="+id+"&absence_id="+ID+"&status=1", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("resultat", result);
                                    JSON json = new JSON(result);
                                    String firstTag = json.key("codeTraitement").toString();
                                    Log.i("codeTraitement", firstTag);
                                    if (firstTag.equals("0")){
                                        Toasty.success(DetailDemande.this, "DEMANDE D'ABSENCE VALIDER", Toast.LENGTH_SHORT, true).show();
                                        final FlatDialog flatDialog = new FlatDialog(DetailDemande.this);
                                        flatDialog.setTitle("JIS RH")
                                                .setSubtitle("DEMANDE VALIDER AVEC SUCCES ")
                                                .setFirstButtonText("ALLEZ A L'ACCEUIL")
                                                .setSecondButtonText("REGARDEZ LES DEMANDE EN COURS")
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
                                                                Intent intent = new Intent(DetailDemande.this, Dashadmin.class);
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
                                                        Intent intent = new Intent(DetailDemande.this, Mdemande.class);
                                                        startActivity(intent);
                                                        finish();
                                                        Animatoo.animateSlideDown(v.getContext());
                                                    }
                                                })
                                                .show();
                                    }

                                    else if (firstTag.equals("2"))
                                    {
                                        Toasty.error(DetailDemande.this, "VOUS AVEZ DEJA VALIDER CETTE DEMANDE", Toast.LENGTH_SHORT, true).show();
                                    }
                                    else {
                                        Toasty.success(DetailDemande.this, "DEMANDE D'ABSENCE VALIDER", Toast.LENGTH_SHORT, true).show();
                                     //   Toasty.error(DetailDemande.this, "IMPOSSIBLE DE VALIDER LA DEMANDE", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                            else {
                                Toasty.error(DetailDemande.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });
                }
            });



        rejecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new  Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[3];
                        field[0] = "employee_id";
                        field[1] = "absence_id";
                        field[2] = "status";
                        String[] data = new String[3];
                        data[0] = id;
                        data[1] = ID;
                        data[2] = "0";
                        PutData putData = new PutData(config.lien+"?view=updateStatus&employee_id="+id+"&absence_id="+ID+"&status=0", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Log.i("resultat", result);
                                JSON json = new JSON(result);
                                String firstTag = json.key("codeTraitement").toString();
                                Log.i("codeTraitement", firstTag);
                                if (firstTag.equals("0")){
                                    Toasty.success(DetailDemande.this, "DEMANDE D'ABSENCE REJECTER", Toast.LENGTH_SHORT, true).show();
                                    final FlatDialog flatDialog = new FlatDialog(DetailDemande.this);
                                    flatDialog.setTitle("JIS RH")
                                            .setSubtitle("DEMANDE REJECTER AVEC SUCCES ")
                                            .setFirstButtonText("ALLEZ A L'ACCEUIL")
                                            .setSecondButtonText("REGARDEZ LES DEMANDE EN COURS")
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
                                                            Intent intent = new Intent(DetailDemande.this, Dashadmin.class);
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
                                                    Intent intent = new Intent(DetailDemande.this, Mdemande.class);
                                                    startActivity(intent);
                                                    finish();
                                                    Animatoo.animateSlideDown(v.getContext());
                                                }
                                            })
                                            .show();
                                }

                                else if (firstTag.equals("2"))
                                {
                                    Toasty.error(DetailDemande.this, "VOUS AVEZ DEJA VALIDER CETTE DEMANDE", Toast.LENGTH_SHORT, true).show();
                                }
                                else {
                                 //   Toasty.error(DetailDemande.this, "IMPOSSIBLE DE VALIDER LA DEMANDE", Toast.LENGTH_SHORT, true).show();
                                    Toasty.success(DetailDemande.this, "DEMANDE D'ABSENCE VALIDER", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }
                        else {
                            Toasty.error(DetailDemande.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
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