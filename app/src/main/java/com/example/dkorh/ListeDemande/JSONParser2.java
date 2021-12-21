package com.example.dkorh.ListeDemande;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class JSONParser2 extends AsyncTask<Void, Void, Boolean> {

    Context c;
    String jsonData;
    RecyclerView rv;

    ProgressDialog pd;
    ArrayList<absence> users=new ArrayList<>();

    public JSONParser2(Context c, String jsonData, RecyclerView rv) {
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
            rv.setAdapter(new MyAdapter2(c,users));
        }else
        {
            Toast.makeText(c, "Ancune demande en attentes", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean parse()
    {
        try
        {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo;

            users.clear();
            absence user;



            for (int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                String name=jo.getString("nom");
                String prenom=jo.getString("prenoms");

                String dd=jo.getString("start_date");
                  String df=jo.getString("end_date");
                 String hd=jo.getString("start_hour");
                  String hf=jo.getString("end_hour");
                 String poste=jo.getString("reason");
                 String r=jo.getString("reason");
                  String type=jo.getString("absence_type");
                  String type2=jo.getString("absence_sous_type");
                  String id=jo.getString("absence_id");
                  String emaix=jo.getString("email");

                user=new absence();

                user.setNom(name);
                user.setPrenom(prenom);

                user.setDd(dd);
                user.setDf(df);
                user.setHd(hd);
                user.setHf(hf);
               user.setPoste(poste);
               user.setR(r);
                user.setType(type);
                user.setType2(type2);
                user.setAbsence_id(id);
                user.setEmail(emaix);

                users.add(user);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }



}




















