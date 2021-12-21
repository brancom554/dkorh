package com.example.dkorh.AjoutUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.dkorh.Dashadmin;
import com.example.jisrh.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;


public class ajoutuser extends AppCompatActivity {
    private SmartMaterialSpinner<String> spProvince;
    private List<String> provinceList;
    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    Button button;
    Button annuler;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoutuser);
        Toolbar toolbar;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        toolbar = findViewById(R.id.home_toolbar5);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));
        SpannableString firstPart = new SpannableString("JIS ");
        SpannableString lastPart = new SpannableString(" RH");
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initSpinner();

          editText =findViewById(R.id.name);
          editText2 =findViewById(R.id.prenom);
          editText3 =findViewById(R.id.email);
          editText4=findViewById(R.id.telephone);
          editText5=findViewById(R.id.fax);
          button=findViewById(R.id.button99);
          annuler= findViewById(R.id.annuler25);


           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   String nom = editText.getText().toString();
                   String prenom =editText2.getText().toString();
                   String email=editText3.getText().toString();
                   String telephone=editText4.getText().toString();
                   String fax=editText5.getText().toString();

                   if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty()){
                       Toasty.error(ajoutuser.this, "COMPLETEZ TOUTE LES INFORMATIONS", Toast.LENGTH_SHORT, true).show();
                   }

                   else  if (!isEmailValid(email)){

                       Toasty.error(ajoutuser.this, "EMAIL INVALIDE", Toast.LENGTH_SHORT, true).show();
                   }

                   else {
                       Intent i = new Intent(ajoutuser.this, AjoutUser2.class);
                       i.putExtra("key",nom);
                       i.putExtra("key1",prenom);
                       i.putExtra("key2",email);
                       i.putExtra("key3",role);

                       Log.i("role",role);

                       i.putExtra("key4",telephone);
                       i.putExtra("key5",fax);
                       startActivity(i);
                       Animatoo.animateSwipeRight(v.getContext());
                   }
               }
           });

    annuler.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ajoutuser.this, Dashadmin.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideDown(v.getContext());
    }
});

    }

    private void initSpinner() {
        spProvince = findViewById(R.id.spinner1);
        provinceList = new ArrayList<>();
        provinceList.add("EMPLOYES");
        provinceList.add("MANAGEUR");
        provinceList.add("RH");
        provinceList.add("GERANT");
        spProvince.setItem(provinceList);
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toasty.info(ajoutuser.this, provinceList.get(position), Toast.LENGTH_SHORT).show();
                role =provinceList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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