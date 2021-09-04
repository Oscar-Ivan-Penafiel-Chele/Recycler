package com.example.recycler_project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderRequestDetail extends RecyclerView.ViewHolder{

    ImageView iconProductDetail;
    TextView typeMaterialTittle, typeMaterialDescription, dateMaterialTittle, dateMaterialDescription, descriptionProductTittle, descriptionProductName, weightProductTitle, weightProductName, priceTotalProductTitle, priceTotalProductName, idProductDetail;


    public MyViewHolderRequestDetail(View itemView) {
        super(itemView);

        iconProductDetail = itemView.findViewById(R.id.iconImageViewProduct);

        idProductDetail = itemView.findViewById(R.id.idProductDetail);
        typeMaterialTittle = itemView.findViewById(R.id.typeMaterialTittle);
        dateMaterialTittle = itemView.findViewById(R.id.dateMaterialTittle);
        descriptionProductTittle = itemView.findViewById(R.id.descriptionProductTittle);
        weightProductTitle = itemView.findViewById(R.id.weightProductTitle);
        priceTotalProductTitle = itemView.findViewById(R.id.priceTotalProductTitle);

        typeMaterialDescription = itemView.findViewById(R.id.typeMaterialDescription);
        dateMaterialDescription = itemView.findViewById(R.id.dateMaterialDescription);
        descriptionProductName = itemView.findViewById(R.id.descriptionProductName);
        weightProductName = itemView.findViewById(R.id.weightProductName);
        priceTotalProductName = itemView.findViewById(R.id.priceTotalProductName);
    }

}
