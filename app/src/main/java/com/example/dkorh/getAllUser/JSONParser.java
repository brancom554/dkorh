package com.example.dkorh.getAllUser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONParser extends AsyncTask<Void, Void, Boolean> {

    Context c;
    String jsonData;
    RecyclerView rv;

    ProgressDialog pd;
    ArrayList<User> users=new ArrayList<>();

    public JSONParser(Context c, String jsonData, RecyclerView rv) {
        this.c = c;
        this.jsonData = jsonData;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Chargement...");
        pd.setMessage("Telechargement des informations.");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);

        pd.dismiss();

        if(isParsed)
        {
            rv.setAdapter(new MyAdapter(c,users));
        }else
        {
            Toast.makeText(c, "Impossible de recuperer les donnees", Toast.LENGTH_SHORT).show();
        }


    }

    private Boolean parse()
    {
        try
        {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo;

            users.clear();
            User user;

            for (int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                String name=jo.getString("nom");
                String username=jo.getString("prenoms");
                String email=jo.getString("email");
                String numero=jo.getString("professional_phone_number");
                String date=jo.getString("date");
                String role=jo.getString("fonction");
                String nomp=jo.getString("professionnal_name");
                String prenomp=jo.getString("professionnal_surname");

                user=new User();

                user.setName(name);
                user.setUsername(username);
                user.setEmail(email);
                user.setNumero(numero);
                user.setDates(date);
                user.setRole(role);
                user.setNomp(nomp);
                user.setPrenomp(prenomp);

                users.add(user);
            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }



}



















