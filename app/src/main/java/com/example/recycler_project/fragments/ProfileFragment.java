package com.example.recycler_project.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    Button btn_save, btn_back, btnEditEnable;
    EditText userName, lastName, email, cellphone;
    RequestQueue requesQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = root.findViewById(R.id.userName);
        lastName = root.findViewById(R.id.lastName);
        email = root.findViewById(R.id.email);
        cellphone = root.findViewById(R.id.cellphone);
        btn_back = root.findViewById(R.id.btn_back_profile);
        btn_save = root.findViewById(R.id.btnSaveProfile);
        btnEditEnable = root.findViewById(R.id.btnEditEnable);

        userName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        cellphone.setEnabled(false);
        btn_save.setEnabled(false);

        getDataUser();

        btnEditEnable.setOnClickListener(this::EditTextEnable);
        btn_back.setOnClickListener(this::onBackPress);
        btn_save.setOnClickListener(this::save);

        return root;
    }

    public void getDataUser(){
        int idUser;
        HttpsTrustManager.allowAllSSL();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferenciaslogin",Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("id",0);

        String url= "https://192.168.1.12/Sentencias/Consulta.php?id="+idUser;
        System.out.println(url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i < response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    userName.setText(jsonObject.getString("nombre"));
                    lastName.setText(jsonObject.getString("apellido"));
                    cellphone.setText(jsonObject.getString("telefono"));
                    email.setText(jsonObject.getString("correo"));
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

    public void EditTextEnable(View v){
        userName.setEnabled(true);
        lastName.setEnabled(true);
        email.setEnabled(true);
        cellphone.setEnabled(true);
        btn_save.setEnabled(true);
    }

    public void save (View v){
        HttpsTrustManager.allowAllSSL();
        String name, apellido, correo, telefono;
        int idUser,rol;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferenciaslogin",Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("id",0);
        rol = sharedPreferences.getInt("rol",0);

        name = userName.getText().toString();
        apellido = lastName.getText().toString();
        correo = email.getText().toString();
        telefono = cellphone.getText().toString();

        if(!verifyBlankSpace(name,apellido,correo,telefono)){
            Toast.makeText(getContext(),"Llenar todos los campos",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!validateEmail(correo)){
            email.setError("Email no válido");
            Toast.makeText(getActivity(), "Correo electrónico no válido", Toast.LENGTH_LONG).show();
            return;
        }

        String url="https://192.168.1.12/Sentencias/updateUser.php?id="+idUser+"&name="+name+"&lastName="+apellido+"&email="+correo+"&cellphone="+telefono;
        RequestQueue servicio= Volley.newRequestQueue(getContext());
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {

            if(response.equals("false")){
                Toast.makeText(getContext(),"Error al actualizar los datos",Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), "Datos actualizados con éxito!",Toast.LENGTH_SHORT).show();
            if(rol == 2){
                showSelectedFragment(new HomeFragment());
            }else{
                showSelectedFragment(new AdminHomeFragment());
            }

        }, (error) -> {
            System.out.println(error);
            Toast.makeText(getContext(),"Sin conexión a internet", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);

    }

    public void onBackPress(View v){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.popBackStack();
    }

    private void showSelectedFragment (Fragment fragment) {
        int rol;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferenciaslogin",Context.MODE_PRIVATE);
        rol = sharedPreferences.getInt("rol",0);

        if(fragment != null){
            if(rol == 2){
                getFragmentManager().beginTransaction().replace(R.id.container_nav,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }else if(rol == 1) {
                getFragmentManager().beginTransaction().replace(R.id.navigationAdmin,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }

        }
    }

    private boolean verifyBlankSpace(String name, String lastname, String email, String phone){
        if(name.isEmpty() || name.trim().length() < 1 || lastname.isEmpty() || lastname.trim().length() < 1 || email.isEmpty() || email.trim().length() < 1 || phone.isEmpty() || phone.trim().length() < 1 ){
            return false;
        }else {
            return true;
        }
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}