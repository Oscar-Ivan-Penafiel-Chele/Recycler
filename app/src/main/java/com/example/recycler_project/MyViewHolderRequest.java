package com.example.recycler_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler_project.fragments.AdminRequestDetailsFragment;

public class MyViewHolderRequest extends RecyclerView.ViewHolder{

    ImageView iconUser;
    Button viewRequest;
    TextView nameTittle, nameDescription, dateTittle, dateDescription, idUser, idRequest;

    public MyViewHolderRequest(View itemView) {
        super(itemView);

        iconUser = itemView.findViewById(R.id.imageUser);
        nameTittle = itemView.findViewById(R.id.nameTittle);
        nameDescription = itemView.findViewById(R.id.nameUser);
        dateTittle = itemView.findViewById(R.id.dateTittle);
        dateDescription = itemView.findViewById(R.id.dateDescription);
        idUser = itemView.findViewById(R.id.idUser);
        idRequest = itemView.findViewById(R.id.idRequest);
        viewRequest = itemView.findViewById(R.id.viewRequest);

        viewRequest.setOnClickListener(this::viewRequest);
    }

    private void viewRequest(View v){
        Fragment fragment = new AdminRequestDetailsFragment();

        Bundle idRequestUser = new Bundle();
        idRequestUser.putInt("idUser",Integer.parseInt(idUser.getText().toString()));
        idRequestUser.putInt("idRequest",Integer.parseInt(idRequest.getText().toString()));
        fragment.setArguments(idRequestUser);

        FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.navigationAdmin, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }
}
