package com.example.recycler_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class register_2 extends AppCompatActivity {
    EditText correo1, contraseña1;
    //String nombre1, apellido1, telefono1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view_2);
        correo1=(EditText)findViewById(R.id.username_input);
        contraseña1=(EditText)findViewById(R.id.pass);
        //nombre1 = getIntent().getStringExtra("nombre");
        //apellido1 = getIntent().getStringExtra("apellido");
        //telefono1 = getIntent().getStringExtra("telefono");
    }
    public void registrar(View view){
        String correo, contraseña,nombre1, apellido1, telefono1;
        correo=correo1.getText().toString();
        contraseña = contraseña1.getText().toString();
        nombre1 = getIntent().getStringExtra("nombre");
        apellido1 = getIntent().getStringExtra("apellido");
        telefono1 = getIntent().getStringExtra("telefono");
        if(correo.equals("") || contraseña.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(nombre1+apellido1+telefono1+correo+contraseña);

        String url="https://192.168.1.3/Sentencias/Registro2.php?nombre="+nombre1+"&apellido="+apellido1+"&telefono="+telefono1+"&correo="+correo+"&contraseña="+contraseña;

        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {
                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                Intent next = new Intent(this, login.class);
                startActivity(next);
        }, (error) -> {
            Toast.makeText(getApplicationContext(),"error comunicacion", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);


    }

}