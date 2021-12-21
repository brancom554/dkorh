package com.example.dkorh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.R;

import java.util.Date;

import eu.amirs.JSON;

public class profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        TextView textView;
        Button button,button1;
        textView=findViewById(R.id.namead);
        button1=findViewById(R.id.mdpxxx);
        button=findViewById(R.id.dec);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String info;
        String nom;
        String prenom;
        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        String firstTag = json.key("is_hr").toString();
        nom = json.key("nom").toString();
        prenom = json.key("prenoms").toString();
        Log.i("name", nom);
        Log.i("surname", prenom);
        textView.setText(nom+" "+prenom);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profil.this, Changepass.class);
                startActivity(i);
                Animatoo.animateSwipeRight(v.getContext());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(profil.this);
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
                                startActivity(new Intent(profil.this,login.class));
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
}