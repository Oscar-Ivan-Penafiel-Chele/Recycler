package com.example.recycler_project;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView iconImage;
    TextView addressName;
    Button addressDelete;

    public MyViewHolder(View itemView){
        super(itemView);
        iconImage = itemView.findViewById(R.id.iconImageView);
        addressDelete = itemView.findViewById(R.id.addressName);
        addressDelete = itemView.findViewById(R.id.deleteAddress);
    }

}
