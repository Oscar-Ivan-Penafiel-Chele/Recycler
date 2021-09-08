package com.example.recycler_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicReference;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText descriptionProduct , weightProduct;
    public int id;
    public String peso, descripcion, precio;
    RequestQueue requesQueue;


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

        getData(id);
        getTipoProducto(id);

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
                        String description = descriptionProduct.getText().toString();
                        String a ="";
                        a = description.replace(" ","+");

                        String weightAux = weightProduct.getText().toString();
                        Double weight = Double.parseDouble(weightProduct.getText().toString());

                        if(!verifySpaceBlank(description,weightAux)){
                            Toast.makeText(getActivity(),"Llenar todos los campos",Toast.LENGTH_LONG).show();
                            return;
                        }

                        double pre=0;
                        if(precio.equalsIgnoreCase("1"))
                            pre=0.22;
                        if(precio.equalsIgnoreCase("2"))
                            pre=0.6;
                        if(precio.equalsIgnoreCase("3"))
                            pre=0.5;

                        double precioTotal = calculationPriceTotal(weight,pre);
                        String url = "https://192.168.43.127/Sentencias/EditProduct.php?pesoNuevo="+weightAux+"&precio="+precioTotal+"&descripcion="+a+"&idMaterial="+id;
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

    private boolean verifySpaceBlank(String description, String weightAuxiliar){
        if(description.isEmpty() || description.trim().length() < 1 || weightAuxiliar.isEmpty() || weightAuxiliar.trim().length() < 1 ){
            return false;
        }else {
            return true;
        }
    }

    public double calculationPriceTotal(double pesoxKilo, double precio){
        double precioTotal=0;
        precioTotal=pesoxKilo*precio;
        return precioTotal;
    }
    public void getTipoProducto(int id){
        String url="https://192.168.43.127/Sentencias/getTipoProducto.php?id="+id;
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

    private void getData(int id){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        int rol = sharedPreferences.getInt("rol",0);

        String url = "https://192.168.43.127/Sentencias/getProductById.php?idProduct="+id+"&rol="+rol;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i < response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    descriptionProduct.setText(jsonObject.getString("descripcion_reciclaje"));
                    weightProduct.setText(Double.toString(jsonObject.getDouble("peso_kilogramo")));
                }catch (JSONException e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, (error)->{
            Toast.makeText(getActivity().getApplicationContext(), "Error de Conexión", Toast.LENGTH_SHORT).show();
        }
        );
        requesQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requesQueue.add(jsonArrayRequest);
    }

}
