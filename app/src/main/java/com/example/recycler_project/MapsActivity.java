package com.example.recycler_project;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    Button btn_back_map;
    Spinner spinnerLocation;
    private AlertDialog alertDialog;
    AlertDialog.Builder builderDialog;
    private int LOCATION_REQUEST_CODE = 10001;
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private List<Address> address;
    private RequestQueue requesQueue;
    private int sizeArray = 0;
    private int sizeArrayAuxiliar = 0;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> arrayLocations = new ArrayList<>();
    private ArrayList<Hashtable> arrayLocationsAuxiliar = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        builderDialog = new AlertDialog.Builder(MapsActivity.this);
        arrayLocations.add(sizeArray,"Seleccione Recicladora...");
        sizeArray++;
        getLocations();

        btn_back_map = (Button) findViewById(R.id.btn_back_map);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);



        btn_back_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermisson();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveLocation(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        addMarket();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //We have location
                    LatLng currentUbicateUser = new LatLng(location.getLatitude(), location.getLongitude());
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUbicateUser, 19));
                }else {
                    //Location is null
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG,"failure: "+e.getLocalizedMessage());
            }
        });
    }

    private void askLocationPermisson(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void getLocations(){
        HttpsTrustManager.allowAllSSL();

        final double[] latitud = {0};
        final double[] longitud = {0};
        final int[] idAddress = new int[1];
        int maxResultados = 1;
        final String[] direccion = new String[1];
        Geocoder geocoder = new Geocoder(getApplication().getApplicationContext());;

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
                        Hashtable dataLocations = new Hashtable();
                        address = geocoder.getFromLocation(latitud[0],longitud[0],maxResultados);
                        direccion[0] = address.get(0).getAddressLine(0);

                        //Guarda informacion en un array auxiliar
                        dataLocations.put("id_recicladora", idAddress[0]);
                        dataLocations.put("addressName",direccion[0]);
                        arrayLocationsAuxiliar.add(sizeArrayAuxiliar,dataLocations);

                        //Guarda informacion para presentar en el spinner
                        arrayLocations.add(sizeArray,direccion[0]);
                        sizeArray++;
                        ArrayAdapter<ArrayList> adapter =new ArrayAdapter(this, R.layout.spinner_items,arrayLocations);
                        spinnerLocation.setAdapter(adapter);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("No se pudo "+ e.getMessage());
                        // No encontro la ubicacion
                    }

                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, (error)->{
            Toast.makeText(getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT).show();
        }
        );
        requesQueue = Volley.newRequestQueue(getApplicationContext());
        requesQueue.add(jsonArrayRequest);
    }

    private void addMarket(){
        final double[] latitud = {0};
        final double[] longitud = {0};
        final LatLng[] positionMarket = new LatLng[1];

        HttpsTrustManager.allowAllSSL();

        String url= "https://192.168.1.12/Sentencias/mapGetLocation.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response)->{
            JSONObject jsonObject = null;
            for(int i=0; i<response.length();i++){
                try {
                    jsonObject = response.getJSONObject(i);
                    latitud[0] = jsonObject.getDouble("coord_latitud");
                    longitud[0] = jsonObject.getDouble("coord_longitud");
                    positionMarket[0] = new LatLng(latitud[0],longitud[0]);
                    mMap.addMarker(new MarkerOptions().position(positionMarket[0]).title("Recicladora"));

                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, (error)->{
            Toast.makeText(getApplicationContext(), "error de Conexion", Toast.LENGTH_SHORT).show();
        }
        );
        requesQueue = Volley.newRequestQueue(getApplicationContext());
        requesQueue.add(jsonArrayRequest);
    }

    private void saveLocation(int position){
        final Hashtable[] hash = {new Hashtable()};
        sharedPreferences = getSharedPreferences("locationRecicler", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        String location = "";
        location = spinnerLocation.getItemAtPosition(position).toString();

        if(location == "Seleccione Recicladora..."){
            return ;
        }

        builderDialog.setTitle("¿Desea seleccionar la ubicación?");
        builderDialog.setMessage("Usted a seleccionado: "+location);

        String finalLocation = location;
        builderDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                for (int i = 0; i < arrayLocationsAuxiliar.size() ; i++){
                        hash[0] = arrayLocationsAuxiliar.get(i);
                        if(hash[0].get("addressName") == finalLocation){
                            editor.putInt("idLocation",Integer.parseInt(hash[0].get("id_recicladora").toString()));
                            editor.commit();
                            Toast.makeText(MapsActivity.this,"Ubicación seleccionada con éxito",Toast.LENGTH_LONG).show();
                        }
                }
            }
        });

        builderDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = builderDialog.create();
        alertDialog.show();
    }
}