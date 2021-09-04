package com.example.recycler_project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderRequest extends RecyclerView.ViewHolder{

    ImageView iconUser;
    TextView nameTittle, nameDescription, dateTittle, dateDescription, idRequest;

    public MyViewHolderRequest(View itemView) {
        super(itemView);

        iconUser = itemView.findViewById(R.id.imageUser);
        nameTittle = itemView.findViewById(R.id.nameTittle);
        nameDescription = itemView.findViewById(R.id.nameUser);
        dateTittle = itemView.findViewById(R.id.dateTittle);
        dateDescription = itemView.findViewById(R.id.dateDescription);
        idRequest = itemView.findViewById(R.id.idRequest);
    }

}
