package com.example.recycler_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView iconImage;
    TextView addressName, addressNameTitle, idAddress;
    Button addressDelete;
    private AlertDialog alertDialog;
    AlertDialog.Builder builderDialog;

    public MyViewHolder(View itemView){
        super(itemView);
        iconImage = itemView.findViewById(R.id.iconImageView);
        addressNameTitle = itemView.findViewById(R.id.addressNameTitle);
        addressName = itemView.findViewById(R.id.addressName);
        addressDelete = itemView.findViewById(R.id.deleteAddress);
        idAddress = itemView.findViewById(R.id.idAddress);

        builderDialog = new AlertDialog.Builder(itemView.getContext());
        addressDelete.setOnClickListener(this::deleteAddress);
    }

    private void deleteAddress(View v){
        int id = Integer.parseInt(idAddress.getText().toString());
        builderDialog.setTitle("¿Desea eliminar la ubicación?");
        builderDialog.setIcon(R.drawable.ic_icons8_google_maps_1_);

        String url="https://192.168.1.5/Sentencias/mapAdminDelete.php?idAddress="+id;
        builderDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestQueue servicio= Volley.newRequestQueue(v.getContext());
                StringRequest respuesta = new StringRequest(
                        Request.Method.GET, url, (response) -> {
                    Toast.makeText(v.getContext(), "Dirección inactivada con éxito!",Toast.LENGTH_SHORT).show();
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
