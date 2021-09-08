package com.example.recycler_project;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderRequestDetail extends RecyclerView.ViewHolder{

    ImageView iconProductDetail;
    Button editRequestDetail;
    TextView dateMaterialTittle,dateMaterialDescription, descriptionProductTittle, descriptionProductName, weightProductTitle, weightProductName, priceTotalProductTitle, priceTotalProductName, idProductDetail;


    public MyViewHolderRequestDetail(View itemView) {
        super(itemView);

        iconProductDetail = itemView.findViewById(R.id.iconImageViewProduct);

        idProductDetail = itemView.findViewById(R.id.idProductDetail);
        dateMaterialTittle = itemView.findViewById(R.id.dateMaterialTittle);
        descriptionProductTittle = itemView.findViewById(R.id.descriptionProductTittle);
        weightProductTitle = itemView.findViewById(R.id.weightProductTitle);
        priceTotalProductTitle = itemView.findViewById(R.id.priceTotalProductTitle);

        dateMaterialDescription = itemView.findViewById(R.id.dateMaterialDescription);
        descriptionProductName = itemView.findViewById(R.id.descriptionProductName);
        weightProductName = itemView.findViewById(R.id.weightProductName);
        priceTotalProductName = itemView.findViewById(R.id.priceTotalProductName);

        editRequestDetail = itemView.findViewById(R.id.editRequestDetail);
        editRequestDetail.setOnClickListener(this::editRequest);
    }

    private void editRequest(View v){
       FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
        int id = Integer.parseInt(idProductDetail.getText().toString());
        ExampleDialog exampleDialog = new ExampleDialog(id);
        exampleDialog.show(manager,"example dialog");
    }
}
