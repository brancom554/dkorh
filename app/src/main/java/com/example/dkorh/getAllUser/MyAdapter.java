package com.example.dkorh.getAllUser;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.jisrh.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList<User> users;

    public MyAdapter(Context c, ArrayList<User> users) {
        this.c = c;
        this.users = users;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        User user= users.get(position);

        final String name=user.getName();
        final String email=user.getEmail();
        final String username=user.getUsername();
        final  String numero=user.getNumero();

        final  String role=user.getRole();
        final  String date=user.getDates();
        final  String nomp=user.getNomp();
        final  String prenomp=user.getPrenomp();

        //BIND
        holder.nameTxt.setText(name);
        holder.emailTxt.setText(email);
        holder.usernameTxt.setText(username);
        holder.numero.setText(numero);
        holder.date.setText(date);
        holder.nomp.setText(nomp);
        holder.prenomp.setText(prenomp);
        holder.role.setText(role);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(name,email,username);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c,DetailUser.class);
        i.putExtra("NAME_KEY",details[0]);
        i.putExtra("EMAIL_KEY",details[1]);
        i.putExtra("USERNAME_KEY",details[2]);

        c.startActivity(i);

    }

}
