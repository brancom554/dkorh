package com.example.dkorh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allyants.notifyme.NotifyMe;
import com.application.isradeleon.notify.Notify;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.jisrh.BuildConfig;
import com.example.jisrh.R;
import com.example.dkorh.model.config;
import com.example.dkorh.model.myDbAdapter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Date;
import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;

public class presence extends AppCompatActivity {


    private static final String TAG = presence.class.getSimpleName();
    private Boolean mRequestingLocationUpdates;
    double TopchronoLa=6.355471590523821;
    double TopchronoLo=2.4276814547822783;


    double resultat;
    double answers;
    private String mLastUpdateTime;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    Button button;
    TextView txtLocationResult;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;


    myDbAdapter helper;
     String test;
    Button matin;
    Button pause;
    Button pause2;
    Button fin;
    String info;
    String id;
    String sheet_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);
        matin=findViewById(R.id.matinx);
        pause=findViewById(R.id.pause);
        pause2=findViewById(R.id.pause2);
        fin=findViewById(R.id.finx);
        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbarxx);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        helper = new myDbAdapter(this);
        SharedPreferences time1 = getApplicationContext().getSharedPreferences("time1", 0);
        SharedPreferences.Editor editorx = time1.edit();
        config config=new config();
       //  sheet_id =  time1.getString("id", "");
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        String ids = json.key("employee_id").toString();
        Log.i("tag", ids);
       // Log.i("sheet_idd", sheet_id);
        test = helper.getData();



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                if (mCurrentLocation != null) {
                  //  txtLocationResult.setText("Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude());
                    resultat =  distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),TopchronoLa,TopchronoLo);
                    if (resultat>=0.7){
                        matin.setEnabled(false);
                        pause.setEnabled(false);
                        pause2.setEnabled(false);
                        fin.setEnabled(false);
                    }
                        Toasty.info(getApplicationContext(), "VOUS ETES BIEN A TOP CHRONO", Toast.LENGTH_SHORT).show();

                    Log.e("kos", String.valueOf(resultat));
                }

            }
        };
        mRequestingLocationUpdates = false;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        if (mCurrentLocation != null) {
         //   txtLocationResult.setText("Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude());
            resultat =  distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),TopchronoLa,TopchronoLo);
            if (resultat>=0.7){
                matin.setEnabled(false);
                pause.setEnabled(false);
                pause2.setEnabled(false);
                fin.setEnabled(false);
            }
            Toasty.info(getApplicationContext(), "VOUS ETES BIEN A TOP CHRONO", Toast.LENGTH_SHORT).show();
        }
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                                .addOnSuccessListener(presence.this, new OnSuccessListener<LocationSettingsResponse>() {
                                    @SuppressLint("MissingPermission")
                                    @Override
                                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                                        Log.i(TAG, "All location settings are satisfied.");

                                        Toasty.info(getApplicationContext(), "RECUPERATION DE VOTRE POSITION", Toast.LENGTH_SHORT).show();
                                        //noinspection MissingPermission
                                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                                mLocationCallback, Looper.myLooper());

                                        if (mCurrentLocation != null) {
                                          //  txtLocationResult.setText("Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude());
                                            resultat =  distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),TopchronoLa,TopchronoLo);
                                            if (resultat>=0.7){
                                                matin.setEnabled(false);
                                                pause.setEnabled(false);
                                                pause2.setEnabled(false);
                                                fin.setEnabled(false);
                                                Toasty.info(getApplicationContext(), "VOUS N'ETES PAS A TOP CHRONO", Toast.LENGTH_SHORT).show();
                                            }

                                            Toasty.info(getApplicationContext(), "VOUS ETES BIEN A TOP CHRONO", Toast.LENGTH_SHORT).show();
                                            Log.e("kosss", String.valueOf(resultat));
                                        }
                                    }
                                })
                                .addOnFailureListener(presence.this, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        int statusCode = ((ApiException) e).getStatusCode();
                                        switch (statusCode) {
                                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                                        "location settings ");
                                                try {
                                                    // Show the dialog by calling startResolutionForResult(), and check the
                                                    // result in onActivityResult().
                                                    ResolvableApiException rae = (ResolvableApiException) e;
                                                    rae.startResolutionForResult(presence.this, REQUEST_CHECK_SETTINGS);
                                                } catch (IntentSender.SendIntentException sie) {
                                                    Intent i = new Intent(presence.this, MainActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                                }
                                                break;
                                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                                        "fixed here. Fix in Settings.";
                                                Log.e(TAG, errorMessage);

                                                Toast.makeText(presence.this, errorMessage, Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(presence.this, MainActivity.class);
                                                startActivity(i);
                                                finish();
                                        }
                                        if (mCurrentLocation != null) {
                                      //      txtLocationResult.setText("Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude());
                                            resultat =  distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),TopchronoLa,TopchronoLo);
                                            if (resultat>=0.7){
                                                matin.setEnabled(false);
                                                pause.setEnabled(false);
                                                pause2.setEnabled(false);
                                                fin.setEnabled(false);
                                            }


                                        }
                                    }
                                });


                        Log.e("infox", String.valueOf(answers));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            Intent intent = new Intent();
                            intent.setAction(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        matin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final FlatDialog flatDialog = new FlatDialog(presence.this);
                 flatDialog.setTitle("JIS RH")
                         .setSubtitle("JOUR ET HEURE DE DEBUT JOURNEE  :"+currentDateTimeString)
                         .setFirstButtonText("MARQUER")
                         .setSecondButtonText("ANNULER")
                         .setBackgroundColor(Color.parseColor("#77b5fe"))
                         .setSecondButtonColor(Color.parseColor("#FF0000"))
                         .setFirstButtonColor(Color.parseColor("#008000"))
                         .withFirstButtonListner(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 Handler handler = new  Handler();
                                 handler.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         String[] field = new String[3];
                                         field[0] = "date";
                                         field[1] = "token";
                                         field[2]= "M";
                                         String[] data = new String[3];
                                         Date d = new Date();
                                         CharSequence s  = DateFormat.format("yyyy-MM-d H:m", d.getTime());
                                         SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                         String  token =  pref.getString("token","null");
                                         JSON json = new JSON(token);
                                         String firstTag = json.key("employee_id").toString();
                                         Log.i("idemp", firstTag);
                                         String matin="M";
                                         data[0] = (String) s;
                                         data[1] = firstTag;
                                         data[2]=  matin;
                                         PutData putData = new PutData(config.lien+"?view=createTimeSheet&creation_date="+s+"&type=M&employee_id="+firstTag+"&id="+test, "POST", field, data);
                                         if (putData.startPut()) {
                                             if (putData.onComplete()) {
                                                 String result = putData.getResult();
                                                 JSON jsons = new JSON(result);
                                                 String firstTags = jsons.key("codeTraitement").toString();


                                                 sheet_id = jsons.key("sheet_id").toString();
                                                 Log.i("sheet_id", sheet_id);



                                                helper.insertData(sheet_id);
                                                 test = helper.getData();

                                                 String base=sheet_id;

                                                 Log.i("base", base);


                                                 Log.i("test", test);
                                             //    editorx.putString("sheet_id",sheet_id);
                                              //   editorx.commit();
                                                 Log.i("resultat", result);
                                                 Log.i("code", firstTags);
                                                 Log.i("id", sheet_id);
                                                 if (firstTags.equals("0")){
                                                     Notify.build(getApplicationContext())
                                                             .setTitle("JIS COMPUTING")
                                                             .setContent("Felicitation vous avez commencer la journee date et heure:"+s)
                                                             .setSmallIcon(R.drawable.logo)
                                                             .setColor(R.color.bleu)
                                                             .largeCircularIcon()
                                                             .show();
                                                     flatDialog.dismiss();
                                                     Toasty.success(presence.this, "Vous avez bien commencer la journee", Toast.LENGTH_SHORT, true).show();

                                                 }

                                              else  if (firstTags.equals("1")){
                                                     Toasty.error(presence.this, "Erreur serveur", Toast.LENGTH_SHORT, true).show();
                                                 }

                                                 else  if (firstTags.equals("2")){
                                                     Toasty.error(presence.this, "Il n'a pas cliquer sur début de journée", Toast.LENGTH_SHORT, true).show();
                                                     flatDialog.dismiss();
                                                 }

                                                 else  if (firstTags.equals("3")){
                                                     Toasty.error(presence.this, "Vous avez deja commencer la journee merci", Toast.LENGTH_SHORT, true).show();
                                                     flatDialog.dismiss();
                                                 }

                                                 else {
                                                     Toasty.error(presence.this, "Erreur inconnue", Toast.LENGTH_SHORT, true).show();
                                                     flatDialog.dismiss();
                                                 }
                                             }
                                         }
                                         else {
                                             Toasty.error(presence.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                                             flatDialog.dismiss();
                                         }
                                     }
                                 });
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


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(presence.this);
                flatDialog.setTitle("JIS RH")
                        .setSubtitle("JOUR ET HEURE DEBUT PAUSE  :"+currentDateTimeString)
                        .setFirstButtonText("MARQUER")
                        .setSecondButtonText("ANNULER")
                        .setBackgroundColor(Color.parseColor("#77b5fe"))
                        .setSecondButtonColor(Color.parseColor("#FF0000"))
                        .setFirstButtonColor(Color.parseColor("#008000"))
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Handler handler = new  Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] field = new String[3];
                                        field[0] = "date";
                                        field[1] = "token";
                                        field[2]= "DP";
                                        String[] data = new String[3];

                                        Date d = new Date();
                                        CharSequence s  = DateFormat.format("yyyy-MM-d H:m", d.getTime());
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                        String  token =  pref.getString("token","null");
                                        JSON json = new JSON(token);
                                        String firstTag = json.key("employee_id").toString();
                                        String matin="DP";
                                        data[0] = (String) s;
                                        data[1] = firstTag;
                                        data[2]=  matin;




                                        PutData putData = new PutData(config.lien+"?view=createTimeSheet&creation_date="+s+"&type=DP&employee_id="+firstTag+"+&id="+test, "POST", field, data);
                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                String result = putData.getResult();
                                                JSON jsons = new JSON(result);
                                                String firstTags = jsons.key("codeTraitement").toString();
                                                Log.i("resultat", result);
                                                Log.i("code", firstTags);
                                                Log.i("sheet_id", sheet_id);
                                                if (firstTags.equals("0")){

                                                    Log.i("sheet_id", sheet_id);

                                                    Notify.build(getApplicationContext())
                                                            .setTitle("JIS COMPUTING")
                                                            .setContent("Felicitation vous avez commencer la pause"+s)
                                                            .setSmallIcon(R.drawable.logo)
                                                            .setColor(R.color.bleu)
                                                            .largeCircularIcon()

                                                            .show();
                                                    flatDialog.dismiss();
                                                    Toasty.success(presence.this, "Vous avez bien commencer la pause", Toast.LENGTH_SHORT, true).show();

                                                }

                                                else  if (firstTags.equals("1")){
                                                    Toasty.error(presence.this, "Erreur", Toast.LENGTH_SHORT, true).show();
                                                }

                                                else  if (firstTags.equals("2")){
                                                    Toasty.error(presence.this, "Il n'a pas cliquer sur début de journée ", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }

                                                else  if (firstTags.equals("3")){
                                                    Toasty.error(presence.this, "Vous avez deja la pause", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }

                                                else {
                                                    Toasty.error(presence.this, "Erreur inconnue", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }
                                            }
                                        }
                                        else {
                                            Toasty.error(presence.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                                            flatDialog.dismiss();
                                        }
                                    }
                                });







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


        pause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(presence.this);
                flatDialog.setTitle("JIS RH")
                        .setSubtitle("JOUR ET HEURE FIN PAUSE  :"+currentDateTimeString)
                        .setFirstButtonText("MARQUER")
                        .setSecondButtonText("ANNULER")
                        .setBackgroundColor(Color.parseColor("#77b5fe"))
                        .setSecondButtonColor(Color.parseColor("#FF0000"))
                        .setFirstButtonColor(Color.parseColor("#008000"))
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Handler handler = new  Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] field = new String[3];
                                        field[0] = "date";
                                        field[1] = "token";
                                        field[2]= "FP";
                                        String[] data = new String[3];

                                        Date d = new Date();
                                        CharSequence s  = DateFormat.format("yyyy-MM-d H:m", d.getTime());
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                        String  token =  pref.getString("token","null");
                                        JSON json = new JSON(token);
                                        String firstTag = json.key("employee_id").toString();
                                        String matin="FP";
                                        data[0] = (String) s;
                                        data[1] = firstTag;
                                        data[2]=  matin;
                                        PutData putData = new PutData(config.lien+"?view=createTimeSheet&creation_date="+s+"&type=FP&employee_id="+firstTag+"+&id="+test, "POST", field, data);
                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                String result = putData.getResult();
                                                JSON jsons = new JSON(result);
                                                String firstTags = jsons.key("codeTraitement").toString();
                                                Log.i("resultat", result);
                                                Log.i("code", firstTags);
                                                Log.i("id", sheet_id);
                                                if (firstTags.equals("0")){
                                                    Log.i("sheet_id", sheet_id);
                                                    Notify.build(getApplicationContext())
                                                            .setTitle("JIS COMPUTING")
                                                            .setContent("Felicitation vous avez terminez la pause"+s)
                                                            .setSmallIcon(R.drawable.logo)
                                                            .setColor(R.color.bleu)
                                                            .largeCircularIcon()
                                                            .show();
                                                    flatDialog.dismiss();
                                                    Toasty.success(presence.this, "Vous avez bien terminer la pause", Toast.LENGTH_SHORT, true).show();

                                                }

                                                else  if (firstTags.equals("1")){
                                                    Toasty.error(presence.this, "Erreur serveur", Toast.LENGTH_SHORT, true).show();
                                                }

                                                else  if (firstTags.equals("2")){
                                                    Toasty.error(presence.this, "Il n'a pas cliquer sur début de journée", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }

                                                else  if (firstTags.equals("3")){
                                                    Toasty.error(presence.this, "Vous avez deja terminez la pause", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }

                                                else {
                                                    Toasty.error(presence.this, "Erreur inconnue", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }
                                            }
                                        }
                                        else {
                                            Toasty.error(presence.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                                            flatDialog.dismiss();
                                        }
                                    }
                                });

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


        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(presence.this);
                flatDialog.setTitle("JIS RH")
                        .setSubtitle("JOUR ET HEURE FIN JOURNEE  :"+currentDateTimeString)
                        .setFirstButtonText("MARQUER")
                        .setSecondButtonText("ANNULER")
                        .setBackgroundColor(Color.parseColor("#77b5fe"))
                        .setSecondButtonColor(Color.parseColor("#FF0000"))
                        .setFirstButtonColor(Color.parseColor("#008000"))
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Handler handler = new  Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] field = new String[3];
                                        field[0] = "date";
                                        field[1] = "token";
                                        field[2]= "F";
                                        String[] data = new String[3];

                                        Date d = new Date();
                                        CharSequence s  = DateFormat.format("yyyy-MM-d H:m", d.getTime());
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                        String  token =  pref.getString("token","null");
                                        JSON json = new JSON(token);
                                        String firstTag = json.key("employee_id").toString();
                                        String matin="F";
                                        data[0] = (String) s;
                                        data[1] = firstTag;
                                        data[2]=  matin;
                                        PutData putData = new PutData(config.lien+"?view=createTimeSheet&creation_date="+s+"&type=S&employee_id="+firstTag+"+&id="+test, "POST", field, data);
                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                String result = putData.getResult();
                                                JSON jsons = new JSON(result);
                                                String firstTags = jsons.key("codeTraitement").toString();
                                                Log.i("resultat", result);
                                                Log.i("code", firstTags);
                                                Log.i("id", sheet_id);
                                                if (firstTags.equals("0")){

                                                    Log.i("sheet_id", sheet_id);

                                                    Notify.build(getApplicationContext())
                                                            .setTitle("JIS COMPUTING")
                                                            .setContent("Felicitation vous avez terminez la journee"+s)
                                                            .setSmallIcon(R.drawable.logo)
                                                            .setColor(R.color.bleu)
                                                            .largeCircularIcon()
                                                            .show();
                                                    flatDialog.dismiss();
                                                    Toasty.success(presence.this, "Felicitation vous avez terminez la journee", Toast.LENGTH_SHORT, true).show();
                                                    helper.delete(test);
                                                }

                                                else  if (firstTags.equals("1")){
                                                    Toasty.error(presence.this, "Erreur", Toast.LENGTH_SHORT, true).show();
                                                }
                                                else  if (firstTags.equals("2")){
                                                    Toasty.error(presence.this, "Il n'a pas cliquer sur début de journée 4", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }

                                                else  if (firstTags.equals("3")){
                                                    Toasty.error(presence.this, "Vous avez deja terminer la journee merci", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }

                                                else {
                                                    Toasty.error(presence.this, "Erreur inconnue", Toast.LENGTH_SHORT, true).show();
                                                    flatDialog.dismiss();
                                                }
                                            }
                                        }
                                        else {
                                            Toasty.error(presence.this, "Erreur de connexion", Toast.LENGTH_SHORT, true).show();
                                            flatDialog.dismiss();
                                        }
                                    }
                                });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            //   startLocationUpdates();
        }

        if (mCurrentLocation != null) {
            txtLocationResult.setText("Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude());
            resultat =  distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),TopchronoLa,TopchronoLo);
        }
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }




    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
}