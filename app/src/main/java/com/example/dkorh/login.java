package com.example.dkorh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.jisrh.R;
import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email;
        TextView textView;
        EditText mdp;
        ImageView imageView;
        Button button;
        email=findViewById(R.id.etEmail);
        mdp =findViewById(R.id.etpass);
        button=findViewById(R.id.button);
        config config=new config();
        textView=findViewById(R.id.forget);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), forget.class);
                Animatoo.animateSlideUp(v.getContext());
                startActivity(intent);
            }
        });

        imageView=findViewById(R.id.show_pass_btn);
      imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(mdp.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                  mdp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
              }
              else{
                  mdp.setTransformationMethod(PasswordTransformationMethod.getInstance());
              }
          }
      });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1=  email.getText().toString();
                String value2= mdp.getText().toString();
                if (value1.isEmpty()){
                    Toasty.error(login.this, "Email invalide", Toast.LENGTH_SHORT, true).show();
                }
                if (value2.isEmpty()){
                    Toasty.error(login.this, "Mot de passe vide", Toast.LENGTH_SHORT, true).show();
                }
                else {
                   // login(value1,value2);
                    Handler handler = new  Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "mdp";
                            String[] data = new String[2];
                            data[0] = value1;
                            data[1] = value2;
                            PutData putData = new PutData(config.lien+"?view=login&email="+value1+"&mdp="+value2, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("resultat", result);
                                    JSON json = new JSON(result);
                                    String firstTag = json.key("role").toString();
                                    String compte = json.key("code").toString();

                                    Log.i("compte", compte);

                                    if (compte.equals("0")){

                                        if (firstTag.equals("1")){
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putBoolean("key_name", true);
                                            editor.putString("token",result);
                                            editor.commit();
                                            Intent intent = new Intent(login.this, MainActivity.class);
                                            startActivity(intent);
                                            Animatoo.animateSlideUp(v.getContext());
                                            finish();
                                        }
                                        else if  (firstTag.equals("0")){
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putBoolean("key_name", true);
                                            editor.putString("token",result);
                                            editor.commit();
                                            Intent intent = new Intent(login.this, Dashadmin.class);
                                            startActivity(intent);
                                            Animatoo.animateSlideUp(v.getContext());
                                            finish();
                                        }

                                        else if  (firstTag.equals("2")){
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putBoolean("key_name", true);
                                            editor.putString("token",result);
                                            editor.commit();
                                            Intent intent = new Intent(login.this, Dashadmin.class);
                                            startActivity(intent);
                                            Animatoo.animateSlideUp(v.getContext());
                                            finish();
                                        }
                                        else if  (firstTag.equals("3")){
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putBoolean("key_name", true);
                                            editor.putString("token",result);
                                            editor.commit();
                                            Intent intent = new Intent(login.this, Dashadmin.class);
                                            startActivity(intent);
                                            Animatoo.animateSlideUp(v.getContext());
                                            finish();
                                        }
                                    }
                                    else if (compte.equals("1")){
                                        Toasty.error(login.this, "Erreur Username ou mot de passe invalide", Toast.LENGTH_SHORT, true).show();
                                    }
                                    else if (compte.equals("2")){
                                        Toasty.error(login.this, "Compte desactivé", Toast.LENGTH_SHORT, true).show();
                                    }
                                    else {
                                        Toasty.error(login.this, "Erreur Username ou mot de passe invalide", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                            else {
                                Toasty.error(login.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });
                }
            }
        });
    }

}