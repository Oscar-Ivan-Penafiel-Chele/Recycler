package com.example.recycler_project.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.ListAdapter;
import com.example.recycler_project.ListElements;
import com.example.recycler_project.MapAdmin;
import com.example.recycler_project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminMapFragment extends Fragment {

    private Button search, addReferenceMap, backHomeAdmin;
    private EditText searchReference;
    RequestQueue requesQueue;
    private List<Address> address;
    List<ListElements> elements = new ArrayList<>();
    RecyclerView recyclerViewAddress ;
    Context contextFragment;
    ListAdapter listAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminMapFragment newInstance(String param1, String param2) {
        AdminMapFragment fragment = new AdminMapFragment();
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

        View root = inflater.inflate(R.layout.fragment_admin_map, container, false);

        backHomeAdmin = root.findViewById(R.id.backHomeAdmin);
        addReferenceMap = root.findViewById(R.id.addReferenceMap);
        search = root.findViewById(R.id.searchIcon);
        recyclerViewAddress = root.findViewById(R.id.reciclerView);
        searchReference = root.findViewById(R.id.searchReference); //EditText Buscar...

        addReferenceMap.setOnClickListener(this::addReference);
        search.setOnClickListener(this::searchReferenceMap);
        backHomeAdmin.setOnClickListener(this::onBackPress);

        getLocations();

        return root;
    }

    public void onBackPress(View v){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.popBackStack();
    }

    private void addReference(View v){
        Intent intent = new Intent(getActivity(), MapAdmin.class);
        startActivity(intent);
    }

    private void searchReferenceMap(View v){
        Toast.makeText(getContext(),"Has dado click en el boton buscar",Toast.LENGTH_SHORT).show();
    }

    private void showSelectedFragment (Fragment fragment) {
        if(fragment != null){
            getFragmentManager().beginTransaction().replace(R.id.navigationAdmin,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void getLocations(){
        HttpsTrustManager.allowAllSSL();

        final double[] latitud = {0};
        final double[] longitud = {0};
        final int[] idAddress = new int[1];
        int maxResultados = 1;
        final String[] direccion = new String[1];
        Geocoder geocoder = new Geocoder(this.getActivity());;

        String url= "https://192.168.1.12/Sentencias/mapGetLocation.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i<response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    idAddress[0] = jsonObject.getInt("id_recicladora");
                    latitud[0] = jsonObject.getDouble("coord_latitud");
                    longitud[0] = jsonObject.getDouble("coord_longitud");
                    try {
                        address = geocoder.getFromLocation(latitud[0],longitud[0],maxResultados);
                        direccion[0] = address.get(0).getAddressLine(0);
                        addElementsRecyclerView(idAddress[0], direccion[0]);
                    } catch (Exception e) {
                       // No encontro la ubicacion
                    }

                }catch (JSONException e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, (error)->{
            Toast.makeText(getActivity().getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT).show();
        }
        );
        requesQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requesQueue.add(jsonArrayRequest);
    }

    public void addElementsRecyclerView(int idAddress, String direccion){
        try {
            elements.add(new ListElements(idAddress,direccion));
            recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            recyclerViewAddress.setHasFixedSize(true);
            recyclerViewAddress.setAdapter(new ListAdapter(getContext(),elements));
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
        }
    }
}