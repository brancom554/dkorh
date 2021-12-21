package com.example.dkorh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import android.widget.EditText;
import android.widget.Toast;

import com.application.isradeleon.notify.Notify;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.R;
import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class Changepass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        Toolbar toolbar;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        toolbar = findViewById(R.id.home_toolbar5xxcd);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));
        SpannableString firstPart = new SpannableString("JIS ");
        SpannableString lastPart = new SpannableString(" RH");
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button button;
        EditText editText, editTextx,fef;
        editText=findViewById(R.id.mdpa);
        editTextx=findViewById(R.id.mdpn);
        fef=findViewById(R.id.mdpn2);

        button =findViewById(R.id.buttonx45);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mdpa=editText.getText().toString();
                String mdpn=editTextx.getText().toString();
               String mdpc=fef.getText().toString();
                if (mdpa.isEmpty() || mdpn.isEmpty() ){
                    Toasty.error(Changepass.this, "Tous les champs sont obligatoire", Toast.LENGTH_SHORT, true).show();
                }
                else if (!mdpn.equals(mdpc)){

                    Toasty.error(Changepass.this, "LE NOUVEAU MOT DE PASSE N'EST PAS CORRECTE", Toast.LENGTH_SHORT, true).show();

                }
                else {

                    Handler handler = new  Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String info = pref.getString("token","vide");
                            JSON json = new JSON(info);
                            String  ids = json.key("employee_id").toString();
                            String[] field = new String[3];
                            field[0] = "employee_id";
                            field[1] = "new_password";
                            field[2] = "old_password";
                            String[] data = new String[3];
                            data[0] = ids;
                            data[1] = mdpa;
                            data[2] = mdpn;
                            PutData putData = new PutData(config.lien+"?view=updatePassword&employee_id="+ids+"&new_password="+mdpn+"&old_password="+mdpa, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("ajout", result);
                                    JSON jsonx = new JSON(result);
                                    String firstTag = jsonx.key("codeTraitement").toString();
                                    Log.i("tag", firstTag);
                                    if (firstTag.equals("0")){
                                        Notify.build(getApplicationContext())
                                                .setTitle("JIS COMPUTING")
                                                .setContent("Felicitation vous avez modifier votre mot de passe")
                                                .setSmallIcon(R.drawable.logo)
                                                .setColor(R.color.bleu)
                                                .largeCircularIcon()
                                                .show();
                                        Toasty.success(Changepass.this, "Succes", Toast.LENGTH_SHORT, true).show();






                                    }
                                    else if  (firstTag.equals("1")){
                                        Toasty.error(Changepass.this, "Erreur serveur 1 ", Toast.LENGTH_SHORT, true).show();
                                    }
                                    else if  (firstTag.equals("2")){
                                        Toasty.error(Changepass.this, "L'ancien vos de passe n'est pas correcte", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }

                            else {
                                Toasty.error(Changepass.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });

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



