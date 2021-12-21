package com.example.dkorh.Demande;

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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.dkorh.MainActivity;
import com.example.jisrh.R;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;

public class DemandeAbsence extends AppCompatActivity {

    String info;
    Spinner type,soustype;
    String type1,type2;
    final String types[] =  {
            "Absences legales",
            "Congés annuels",
            "Permission "
    };

    final String absencelagal[] ={
            "Dècès d'un proche",
            "Mariage",
            "Parternité",
            "Marternité"
    };

    final String conge[] ={
            "Congés annuels"
    };


    final String autres[] =  {
            "Raison exceptionnelle"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_absence);
        Button button;
        Button button1;
        button =findViewById(R.id.button);
        button1=findViewById(R.id.button1);
        TextView textView;
        TextView textView1;
        TextView textView2;
        textView=findViewById(R.id.nomd);
        textView1=findViewById(R.id.prenomd);
        textView2=findViewById(R.id.poste);
        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbarx);
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
        String id = json.key("username").toString();
        textView.setText("NOM  : "+nom);
        textView1.setText("PRENOM : "+prenom);
        textView2.setText("POSTE : "+poste);
        final ArrayAdapter<String> adapter;
        type = findViewById(R.id.divisionSP);
        soustype = findViewById(R.id.upozellaSP);

        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,types);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type1= types[i];
                if(i==0){
                    final ArrayAdapter<String> adapter5;
                    adapter5= new ArrayAdapter<String>(DemandeAbsence.this,android.R.layout.simple_spinner_dropdown_item,absencelagal);
                    soustype.setAdapter(adapter5);
                    soustype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            type2 = absencelagal[i];
                            if(i==0){
                                Toast.makeText(DemandeAbsence.this, ""+type2, Toast.LENGTH_SHORT).show();
                            }if(i==1){
                                Toast.makeText(DemandeAbsence.this, ""+type2, Toast.LENGTH_SHORT).show();
                            }
                            if(i==2){
                                Toast.makeText(DemandeAbsence.this, ""+type2, Toast.LENGTH_SHORT).show();
                            }
                            if(i==3){
                                Toast.makeText(DemandeAbsence.this, ""+type2, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                if(i==1){
                    final ArrayAdapter<String> adapter5;
                    adapter5= new ArrayAdapter<String>(DemandeAbsence.this,android.R.layout.simple_spinner_dropdown_item,conge);
                    soustype.setAdapter(adapter5);
                    soustype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            type2 = conge[i];
                            if(i==0){
                                Toast.makeText(DemandeAbsence.this, ""+type2, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

                if(i==2){
                    final ArrayAdapter<String> adapter5;
                    adapter5= new ArrayAdapter<String>(DemandeAbsence.this,android.R.layout.simple_spinner_dropdown_item,autres);
                    soustype.setAdapter(adapter5);
                    soustype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            type2 = autres[i];
                            if(i==0){
                                Toast.makeText(DemandeAbsence.this, ""+type2, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                Toast.makeText(DemandeAbsence.this, ""+type1, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (type1.isEmpty() || type2.isEmpty()){
                 Toasty.error(DemandeAbsence.this, "Erreur", Toast.LENGTH_SHORT, true).show();
             }
             else {

                 Log.i("type1",type1);
                 Log.i("type2",type2);


                 Intent intent = new Intent(DemandeAbsence.this, Absence2.class);

                 intent.putExtra("cle",type1);
                 intent.putExtra("cle1",type2);
                 startActivity(intent);
                 Animatoo.animateSwipeLeft(v.getContext());
             }
         }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemandeAbsence.this, MainActivity.class);
                startActivity(intent);
                finish();
                Animatoo.animateSlideDown(v.getContext());
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