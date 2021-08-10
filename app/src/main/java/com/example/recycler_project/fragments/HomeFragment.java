package com.example.recycler_project.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.MapsActivity;
import com.example.recycler_project.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Button btn_map;
    Button btn_recycler;
    Button btn_register;
    TextView user;
    String usuario;
    RequestQueue requesQueue;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btn_map = root.findViewById(R.id.btn_map);
        btn_recycler = root.findViewById(R.id.btn_recycler);
        btn_register = root.findViewById(R.id.btn_register);

        btn_map.setOnClickListener(this::onClick);
        btn_recycler.setOnClickListener(this::onClick);
        btn_register.setOnClickListener(this::onClick);
        user=root.findViewById(R.id.txt_nombre);

        consulta();

        return root;
    }

    public void consulta( ){
        HttpsTrustManager.allowAllSSL();
        SharedPreferences preferences= getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        String b = preferences.getString("usuario","asas");

        String url= "https://192.168.1.4/Sentencias/Consulta.php?usuario="+b;
        System.out.println(url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i<response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    user.setText("Bienvenido "+jsonObject.getString("nombre"));
                    System.out.println(jsonObject.getString("nombre"));
                }catch (JSONException e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
            }
        }, (error)->{
            Toast.makeText(getActivity().getApplicationContext(), "error de Conexion", Toast.LENGTH_SHORT).show();
        }
        );
        requesQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requesQueue.add(jsonArrayRequest);

    }

    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btn_map:
                Intent intent_map = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent_map);
                break;
            case R.id.btn_recycler:
                showSelectedFragment(new RecyclerFragment());
                break;
            case R.id.btn_register:
                showSelectedFragment(new ProductsFragment());
                break;
        }
    }

    private void showSelectedFragment (Fragment fragment) {
        if(fragment != null){
            getFragmentManager().beginTransaction().replace(R.id.container_nav,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }
}