package com.example.dkorh.Demande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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

import com.application.isradeleon.notify.Notify;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.dkorh.MainActivity;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.R;

import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class absence3 extends AppCompatActivity {
    String nom2 ;
    String prenom2;
    String type ;
    String type2 ;
    String dd ;
    String df ;
    String hd;
    String hf;
    String r;
    String poste;
    String info;
    String id;
    String dateaa;
    String dateff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence3);
        Toolbar toolbar;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        toolbar = findViewById(R.id.home_toolbar500x);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        textView=findViewById(R.id.nom);
        textView1=findViewById(R.id.prenom);
        textView2=findViewById(R.id.type);
        textView3=findViewById(R.id.type2);
        textView4=findViewById(R.id.dd);
        textView5=findViewById(R.id.df);
        textView6=findViewById(R.id.hd);
        textView7=findViewById(R.id.hf);
        textView8=findViewById(R.id.r);
        textView9=findViewById(R.id.poste);
        info = pref.getString("token","vide");

        JSON json = new JSON(info);
         nom2 = json.key("nom").toString();
         prenom2 = json.key("prenoms").toString();
         poste = json.key("poste").toString();
         id = json.key("employee_id").toString();

         Log.i("idu",id);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("clex");
            type2 = extras.getString("cle1x");
            dd = extras.getString("cle2x");
            df = extras.getString("cle3x");
            hd = extras.getString("cle4x");
            hf = extras.getString("cle5x");
            r = extras.getString("cle6x");

            dateaa = extras.getString("cle7x");
            dateff = extras.getString("cle8x");
        }
        textView.setText(nom2);
        textView1.setText(prenom2);
        textView2.setText(type);
        textView3.setText(type2);
        textView4.setText(dateaa);
        textView5.setText(dateff);
        textView6.setText(hd);
        textView7.setText(hf);
        textView8.setText(r);
        textView9.setText(poste);
        Button button,button1;
        button=findViewById(R.id.validexx);
        button1=findViewById(R.id.annulerx);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(absence3.this);
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
                                Intent intent = new Intent(absence3.this, MainActivity.class);
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
                        String[] field = new String[8];
                        field[0] = "start_date";
                        field[1] = "end_date";
                        field[2] = "reason";
                        field[3] = "employee_id";
                        field[4] = "type";
                        field[5] = "sous_type";
                        field[6] = "start_hour";
                        field[7] = "end_hour";
                        String[] data = new String[8];
                        data[0] = dd;
                        data[1] = df;
                        data[2] = r;
                        data[3] = id;
                        data[4] = type;
                        data[5] = type2;
                        data[6] = hd;
                        data[7] = hf;
                        PutData putData = new PutData(config.lien+"?view=createAbsence&start_date="+dd+"&end_date="+df+"&reason="+r+"&employee_id="+id+"&type="+type+"&sous_type="+type2+"&start_hour="+hd+"&end_hour="+hf+"", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Log.i("answer", result);
                                JSON json = new JSON(result);
                                String firstTag = json.key("codeTraitement").toString();
                                Log.i("tag", firstTag);
                                if (firstTag.equals("0")){
                                    Toasty.success(absence3.this, "ENVOYEZ AVEC SUCCES", Toast.LENGTH_SHORT, true).show();
                                    Notify.build(getApplicationContext())
                                            .setTitle("JIS COMPUTING")
                                            .setContent("Felicitation vous avez envoyez une demande d'absence")
                                            .setSmallIcon(R.drawable.logo)
                                            .setColor(R.color.bleu)
                                            .largeCircularIcon()
                                            .show();
                                         Intent intent = new Intent(absence3.this,  MainActivity.class);
                                         startActivity(intent);
                                         finish();
                                         Animatoo.animateSlideDown(v.getContext());
                                }
                                else if  (firstTag.equals("1")){
                                    Toasty.success(absence3.this, "VOUS AVEZ DEJA UNE DEMANDE EN COURS", Toast.LENGTH_SHORT, true).show();
                                }
                                else if  (firstTag.equals("2")){
                                    Toasty.error(absence3.this, "DATE INVALIDE", Toast.LENGTH_SHORT, true).show();
                                }
                                else {
                                    Toasty.error(absence3.this, "Erreur seveur", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }
                        else {
                            Toasty.error(absence3.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
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