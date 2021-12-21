package com.example.dkorh.getAllUser;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.jisrh.R;


public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView nameTxt,usernameTxt,emailTxt,role,date,poste,numero,nomp,prenomp;

      ItemClickListener itemClickListener;
    public MyViewHolder(View itemView) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        usernameTxt= (TextView) itemView.findViewById(R.id.usernameTxt);
        emailTxt= (TextView) itemView.findViewById(R.id.emailTxt);
        numero =(TextView)itemView.findViewById(R.id.numero);
        role=(TextView) itemView.findViewById(R.id.role);
        date=(TextView) itemView.findViewById(R.id.date);

        nomp=(TextView) itemView.findViewById(R.id.NOMP);
        prenomp=(TextView) itemView.findViewById(R.id.PRENOMP);
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
