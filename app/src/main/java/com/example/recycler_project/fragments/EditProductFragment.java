package com.example.recycler_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProductFragment extends Fragment {

    private Button  btn_save, btn_back;
    private EditText txt_descrp, txt_peso;
    private RequestQueue requesQueue;
    private double pesoxKilo,total, pesoR;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProductFragment newInstance(String param1, String param2) {
        EditProductFragment fragment = new EditProductFragment();
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
        View root = inflater.inflate(R.layout.fragment_edit_product, container, false);

        btn_back = root.findViewById(R.id.backEditProducts);
        btn_save = root.findViewById(R.id.productEditButton);
        txt_descrp = root.findViewById(R.id.productEdit);
        txt_peso = root.findViewById(R.id.weightEdit);

        btn_back.setOnClickListener(this::onClick);
        btn_save.setOnClickListener(this::onClick);

        return root;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backEditProducts:
                onBackPress();
                break;
            case R.id.productEditButton:
                //editProducts();
                showSelectedFragment(new ProductsFragment());
                break;
        }
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

    private void getProductById(int id){
        HttpsTrustManager.allowAllSSL();
        final int[] idMaterial = new int[1];
        String url= "https://192.168.1.12/Sentencias/Consulta.php?idProduct="+id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i < response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    txt_descrp.setText(jsonObject.getString("descripcion_reciclaje"));
                    txt_peso.setText(jsonObject.getString("peso_kilogramo"));
                    idMaterial[0] = jsonObject.getInt("material_id");
                    getPriceForMaterial(idMaterial[0]);
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

    private void editProducts(){
        HttpsTrustManager.allowAllSSL();
        int idProduct = 0 ; // Seteat el id para hacer el edit
        String descripcion = txt_descrp.getText().toString();
        double peso = Double.parseDouble(txt_peso.getText().toString());

        if(descripcion.isEmpty() || txt_peso.getText().toString().isEmpty()){
            Toast.makeText(getContext(),"Llenar todos los campos",Toast.LENGTH_SHORT).show();
            return;
        }

        String url="https://192.168.1.12/Sentencias/updateUser.php?idProduct="+idProduct+"&descripcion="+descripcion+"&peso="+txt_peso.getText().toString();


        Toast.makeText(getContext(), "Editado producto con éxito", Toast.LENGTH_SHORT).show();
    }

    public void getPriceForMaterial(int idMaterial){
        double pesoM;

        pesoM = Double.parseDouble(txt_peso.getText().toString());

        HttpsTrustManager.allowAllSSL();

        String url ="https://192.168.1.12/Sentencias/Consulta_Material.php?idMaterial="+idMaterial;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i<response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    pesoxKilo = Double.parseDouble(jsonObject.getString("precio_x_kilo"));
                    total = calculationPriceTotal(pesoxKilo, pesoM);
                    pesoR= Double.parseDouble(txt_peso.getText().toString());
//                    SharedPreferences preferences = this.getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
//                    registerRecycling(preferences.getInt("id",0), id, pesoR, total);

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
    }

    public double calculationPriceTotal(double pesoxKilo, double precio){
        double precioTotal=0;
        precioTotal=pesoxKilo*precio;
        return precioTotal;
    }
}