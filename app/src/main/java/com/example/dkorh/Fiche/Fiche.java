package com.example.dkorh.Fiche;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.isradeleon.notify.Notify;
import com.example.jisrh.R;
import com.example.dkorh.model.config;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

import static android.content.ContentValues.TAG;

public class Fiche extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    DatePickerDialog.OnDateSetListener listener;
    DatePickerDialog.OnDateSetListener listener2;
    private Spinner spinner;
    private ArrayList<String> studentss;
    private JSONArray result;
    String idemplo;
    SharedPreferences prefx;
    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;
    TextView datedebut;
    TextView datefin;
    String firstTag;
    String dated;
    String datef;
    String dateda;
    String datefa;
    Button button;
    EditText editText;
    int a1,m1,j1,a2,m2,j2,g,h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche);
        Toolbar toolbar;
        config config=new config();
        String info;
        String nom;
        datefin=findViewById(R.id.datefinx);
        editText=findViewById(R.id.mailrx);
        button =findViewById(R.id.buttonx);
        datedebut=findViewById(R.id.datedebutx);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        textViewName = (TextView) findViewById(R.id.textViewNamexx);
        textViewCourse = (TextView) findViewById(R.id.textViewCoursexx);
        textViewSession = (TextView) findViewById(R.id.textViewSessionxx);
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
        firstTag = json.key("employee_id").toString();
        prefx  = getApplicationContext().getSharedPreferences("MyPrefx", 0);
        studentss = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinnerixxT);
        spinner.setOnItemSelectedListener(Fiche.this);
        getData();
        datedebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Fiche.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener, year,month,day);
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
               a1=year;
               m1=month;
               j1=day;

                dateda = day+"-"+month+"-"+year;
                datedebut.setText(dateda);
                Log.i("idem", firstTag);

                Log.i("date", dated);

                Toasty.info(Fiche.this, " "+day+" "+month+" "+year, Toast.LENGTH_SHORT, true).show();
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
                        Fiche.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener2, year,month,day);
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
                a2=year;
                m2=month;
                j2=day;
                datef =year+"-"+month+"-"+day;
                datefa=day+"-"+month+"-"+year;
                datefin.setText(datefa);
                Log.i("idem", firstTag);

                Log.i("date", datef);

                Toasty.info(Fiche.this, " "+day+" "+month+" "+year, Toast.LENGTH_SHORT, true).show();
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dd=dated;
                String df=datef;

                String emailx=editText.getText().toString();
                idemplo=prefx.getString("id_absencex","vide");
                Log.i("idxddfb",idemplo);

                if (!isEmailValid(emailx)){
                    Toasty.error(Fiche.this, "Email Invalide", Toast.LENGTH_SHORT, true).show();
                }
                 else if (idemplo.isEmpty() || idemplo==null || idemplo.equals("vide")){
                    Toasty.error(Fiche.this, "SELECTIONNER UNE PERSONNE", Toast.LENGTH_SHORT, true).show();
                }
                else if (dd==null ){
                    Toasty.error(Fiche.this, "DATE DE DEBUT NECCESSAIRE", Toast.LENGTH_SHORT, true).show();
                }
                else if (df==null ){
                    Toasty.error(Fiche.this, "DATE DE FIN NECCESSAIRE", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    Handler handler = new  Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "employee_id";
                            field[1] = "start";
                            field[2] = "end";
                            field[3] = "email";
                            String[] data = new String[4];
                            data[0] = idemplo;
                            data[1] = dd;
                            data[2] = df;
                            data[3] = emailx;
                            PutData putData = new PutData(config.lien+"?view=setConge&employee_id="+idemplo+"&start="+dd+"&end="+df+"&email="+emailx, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("resultat", result);
                                    JSON json = new JSON(result);
                                    String firstTag = json.key("code").toString();
                                    Log.i("code", firstTag);
                                    if (firstTag.equals("0")){
                                        Notify.build(getApplicationContext())
                                                .setTitle("JIS COMPUTING")
                                                .setContent("Fiche envoyé avec succes")
                                                .setSmallIcon(R.drawable.logo)
                                                .setColor(R.color.bleu)
                                                .largeCircularIcon()
                                                .show();
                                        Toasty.success(Fiche.this, "Fiche envoyé avec succes", Toast.LENGTH_SHORT, true).show();
                                    }
                                    else if (firstTag.equals("2")){
                                        Toasty.success(Fiche.this, "DATE INVALIDE", Toast.LENGTH_SHORT, true).show();
                                    }
                                    else {
                                        Toasty.error(Fiche.this, "Erreur", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                            else {
                                Toasty.error(Fiche.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
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
    private void getData(){
        StringRequest stringRequest = new StringRequest(config.lien+"?view=getEmployeesHavingAbsences&employee_id="+firstTag,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(fichefect.JSON_ARRAY);
                            getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }
    private void getStudents(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                studentss.add(json.getString(fichefect.TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(Fiche.this, android.R.layout.simple_spinner_dropdown_item, studentss));
    }
    private String getName(int position){
        String name="";
        try {
            JSONObject json = result.getJSONObject(position);
            name = json.getString(fichefect.TAG_PRENOM);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String getCourse(int position){
        String course="";
        try {
            JSONObject json = result.getJSONObject(position);
            course = json.getString(fichefect.TAG_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }
    public String getSession(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(fichefect.TAG_NUMERO);
            SharedPreferences.Editor editor = prefx.edit();
            editor.putString("id_absencex",session);
            editor.commit();
            Log.i("IDXX",session);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textViewName.setText(getName(position));
        textViewCourse.setText(getCourse(position));
        textViewSession.setText(getSession(position));
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewName.setText("");
        textViewCourse.setText("");
        textViewSession.setText("");
}

}