package com.example.recycler_project.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.HttpsTrustManager;
import com.example.recycler_project.ListAdapterProduct;
import com.example.recycler_project.ListAdapterRequestDetail;
import com.example.recycler_project.ListProducts;
import com.example.recycler_project.ListRequestDetail;
import com.example.recycler_project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminRequestDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminRequestDetailsFragment extends Fragment {

    Button backRequestDetail, saveRequestDetails;
    RequestQueue requesQueue;
    List<ListRequestDetail> requestDetails = new ArrayList<>();
    RecyclerView recyclerView;
    private AlertDialog alertDialog;
    AlertDialog.Builder builderDialog;
    TextView priceTotalProducts;
    double total = 0;
    int idRequest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminRequestDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminRequestDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminRequestDetailsFragment newInstance(String param1, String param2) {
        AdminRequestDetailsFragment fragment = new AdminRequestDetailsFragment();
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

        View root = inflater.inflate(R.layout.fragment_admin_request_details, container, false);

        builderDialog = new AlertDialog.Builder(getActivity());
        backRequestDetail = root.findViewById(R.id.backRequestDetails);
        recyclerView = root.findViewById(R.id.recyclerViewRequestDetails);
        priceTotalProducts = root.findViewById(R.id.priceTotalProducts);
        saveRequestDetails = root.findViewById(R.id.saveRequestDetails);

        backRequestDetail.setOnClickListener(this::onBackPress);
        saveRequestDetails.setOnClickListener(this::saveRequest);

        Bundle dataId = getArguments();
        int idUser = dataId.getInt("idUser");
        idRequest = dataId.getInt("idRequest");
        getAllRequestDetail(idUser);
        return root;
    }

    public void onBackPress(View v){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.beginTransaction()
                .replace(R.id.navigationAdmin, new AdminRequestFragment())
                .addToBackStack(null)
                .commit();
    }

    private void saveRequest(View v){
        builderDialog.setTitle("¿Estás seguro de confirmar la transacción?");
        builderDialog.setIcon(R.drawable.ic_logo_recycle);

        builderDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = "https://192.168.43.127/Sentencias/confirmRequest.php?idRequest="+idRequest;
                RequestQueue servicio= Volley.newRequestQueue(getContext());
                StringRequest respuesta = new StringRequest(
                        Request.Method.GET, url, (response) -> {

                    if(response.equals("false")){
                        Toast.makeText(getContext(),"Error de Conexión",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(getActivity(),"Transacción realizada con éxito",Toast.LENGTH_LONG).show();
                    showSelectedFragment(new AdminHomeFragment());

                }, (error) -> {
                    System.out.println(error);
                    Toast.makeText(getContext(),"Error de Conexión", Toast.LENGTH_SHORT).show();
                });
                servicio.add(respuesta);
            }
        });

        builderDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Nothing
            }
        });
        alertDialog = builderDialog.create();
        alertDialog.show();
    }

    private void showSelectedFragment(Fragment fragment){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.beginTransaction()
                .replace(R.id.navigationAdmin, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void getAllRequestDetail(int idUser){
        HttpsTrustManager.allowAllSSL();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        final String[] description = new String[1];
        final double[] weight = new double[1];
        final int[] idProduct = new int[1];
        final int[] idMaterial = new int[1];
        final String[] dateRequest = new String[1];
        final double[] priceTotal = new double[1];
        final double[] tot = new double[1];

        String url = "https://192.168.43.127/Sentencias/getProductsByUser.php?idUser="+idUser+"&idRequest="+idRequest;
        System.out.println(url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) -> {
            JSONObject jsonObject = null;
            for (int i = 0; i < response.length(); i++) {
                try {
                    jsonObject = response.getJSONObject(i);
                    idProduct[0] = jsonObject.getInt("id");
                    idMaterial[0] = jsonObject.getInt("material_id");
                    dateRequest[0] = jsonObject.getString("fecha_reciclaje");
                    description[0] = jsonObject.getString("descripcion_reciclaje");
                    weight[0] = jsonObject.getDouble("peso_kilogramo");
                    priceTotal[0] = jsonObject.getDouble("precio_total");
                    tot[0] = priceTotalProducts(priceTotal[0]);
                    priceTotalProducts.setText("TOTAL ($): "+Double.toString(tot[0]));
                    try {
                        addProductsRecyclerView(idProduct[0], idMaterial[0], description[0], dateRequest[0],weight[0], priceTotal[0]);
                    } catch (Exception e) {
                        // No encontro la ubicacion
                    }

                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, (error) -> {
            Toast.makeText(getActivity().getApplicationContext(), "No se encontraron Productos", Toast.LENGTH_SHORT).show();
        }
        );
        requesQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requesQueue.add(jsonArrayRequest);
    }

    private void addProductsRecyclerView(int idProduct, int idMaterial , String descripcion , String dateRequest, double peso, double priceTotal) {
        final int imageProduct;

        if(idMaterial == 1){
            imageProduct = R.drawable.ic_button_paper;
        }else if (idMaterial == 2){
            imageProduct = R.drawable.ic_button_plastic;
        }else if (idMaterial == 3){
            imageProduct = R.drawable.ic_button_glass;
        }else {
            imageProduct = 0;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        requestDetails.add(new ListRequestDetail(imageProduct, idProduct, dateRequest, descripcion, peso, priceTotal));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ListAdapterRequestDetail(requestDetails, getContext()));
    }

    private double priceTotalProducts(double priceTotal){
        total = priceTotal + total;
        return total;
    }

}