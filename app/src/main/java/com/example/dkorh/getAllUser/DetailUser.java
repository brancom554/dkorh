package com.example.dkorh.getAllUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jisrh.R;

public class DetailUser extends AppCompatActivity {
    TextView nameTxt,emailTxt, usernameTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);


        Toolbar toolbar;
        toolbar = findViewById(R.id.home_toolbar1000x);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor((Color.parseColor("#77b5fe")));

        nameTxt = (TextView) findViewById(R.id.nameDetailTxt);
        emailTxt= (TextView) findViewById(R.id.emailDetailTxt);
        usernameTxt = (TextView) findViewById(R.id.usernameDetailTxt);


        Intent i=this.getIntent();


        String name=i.getExtras().getString("NAME_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");
        String username=i.getExtras().getString("USERNAME_KEY");

        nameTxt.setText(name);
        emailTxt.setText(email);
        usernameTxt.setText(username);



    }
}
