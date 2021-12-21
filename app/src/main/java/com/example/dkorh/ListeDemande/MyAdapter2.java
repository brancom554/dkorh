package com.example.dkorh.ListeDemande;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.jisrh.R;


import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {

    Context c;
    ArrayList<absence> users;

    public MyAdapter2(Context c, ArrayList<absence> users) {
        this.c = c;
        this.users = users;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.model_absence,parent,false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int position) {

        absence absence1= users.get(position);

        final String name=absence1.getNom();
        final String prenom=absence1.getPrenom();
        final String emailx=absence1.getEmail();
        final String dd=absence1.getDd();
        final  String df=absence1.getDf();
        final  String hd=absence1.getHd();
        final  String hf=absence1.getHf();
        final  String r=absence1.getR();
        final  String role=absence1.getRole();
        final  String type=absence1.getType();
        final  String type2=absence1.getType2();
        final  String ID=absence1.getAbsence_id();


        //BIND
        holder.nameTxt.setText(name);
        holder.usernameTxt.setText(prenom);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(name,prenom,emailx,dd,df,hd,hf,r,role,type,type2,ID);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c, DetailDemande.class);
        i.putExtra("NAME_KEY",details[0]);
        i.putExtra("USERNAME_KEY",details[1]);
        i.putExtra("EMAIL",details[2]);
        i.putExtra("DD",details[3]);
        i.putExtra("DF",details[4]);
        i.putExtra("HD",details[5]);
        i.putExtra("HF",details[6]);
        i.putExtra("RO",details[7]);
        i.putExtra("RR",details[8]);
        i.putExtra("TY1",details[9]);
        i.putExtra("TY2",details[10]);
        i.putExtra("ID",details[11]);
        c.startActivity(i);

    }

}
