package com.example.recycler_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class register_2 extends AppCompatActivity {
    EditText correo1, contraseña1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view_2);
        correo1=(EditText)findViewById(R.id.username_input);
        contraseña1=(EditText)findViewById(R.id.pass);
    }

    public void registrar(View view){
        HttpsTrustManager.allowAllSSL();
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

        String url="https://192.168.1.12/Sentencias/Registro2.php?nombre="+nombre1+"&apellido="+apellido1+"&telefono="+telefono1+"&correo="+correo+"&contrasena="+contraseña;
        System.out.println(url);
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {

                //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                if(response.equals("usuario ya existe, coloque otro nombre")){
                    Toast.makeText(getApplicationContext(),"El usuario ya existe",Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(),"Bienvenido", Toast.LENGTH_SHORT).show();
                Intent next = new Intent(this, MainActivity.class);
                startActivity(next);

        }, (error) -> {
            System.out.println(error);
                    Toast.makeText(getApplicationContext(),"Sin conexión", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);


    }

}