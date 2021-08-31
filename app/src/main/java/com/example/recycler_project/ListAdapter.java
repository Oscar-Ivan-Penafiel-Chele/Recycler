package com.example.recycler_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<ListElements> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(Context context,List<ListElements> itemList ){
        this.mInflater = LayoutInflater.from(context);
        this.context = context ;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.addressName.setText(mData.get(position).getAddress());
    }
}
