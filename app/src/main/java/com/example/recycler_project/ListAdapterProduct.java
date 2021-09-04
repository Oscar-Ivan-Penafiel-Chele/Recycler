package com.example.recycler_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapterProduct extends RecyclerView.Adapter<MyViewHolderProduct>{

    private List<ListProducts> mData;
    private Context context;

    public ListAdapterProduct(List<ListProducts> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderProduct onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new MyViewHolderProduct(LayoutInflater.from(context).inflate(R.layout.list_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderProduct holder, int position) {
        holder.idProduct.setText(Integer.toString(mData.get(position).getIdProduct()));
        holder.iconImageViewProduct.setImageResource(mData.get(position).getImage());
        holder.descriptionName.setText(mData.get(position).getDescriptionName());
        holder.weightName.setText(Double.toString(mData.get(position).getWeightName()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
