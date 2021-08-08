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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recycler_project.MapsActivity;
import com.example.recycler_project.R;
import com.example.recycler_project.login;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    Button btn_back, btn_password;
    TextView txt_pass,txt_help,txt_info,txt_out;
    ImageView img_key,img_help,img_info,img_out;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        btn_back = root.findViewById(R.id.btn_back_settings);
        txt_pass = root.findViewById(R.id.txt_pass);
        img_key = root.findViewById(R.id.img_key);
        txt_help = root.findViewById(R.id.txt_help);
        img_help = root.findViewById(R.id.img_help);
        txt_info = root.findViewById(R.id.txt_info);
        img_info = root.findViewById(R.id.img_info);
        txt_out = root.findViewById(R.id.txt_out);
        img_out = root.findViewById(R.id.img_out);

        btn_back.setOnClickListener(this::onClick);
        txt_pass.setOnClickListener(this::onClick);
        img_key.setOnClickListener(this::onClick);
        txt_help.setOnClickListener(this::onClick);
        img_help.setOnClickListener(this::onClick);
        txt_info.setOnClickListener(this::onClick);
        img_info.setOnClickListener(this::onClick);
        txt_out.setOnClickListener(this::onClick);
        img_out.setOnClickListener(this::onClick);

        return root;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_settings:
                onBackPress();
                break;
            case R.id.txt_pass:
            case R.id.img_key:
                showSelectedFragment(new PasswordFragment());
                break;
            case R.id.txt_help:
            case R.id.img_help:
                showSelectedFragment(new HelpFragment());
                break;
            case R.id.txt_info:
            case R.id.img_info:
                showSelectedFragment(new AboutFragment());
                break;
            case R.id.txt_out:
            case R.id.img_out:
                SharedPreferences preferences= getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent login = new Intent(getActivity(), com.example.recycler_project.login.class);
                startActivity(login);
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