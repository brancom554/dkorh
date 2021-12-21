package com.example.dkorh.getAllUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.jisrh.R;
import com.example.dkorh.model.config;


public class ListeUser extends AppCompatActivity {
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_user);
        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbar1000);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));

        config config=new config();
        String jsonURL=config.lien+"?view=getEmployees";

        rv = (RecyclerView) findViewById(R.id.rvx);
        rv.setLayoutManager(new LinearLayoutManager(this));
        new JSONDownloader(ListeUser.this,jsonURL, rv).execute();
    }

}