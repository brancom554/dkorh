package com.example.dkorh.ListeDemande;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jisrh.R;


public class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView nameTxt,usernameTxt;
      ItemClickListener itemClickListener;
       public MyViewHolder2(View itemView) {
        super(itemView);
        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        usernameTxt= (TextView) itemView.findViewById(R.id.prenomxc);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }
}
