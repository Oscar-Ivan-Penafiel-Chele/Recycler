package com.example.recycler_project.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordFragment extends Fragment {

    Button btn_conf_pass, back_profile;
    EditText currentPassword, newPassword,confirmNewPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
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
        View root = inflater.inflate(R.layout.fragment_password, container, false);

        btn_conf_pass = root.findViewById(R.id.btn_conf_pass);
        back_profile = root.findViewById(R.id.btn_back_profile);
        currentPassword = root.findViewById(R.id.oldPassword);
        newPassword = root.findViewById(R.id.newPassword);
        confirmNewPassword = root.findViewById(R.id.confirmNewPassword);

        newPassword.setEnabled(false);
        confirmNewPassword.setEnabled(false);
        btn_conf_pass.setEnabled(false);

        currentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isReady = currentPassword.getText().toString().length() > 0 ;
                btn_conf_pass.setEnabled(isReady);
                newPassword.setEnabled(isReady);
                confirmNewPassword.setEnabled(isReady);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        btn_conf_pass.setOnClickListener(this::savePassword);
        back_profile.setOnClickListener(this::onBackPress);

        return root;
    }

    public void savePassword(View v){
        HttpsTrustManager.allowAllSSL();
        String contrasenaActual, nuevaContrasena, confirNuevaContrasena;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);

        contrasenaActual = currentPassword.getText().toString();
        nuevaContrasena = newPassword.getText().toString();
        confirNuevaContrasena = confirmNewPassword.getText().toString();
        int id = sharedPreferences.getInt("id",0);
        int rol = sharedPreferences.getInt("rol",0);

        if(contrasenaActual.isEmpty() || nuevaContrasena.isEmpty() || confirNuevaContrasena.isEmpty()){
            Toast.makeText(getContext(),"Llenar los campos necesarios",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!nuevaContrasena.equals(confirNuevaContrasena)){
            Toast.makeText(getContext(),"La contraseña de confirmación no coincide",Toast.LENGTH_SHORT).show();
            return;
        }

        String url="https://192.168.43.127/Sentencias/updatePassword.php?id="+id+"&oldPassword="+contrasenaActual+"&newPassword="+nuevaContrasena;

        RequestQueue servicio= Volley.newRequestQueue(getContext());
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {

            if(response.equals("false")){
                Toast.makeText(getContext(),"Contraseña Actual no coincide",Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), "Contraseña actualizada con éxito!",Toast.LENGTH_SHORT).show();
            if(rol == 2){
                showSelectedFragment(new HomeFragment());
            }else if(rol == 1){
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        int rol = sharedPreferences.getInt("rol",0);
        if(fragment != null){
            if(rol == 1){
                getFragmentManager().beginTransaction().replace(R.id.navigationAdmin,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }else if (rol == 2){
                getFragmentManager().beginTransaction().replace(R.id.container_nav,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }

        }
    }
}