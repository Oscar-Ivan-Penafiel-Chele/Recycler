package com.example.recycler_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

public class MyViewHolderProduct extends RecyclerView.ViewHolder{

    private AlertDialog alertDialog;
    AlertDialog.Builder builderDialog;
    public Button editProduct, deleteProduct;
    public TextView descriptionTittle;
    public TextView weightTittle;
    public TextView descriptionName;
    public TextView weightName;
    public TextView idProduct;
    public ImageView iconImageViewProduct;


    public MyViewHolderProduct(@NonNull @NotNull View itemView) {
        super(itemView);

        iconImageViewProduct = itemView.findViewById(R.id.iconImageViewProduct);
        editProduct = itemView.findViewById(R.id.editProduct);
        deleteProduct = itemView.findViewById(R.id.deleteProduct);
        descriptionTittle = itemView.findViewById(R.id.descriptionTittle);
        weightTittle = itemView.findViewById(R.id.weightTitle);
        descriptionName = itemView.findViewById(R.id.descriptionName);
        weightName = itemView.findViewById(R.id.weightName);
        idProduct = itemView.findViewById(R.id.idProduct);

        builderDialog = new AlertDialog.Builder(itemView.getContext());
        editProduct.setOnClickListener(this::editDetailsProduct);
        deleteProduct.setOnClickListener(this::deleteProduct);
    }

    private void editDetailsProduct(View v){
        FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
        int id = Integer.parseInt(idProduct.getText().toString());
         ExampleDialog exampleDialog = new ExampleDialog(id);
         exampleDialog.show(manager,"example dialog");

    }

    private void deleteProduct(View v){
        int id = Integer.parseInt(idProduct.getText().toString());
        builderDialog.setTitle("¿Desea eliminar el producto?");
        builderDialog.setIcon(R.drawable.ic_bx_error);

        String url="https://192.168.1.5/Sentencias/deleteProduct.php?idProduct="+id;
        builderDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestQueue servicio= Volley.newRequestQueue(v.getContext());
                StringRequest respuesta = new StringRequest(
                        Request.Method.GET, url, (response) -> {
                    Toast.makeText(v.getContext(), "Producto eliminado con éxito!",Toast.LENGTH_SHORT).show();
                }, (error) -> {
                    System.out.println(error);
                    Toast.makeText(v.getContext(),"Sin conexión a internet", Toast.LENGTH_SHORT).show();
                });
                servicio.add(respuesta);
            }
        });

        builderDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = builderDialog.create();
        alertDialog.show();
    }

}
