package com.example.recycler_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.atomic.AtomicReference;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText descriptionProduct , weightProduct;
    public int id;
    public String peso, descripcion, precio;
    public ExampleDialog(int id) {
        this.id =id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog, null);

        descriptionProduct = v.findViewById(R.id.decriptionNameEdit);
        weightProduct = v.findViewById(R.id.weightNameEdit);
        getTipoProducto(id);
        System.out.println(id+" "+ peso);

        builder.setView(v)
                .setTitle("Editar Producto")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        peso = weightProduct.getText().toString();
                        descripcion = descriptionProduct.getText().toString();
                        double pre=0;
                        if(precio.equalsIgnoreCase("1"))
                            pre=0.22;
                        if(precio.equalsIgnoreCase("2"))
                            pre=0.6;
                        if(precio.equalsIgnoreCase("3"))
                            pre=0.5;

                        double precioTotal = calculationPriceTotal(Double.parseDouble(peso),pre);
                        System.out.println(precio);
                        String url = "https://192.168.1.5/Sentencias/EditProduct.php?pesoNuevo="+peso+"&precio="+precioTotal+"&descripcion="+descripcion+"&idMaterial="+id;
                        System.out.println(id+" "+ peso);
                        RequestQueue servicio= Volley.newRequestQueue(v.getContext());
                        StringRequest respuesta = new StringRequest(
                                Request.Method.GET, url, (response) -> {

                            Toast.makeText(v.getContext(), "Producto actualizado con éxito!",Toast.LENGTH_SHORT).show();
                        }, (error) -> {
                            System.out.println(error);
                            Toast.makeText(v.getContext(),"Sin conexión a internet", Toast.LENGTH_SHORT).show();
                        });
                        servicio.add(respuesta);
                    }
                });
        return builder.create();
    }

    public double calculationPriceTotal(double pesoxKilo, double precio){
        double precioTotal=0;
        precioTotal=pesoxKilo*precio;
        return precioTotal;
    }
    public void getTipoProducto(int id){
        String url="https://192.168.1.5/Sentencias/getTipoProducto.php?id="+id;
        RequestQueue servicio= Volley.newRequestQueue(getContext());
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {
            System.out.println(response);
            precio =response;
        }, (error) -> {
            System.out.println(error);
        });
        servicio.add(respuesta);


    }

}
