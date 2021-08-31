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

import com.example.recycler_project.R;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterProductFragment extends Fragment {
    EditText descripcion, peso;
    Button btn_back, btn_save;
    Double pesoxKilo, total, pesoR;
    ArrayList <Hashtable> products = new ArrayList();
    int sizeArray = 0;

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
        descripcion= root.findViewById(R.id.edtDescriptionProduct);
        peso = root.findViewById(R.id.edtPeso);
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
//                obtenerDatosM();
                registerProduct();
                //showSelectedFragment(new HomeFragment());
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

    private void registerProduct(){
        Hashtable product = new Hashtable();
        product.put("Description",descripcion.getText().toString());
        product.put("Weight",peso.getText().toString());

        products.add(sizeArray,product);
        sizeArray ++;

        System.out.println(products);
    }
}