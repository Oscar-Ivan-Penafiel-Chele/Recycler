package com.example.recycler_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Pattern;

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

        if(correo.equals("") || correo.trim().length() <= 0 || contraseña.equals("") || contraseña.trim().length() <= 0){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(contraseña.length() < 6){
            Toast.makeText(this, "La contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!validateEmail(correo)){
            correo1.setError("Email no válido");
            Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_LONG).show();
            return;
        }

        nombre1.replace(" ","+");
        apellido1.replace(" ","+");

        String url="https://192.168.43.127/Sentencias/Registro2.php?nombre="+nombre1+"&apellido="+apellido1+"&telefono="+telefono1+"&correo="+correo+"&contrasena="+contraseña;
        System.out.println(url);
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {

                if(response.equals("usuario ya existe, coloque otro nombre")){
                    Toast.makeText(getApplicationContext(),"El usuario ya existe",Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(),"Bienvenido", Toast.LENGTH_SHORT).show();
                Intent next = new Intent(this, MainActivity.class);
                startActivity(next);

        }, (error) -> {
            System.out.println(error);
                    Toast.makeText(getApplicationContext(),"Error de Conexión", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);


    }

    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}