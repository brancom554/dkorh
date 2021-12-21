package com.example.dkorh.ListeDemande;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jisrh.R;
import com.example.dkorh.model.config;

import eu.amirs.JSON;


public class Encours extends Fragment {
    RecyclerView rv;
    String info;
    String id;
    private Context mContext;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =   inflater.inflate(R.layout.fragment_encours, container, false);
        config config=new config();
        SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0);
        info = pref.getString("token","vide");
        JSON json = new JSON(info);
        id = json.key("employee_id").toString();
        rv = view.findViewById(R.id.rvx);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        String jsonURL=config.lien+"?view=getAbsences&employee_id="+id;
        new JSONDownloader2(mContext,jsonURL, rv).execute();
        return view;

    }


}