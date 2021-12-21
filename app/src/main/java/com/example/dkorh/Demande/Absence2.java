package com.example.dkorh.Demande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.jisrh.R;


import java.util.Calendar;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

import static android.content.ContentValues.TAG;

public class Absence2 extends AppCompatActivity {
    TextView datedebut;
    TextView datefin;
    TextView heuredebut;
    TextView heurefin;
    EditText raison;
    String dated;
    String datef;
    String hd;
    String hf;
    String info;
    DatePickerDialog.OnDateSetListener listener;
    DatePickerDialog.OnDateSetListener listener2;
    TimePickerDialog.OnTimeSetListener listener3;
    TimePickerDialog.OnTimeSetListener listener4;


    String dateda;
    String datefa;
    String type,type2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence2);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbar200);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Button button;
        button =findViewById(R.id.button);
        datedebut=findViewById(R.id.datedebut);
        datefin=findViewById(R.id.datedefin);
        heuredebut=findViewById(R.id.heuredebut);
        heurefin=findViewById(R.id.heurefin);
        raison=findViewById(R.id.raison);
        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        String id = json.key("username").toString();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("cle");
            type2 = extras.getString("cle1");
        }
        datedebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Absence2.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                dated = year+"-"+month+"-"+day;
                dateda = day+"-"+month+"-"+year;
                datedebut.setText(dateda);
                Toasty.info(Absence2.this, " "+day+" "+month+" "+year, Toast.LENGTH_SHORT, true).show();
            }
        };
        datefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Absence2.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener2, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        listener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                 datef = year+"-"+month+"-"+day;
                datefa=day+"-"+month+"-"+year;
                datefin.setText(datefa);
                Toasty.info(Absence2.this, " "+day+"  "+month+"  "+year, Toast.LENGTH_SHORT, true).show();
            }
        };

       heuredebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hr = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Absence2.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener3, hr, min, true);
                timePickerDialog.setTitle("HEURE DEBUT");
                Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        listener3=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hd = hourOfDay + ":" + minute;
                heuredebut.setText(hd);
            }
        };
        heurefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hr = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Absence2.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener4, hr, min, true);
                timePickerDialog.setTitle("HEURE FIN");
                Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });
        listener4=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hf = hourOfDay + ":" + minute;
                heurefin.setText(hf);
            }
        };
     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String dd=dated;
             String df=datef;
             String hd2=hd;
             String hf2=hf;
             String r=raison.getText().toString();

/*
             String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
             Date d = new Date();
             CharSequence s  = DateFormat.format("yyyy-MM-d H:m", d.getTime());

             if (dated<datef){
                 Toasty.error(Absence2.this, "DATE INVAALIDE", Toast.LENGTH_SHORT, true).show();
             }
*/





             if (dd==null ){
                 Toasty.error(Absence2.this, "DATE DE DEBUT NECCESSAIRE", Toast.LENGTH_SHORT, true).show();
             }
             else  if (df==null ){
                 Toasty.error(Absence2.this, "DATE DE FIN NECCESSAIRE", Toast.LENGTH_SHORT, true).show();
             }
             else  if (hd2==null){
                 Toasty.error(Absence2.this, "HEURE DE DEBUT", Toast.LENGTH_SHORT, true).show();
             }
             else  if (hf2==null){
                 Toasty.error(Absence2.this, "HEUR DE FIN NECESSAIRE", Toast.LENGTH_SHORT, true).show();
             }
             else  if (r.isEmpty()){
                 Toasty.error(Absence2.this, "RAISON OBLIGATOIRE", Toast.LENGTH_SHORT, true).show();
             }

             else {
                 Intent intent = new Intent(Absence2.this, absence3.class);
                 intent.putExtra("clex",type);
                 intent.putExtra("cle1x",type2);
                 intent.putExtra("cle2x",dd);
                 intent.putExtra("cle3x",df);
                 intent.putExtra("cle4x",hd2);
                 intent.putExtra("cle5x",hf2);
                 intent.putExtra("cle6x",r);
                 intent.putExtra("cle7x",dateda);
                 intent.putExtra("cle8x",datefa);

                 startActivity(intent);
                 Animatoo.animateSwipeLeft(v.getContext());
             }
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