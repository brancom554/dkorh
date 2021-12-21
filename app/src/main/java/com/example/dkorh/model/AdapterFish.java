package com.example.dkorh.model;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jisrh.R;

import java.util.Collections;
import java.util.List;

public class AdapterFish extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataFish> data= Collections.emptyList();
    DataFish current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterFish(Context context, List<DataFish> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.rapport_list, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataFish current=data.get(position);
        myHolder.date.setText(current.date);
        myHolder.dj.setText("HEURE DEBUT JOURNEE :"+current.dj);
        myHolder.dp.setText("HEURE DEBUT PAUSE :"+current.dp);
        myHolder.fp.setText("HEURE FIN PAUSE :"+current.fp);
        myHolder.fj.setText("HEURE FIN JOURNEE :"+current.fj);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView dj;
        TextView dp;
        TextView fp;
        TextView fj;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.date);
            dj= (TextView) itemView.findViewById(R.id.dj);
            dp = (TextView) itemView.findViewById(R.id.dp);
            fp = (TextView) itemView.findViewById(R.id.fp);
            fj = (TextView) itemView.findViewById(R.id.fj);
        }

    }

}
