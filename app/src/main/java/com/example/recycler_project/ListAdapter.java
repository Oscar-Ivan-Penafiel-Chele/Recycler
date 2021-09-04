package com.example.recycler_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<ListElements> mData;
    private Context context;

    public ListAdapter(Context context,List<ListElements> item ){
        this.context = context ;
        this.mData = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_element,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idAddress.setText(Integer.toString(mData.get(position).getIdAddress()));
        holder.addressName.setText(mData.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}
