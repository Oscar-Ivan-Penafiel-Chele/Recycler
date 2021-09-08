package com.example.recycler_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler_project.fragments.AdminRequestDetailsFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapterRequest extends RecyclerView.Adapter<MyViewHolderRequest>{

    private List<ListRequest> mData;
    private Context context;

    public ListAdapterRequest(List<ListRequest> item, Context context) {
        this.mData = item;
        this.context = context;
    }

    @Override
    public MyViewHolderRequest onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolderRequest(LayoutInflater.from(context).inflate(R.layout.list_request,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolderRequest holder, int position) {
        holder.idUser.setText(Integer.toString(mData.get(position).getId()));
        holder.nameDescription.setText(mData.get(position).getName()+" "+mData.get(position).getLastName());
        holder.dateDescription.setText(mData.get(position).getDate());
        holder.idRequest.setText(Integer.toString(mData.get(position).getIdRequest()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void requestDetails(View v){
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.navigationAdmin, new AdminRequestDetailsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }
}
