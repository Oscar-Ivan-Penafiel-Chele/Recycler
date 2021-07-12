package com.example.recycler_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recycler_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    Button btn_new, btn_search, btn_edit, btn_delete, btn_save, btn_back;
    EditText txt_search;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        btn_new = root.findViewById(R.id.btn_add);
        btn_back = root.findViewById(R.id.btn_back_products);
        btn_edit = root.findViewById(R.id.btn_edit);
        btn_delete = root.findViewById(R.id.btn_delete);
        btn_save = root.findViewById(R.id.btn_save_product);
        txt_search = root.findViewById(R.id.edit_search);
        btn_search = root.findViewById(R.id.btn_search);

        btn_new.setOnClickListener(this::onClick);
        btn_back.setOnClickListener(this::onClick);
        btn_edit.setOnClickListener(this::onClick);
        btn_delete.setOnClickListener(this::onClick);
        btn_save.setOnClickListener(this::onClick);
        btn_search.setOnClickListener(this::onClick);

        return root;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_products:
                onBackPress();
                break;
            case R.id.btn_add:
                showSelectedFragment(new RecyclerFragment());
                break;
            case R.id.btn_edit:
                showSelectedFragment(new EditProductFragment());
                break;
            case R.id.btn_save_product:
                Toast.makeText(getContext(), "Transacción realizada con éxito",Toast.LENGTH_SHORT).show();
                showSelectedFragment(new HomeFragment());
                break;
            case R.id.btn_search:

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
}