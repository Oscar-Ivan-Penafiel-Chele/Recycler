package com.example.recycler_project.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.recycler_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerFragment extends Fragment {
    int id;
    Button btn_back;
    Button btn_paper;
    Button btn_glass;
    Button btn_plastic;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerFragment newInstance(String param1, String param2) {
        RecyclerFragment fragment = new RecyclerFragment();
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

        View root = inflater.inflate(R.layout.fragment_recycler, container, false);
        btn_back = root.findViewById(R.id.btn_back);
        btn_paper = root.findViewById(R.id.btn_paper);
        btn_plastic = root.findViewById(R.id.btn_plastic);
        btn_glass = root.findViewById(R.id.btn_glass);

        btn_back.setOnClickListener(this::onClick);
        btn_paper.setOnClickListener(this::onClick);
        btn_plastic.setOnClickListener(this::onClick);
        btn_glass.setOnClickListener(this::onClick);

        verifyLocationMap();

        return root;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                onBackPress();
                break;
            case R.id.btn_paper:
                if(!verifyLocationMap()){
                    Toast.makeText(getContext(),"Por favor seleccione una ubicación",Toast.LENGTH_SHORT).show();
                    return;
                }
                id=1;
                showSelectedFragment(new RegisterProductFragment(),id );
                break;
            case R.id.btn_plastic:
                if(!verifyLocationMap()){
                    Toast.makeText(getContext(),"Por favor seleccione una ubicación",Toast.LENGTH_SHORT).show();
                    return;
                }
                id=2;
                showSelectedFragment(new RegisterProductFragment(),id );
                break;
            case R.id.btn_glass:
                if(!verifyLocationMap()){
                    Toast.makeText(getContext(),"Por favor seleccione una ubicación",Toast.LENGTH_SHORT).show();
                    return;
                }
                id=3;
                showSelectedFragment(new RegisterProductFragment(),id );
                break;
        }
    }

    public void onBackPress(){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.popBackStack();
    }

    private void showSelectedFragment (Fragment fragment, int id) {
        Bundle datosMaterial= new Bundle();
        datosMaterial.putInt("id",id);
        fragment.setArguments(datosMaterial);
        if(fragment != null){
            getFragmentManager().beginTransaction().replace(R.id.container_nav,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private boolean verifyLocationMap(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("locationRecicler", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("idLocation",0);

        if (id == 0){
            return false;
        }else{
            return true;
        }
    }

}