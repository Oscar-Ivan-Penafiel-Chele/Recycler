package com.example.recycler_project.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.R;
import com.example.recycler_project.login;

import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterProductFragment extends Fragment {
    EditText descripcion, peso;
    Button btn_back, btn_save;
    Double pesoxKilo, total, pesoR;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;//h
    private String mParam2;

    public RegisterProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterProductFragment newInstance(String param1, String param2) {
        RegisterProductFragment fragment = new RegisterProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_product, container, false);
        btn_back = root.findViewById(R.id.btn_back_pro);
        descripcion=(EditText)root.findViewById(R.id.edtDescriptionProduct);
        peso=(EditText)root.findViewById(R.id.edtPeso);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPress();
            }
        });

        btn_save = root.findViewById(R.id.btn_guardar);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Registrado con éxito",Toast.LENGTH_SHORT).show();
                obtenerDatosM();
                showSelectedFragment(new HomeFragment());
            }
        });


        return root;
    }

    public void onBackPress(){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.popBackStack();
    }
    private void showSelectedFragment (Fragment fragment) {
        if(fragment != null){
            getFragmentManager().beginTransaction().replace(R.id.container_nav,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void obtenerDatosM(){
        double pesoM;
        pesoM=Double.parseDouble(peso.getText().toString());
        HttpsTrustManager.allowAllSSL();
        Bundle datosMaterial= getArguments();
        if (datosMaterial == null) {
            // No hay datos, manejar excepción
            return;
        }
        int id=datosMaterial.getInt("id");
        System.out.println(id);
        String url ="https://192.168.1.4/Sentencias/Consulta_Material.php?idMaterial="+id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i<response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    pesoxKilo=Double.parseDouble(jsonObject.getString("precio_x_kilo"));
                    total=CalculoTotal(pesoxKilo, pesoM);
                    pesoR= Double.parseDouble(peso.getText().toString());
                    System.out.println(total);
                    SharedPreferences preferences = this.getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
                    System.out.println("el id del usuario es: "+preferences.getInt("id",0));
                    RegistroReciclado(preferences.getInt("id",0), id, pesoR, total);

                }catch (JSONException e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
            }
        }, (error)->{
            Toast.makeText(getActivity().getApplicationContext(), "error de Conexion", Toast.LENGTH_SHORT).show();
        }
        );
        RequestQueue requesQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requesQueue.add(jsonArrayRequest);
        //Calcular precio aproximado


    }

    public double CalculoTotal(double pesoxKilo, double precio){
        double precioTotal=0;
        precioTotal=pesoxKilo*precio;
        return precioTotal;
    }

    public void RegistroReciclado(int idUsuario, int idMaterial, double pesoKilo, double precioTotal){
        HttpsTrustManager.allowAllSSL();
        String url="https://192.168.1.4/Sentencias/RegistroMaterial.php?idUsuario="+idUsuario+"&idMaterial="+idMaterial+"&" +
                "peso="+pesoKilo+"&precioTotal="+precioTotal;
        System.out.println(url);
        RequestQueue servicio = Volley.newRequestQueue(this.getActivity());
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {
                    Toast.makeText(getActivity().getApplicationContext(),"Material registrado exitosamente", Toast.LENGTH_SHORT).show();
                }, (error) -> {
                    System.out.println(error);
                    Toast.makeText(getActivity().getApplicationContext(),"error comunicacion", Toast.LENGTH_SHORT).show();
                });
        servicio.add(respuesta);
    }
}