package com.example.recycler_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapterRequestDetail extends RecyclerView.Adapter<MyViewHolderRequestDetail>{
    private List<ListRequestDetail> mData;
    private Context context;

    public ListAdapterRequestDetail(List<ListRequestDetail> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @NotNull
    @Override
    public MyViewHolderRequestDetail onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new MyViewHolderRequestDetail(LayoutInflater.from(context).inflate(R.layout.list_request_details,parent,false));
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolderRequestDetail holder, int position) {
        holder.iconProductDetail.setImageResource(mData.get(position).getImage());
        holder.idProductDetail.setText(Integer.toString(mData.get(position).getIdProductDetail()));
        holder.descriptionProductName.setText(mData.get(position).getDescriptionMaterialDetail());
        holder.weightProductName.setText(Double.toString(mData.get(position).getWeight()));
        holder.dateMaterialDescription.setText(mData.get(position).getDateTimeMaterial());
        holder.priceTotalProductName.setText(Double.toString(mData.get(position).getPriceTotal()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
