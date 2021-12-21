package com.example.dkorh.Rapport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.isradeleon.notify.Notify;
import com.example.jisrh.R;

import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

import static android.content.ContentValues.TAG;

public class generapport extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener listener;
    DatePickerDialog.OnDateSetListener listener2;

    TextView datedebut;
    TextView datefin;

    String dated;
    String datef;

    String dateda;
    String datefa;

    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generapport);
        Toolbar toolbar;
        config config=new config();
        String info;
        String nom;
        datefin=findViewById(R.id.datefinx);
        editText=findViewById(R.id.mailr);
        button =findViewById(R.id.buttonx);
        datedebut=findViewById(R.id.datedebutx);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);



        toolbar = findViewById(R.id.home_toolbar55);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#ff8d32")));
        SpannableString firstPart = new SpannableString("JIS ");
        SpannableString lastPart = new SpannableString(" RH");
        firstPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.bleu)), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastPart.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.blanc)), 0, lastPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(TextUtils.concat(firstPart, lastPart));

        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        String firstTag = json.key("employee_id").toString();



        datedebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        generapport.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
              //  dated = month + "/" + day + "/" + year;
                dated =year+"-"+month+"-"+day;

                dateda = day+"-"+month+"-"+year;

                datedebut.setText(dateda);
                Log.i("idem", firstTag);

                Log.i("date", dated);

                Toasty.info(generapport.this, " "+day+" "+month+" "+year, Toast.LENGTH_SHORT, true).show();
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
                        generapport.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener2, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        listener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                //  dated = month + "/" + day + "/" + year;
                datef =year+"-"+month+"-"+day;
                datefa=day+"-"+month+"-"+year;
                datefin.setText(datefa);
                Log.i("idem", firstTag);

                Log.i("date", datef);

                Toasty.info(generapport.this, " "+day+" "+month+" "+year, Toast.LENGTH_SHORT, true).show();
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dd=dated;

                String df=datef;

                String emailx=editText.getText().toString();


                if (!isEmailValid(emailx)){

                    Toasty.error(generapport.this, "Nous ne pouvons pas envoyer un rapport sur un mail invalide", Toast.LENGTH_SHORT, true).show();

                }

               else if (dd==null ){
                    Toasty.error(generapport.this, "DATE DE DEBUT NECCESSAIRE", Toast.LENGTH_SHORT, true).show();
                }

                else if (df==null ){
                    Toasty.error(generapport.this, "DATE DE FIN NECCESSAIRE", Toast.LENGTH_SHORT, true).show();
                }


             else {
                    Handler handler = new  Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "employee_id";
                            field[1] = "dd";
                            field[2] = "email";
                            field[3] = "df";
                            String[] data = new String[4];
                            data[0] = firstTag;
                            data[1] = dd;
                            data[2] = emailx;
                            data[3] = df;
                            PutData putData = new PutData(config.lien+"?view=getTimesSheet&employee_id="+firstTag+"&from="+dd+"&to="+df+"&email="+emailx, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("resultat", result);
                                    JSON json = new JSON(result);
                                    String firstTag = json.key("code").toString();
                                    Log.i("code", firstTag);
                                    if (firstTag.equals("0")){

                                        Toasty.success(generapport.this, "Rapport envoyé avec succes", Toast.LENGTH_SHORT, true).show();
                                    }

                                    else {
                                        Toasty.error(generapport.this, "Erreur", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                            else {
                                Toasty.error(generapport.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });
                }
            }
        });





    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}