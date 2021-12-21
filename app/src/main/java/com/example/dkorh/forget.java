package com.example.dkorh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.jisrh.R;
import com.example.dkorh.model.config;
import com.example.dkorh.model.myDbAdapter;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class forget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        EditText email;
        config config=new config();
        TextView textView;
        Button button;
        email=findViewById(R.id.etEmail);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1=  email.getText().toString();
                if (value1.isEmpty()){
                    Toasty.error(forget.this, "Email invalide", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    // login(value1,value2);
                    Handler handler = new  Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[1];
                            field[0] = "email";
                            String[] data = new String[1];
                            data[0] = value1;
                            PutData putData = new PutData(config.lien+"?view=forgetPassword&email="+value1, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("resultat", result);
                                    JSON json = new JSON(result);
                                    String firstTag = json.key("codeTraitement").toString();
                                    Log.i("message", firstTag);
                                    if (firstTag.equals("0")){
                                        Toasty.success(forget.this, "Nouveau mot de passe envoyé avec succès sur votre mail", Toast.LENGTH_SHORT, true).show();

                                        Intent intent = new Intent(forget.this, login.class);
                                        Animatoo.animateSlideUp(v.getContext());
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toasty.error(forget.this, "Erreur", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                            else {
                                Toasty.error(forget.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });
                }
            }
        });

        myDbAdapter helper;
        helper = new myDbAdapter(this);










    }
}