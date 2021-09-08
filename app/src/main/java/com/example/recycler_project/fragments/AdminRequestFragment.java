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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.ListAdapterRequest;
import com.example.recycler_project.ListRequest;
import com.example.recycler_project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminRequestFragment extends Fragment{

    Button backRequest, searchRequesButton;
    EditText searchRequest;
    RequestQueue requesQueue;
    List<ListRequest> requests = new ArrayList<>();
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminRequestFragment newInstance(String param1, String param2) {
        AdminRequestFragment fragment = new AdminRequestFragment();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_admin_request, container, false);

        backRequest = root.findViewById(R.id.backRequest);
        searchRequest = root.findViewById(R.id.searchRequest);
        searchRequesButton = root.findViewById(R.id.searchRequestButton);
        recyclerView = root.findViewById(R.id.recyclerViewRequest);

        backRequest.setOnClickListener(this::onBackPress);
        searchRequesButton.setOnClickListener(this::onClick);
        getAllRequest(searchRequest.getText().toString());

        return root;
    }

    public void onBackPress(View v){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.beginTransaction()
                .replace(R.id.navigationAdmin, new AdminHomeFragment())
                .addToBackStack(null)
                .commit();
    }

    public void onClick(View v){
                requests.clear();
                getAllRequest(searchRequest.getText().toString());
    }
    private void getAllRequest(String b){
        final int[] idRequest = new int[1];

        if(b.isEmpty()) {
            HttpsTrustManager.allowAllSSL();
            final String[] nameUser = new String[1];
            final String[] dateRequest = new String[1];
            final int[] idUser = new int[1];
            final String[] apellido = new String[1];

            String url = "https://192.168.43.127/Sentencias/getAllRequest.php";
            System.out.println(url);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) -> {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nameUser[0] = jsonObject.getString("nombre");
                        apellido[0] = jsonObject.getString("apellido");
                        dateRequest[0] = jsonObject.getString("fecha_peticion");
                        idUser[0] = jsonObject.getInt("id_usuario");
                        idRequest[0] = jsonObject.getInt("id_peticion");

                        addElementsRecyclerView(idUser[0], idRequest[0], nameUser[0], apellido[0], dateRequest[0]);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, (error) -> {
                Toast.makeText(getActivity().getApplicationContext(), "No existen registros", Toast.LENGTH_SHORT).show();
            }
            );
            requesQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requesQueue.add(jsonArrayRequest);
        }else {
            HttpsTrustManager.allowAllSSL();
            final String[] nameUser = new String[1];
            final String[] dateRequest = new String[1];
            final int[] idUser = new int[1];
            final String[] apellido = new String[1];

            String url = "https://192.168.43.127/Sentencias/getRequest.php?apellido="+b;
            System.out.println(url);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) -> {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nameUser[0] = jsonObject.getString("nombre");
                        apellido[0] = jsonObject.getString("apellido");
                        dateRequest[0] = jsonObject.getString("fecha_peticion");
                        idUser[0] = jsonObject.getInt("id");
                        idRequest[0] = jsonObject.getInt("id_peticion");
                        addElementsRecyclerView(idUser[0],idRequest[0], nameUser[0], apellido[0], dateRequest[0]);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, (error) -> {
                Toast.makeText(getActivity().getApplicationContext(), "No existen registros", Toast.LENGTH_SHORT).show();
            }
            );
            requesQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requesQueue.add(jsonArrayRequest);
        }
    }

    private void addElementsRecyclerView(int id, int idRequest, String name, String lastName, String date){
        requests.add(new ListRequest(id, idRequest, name, lastName, date));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ListAdapterRequest(requests,getContext()));
    }

}