package com.example.recycler_project;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recycler_project.databinding.ActivityMapAdminBinding;
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

import java.util.List;

public class MapAdmin extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapAdminBinding binding;
    private Button backMapAdmin, addLocation, searchLocationButton;
    private EditText searchLocation;
    int LOCATION_REQUEST_CODE = 10001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String TAG = "MapAdmin";
    private List<Address> address;
    private LatLng latLng;
    private SharedPreferences sharedPreferences;
    private int idUser;
    RequestQueue requesQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backMapAdmin = (Button) findViewById(R.id.backMapAdmin);
        addLocation = (Button) findViewById(R.id.addLocation);
        searchLocationButton = (Button) findViewById(R.id.searchLocationButton);
        searchLocation = (EditText) findViewById(R.id.searchLocation);

        addLocation.setEnabled(false);

        searchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isReady = searchLocation.getText().toString().length() > 0;
                addLocation.setEnabled(isReady);
            }
        });

        addLocation.setOnClickListener(this::addLocationMap);
        searchLocationButton.setOnClickListener(this::searchLocationMap);
        backMapAdmin.setOnClickListener(this::onBackPressed);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermisson();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAdmin);
        mapFragment.getMapAsync(this);
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
                } else {
                    //Location is null
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG, "failure: " + e.getLocalizedMessage());
            }
        });
    }

    private void askLocationPermisson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
            }
        }

    }

    private void searchLocationMap(View v) {
        String direccion = searchLocation.getText().toString();
        Geocoder geo = new Geocoder(getApplicationContext());
        int maxResultados = 1;

        if (direccion.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Barra de búsqueda vacía, por favor llenarla", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            address = geo.getFromLocationName(direccion, maxResultados);
            latLng = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            System.out.println(latLng);

        } catch (Exception e) {
            addLocation.setEnabled(false);
            Toast.makeText(getApplicationContext(),"La dirección no fue encontrada",Toast.LENGTH_SHORT).show();

        }

    }

    private void addLocationMap(View v){
        HttpsTrustManager.allowAllSSL();
        sharedPreferences = getSharedPreferences("preferenciaslogin",MODE_PRIVATE);
        idUser = sharedPreferences.getInt("id",0);
        double latitud = latLng.latitude;
        double longitud = latLng.longitude;

        String url="https://192.168.1.12/Sentencias/mapAdminAdd.php?idUser="+idUser+"&latitud="+latitud+"&longitud="+longitud;
        RequestQueue servicio = Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {

            if(response.equals("1")){
                Toast.makeText(getApplicationContext(),"Punto de Referencia ya existe",Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getApplicationContext(),"Punto de Referencia guardada exitosamente: ",Toast.LENGTH_SHORT).show();
            finish();

        }, (error) -> {
            System.out.println(error);
            Toast.makeText(getApplicationContext().getApplicationContext(),"Sin comunicacion", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);
    }

    private void onBackPressed(View v) {
        finish();
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
                    //user.setText("Bienvenido "+jsonObject.getString("nombre"));
                    latitud[0] = jsonObject.getDouble("coord_latitud");
                    longitud[0] = jsonObject.getDouble("coord_longitud");
                    positionMarket[0] = new LatLng(latitud[0],longitud[0]);
                    mMap.addMarker(new MarkerOptions().position(positionMarket[0]).title("Recicladores"));

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
}