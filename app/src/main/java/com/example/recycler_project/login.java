package com.example.recycler_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class login extends AppCompatActivity {

    EditText txt_username, pass;
    Button btn_login;
    String username;
    int rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        txt_username = (EditText) findViewById(R.id.username_input);
        username = txt_username.getText().toString();
        pass = (EditText) findViewById(R.id.pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        //btn_login.setOnClickListener(this::home);
        recuperarPreferencias(); //
    }
    public void login(View view){
        HttpsTrustManager.allowAllSSL(); //PErmite conexion incluso si expiro el ssl de la pagina (Página no segura)
        String usuario = txt_username.getText().toString();
        String contrasena = pass.getText().toString();
        if(usuario.equals("") || contrasena.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url="https://192.168.1.4/Sentencias/Login.php?usuario="+usuario+"&contrasena="+contrasena;
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> { //Logra realizar conexion
                    //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();

                    if(response.equalsIgnoreCase("Correcto")){ // usuario reciclador  rol=2
                        guardarPreferencias(usuario, contrasena, response);
                        Intent next = new Intent(this, Navigation.class);
                        startActivity(next);

                    }else if (response.equalsIgnoreCase("Admin")){ // usuario administrados  rol=1
                        guardarPreferencias(usuario, contrasena, response);
                        Intent next = new Intent(this, AdminNavigationActivity.class);
                        startActivity(next);
                    }else if (response.equalsIgnoreCase("Incorrecto")){ // No se encontre la cuenta en la BDD
                        Toast.makeText(this, "Usuario o contraseña invalidos", Toast.LENGTH_SHORT).show();
                    }

        }, (error) -> {
            System.out.println(error); // No logra realizar conexion;
            Toast.makeText(getApplicationContext(),"error comunicacion", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);


    }

    public void guardarPreferencias(String usuario, String contraseña, String r){ //guarda datos se sesion

        if(r.equalsIgnoreCase("Correcto")){
            rol=2;
        }else if(r.equalsIgnoreCase("Admin")){
            rol=1;
        }
        SharedPreferences preferences = getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("contraseña", contraseña);
        editor.putInt("rol", rol);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    public void recuperarPreferencias(){ //Recupera datos si ya existe una sesion iniciada;
        txt_username = (EditText) findViewById(R.id.username_input);
        pass = (EditText) findViewById(R.id.pass);
        SharedPreferences preferences=getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        txt_username.setText(preferences.getString("usuario",""));
        pass.setText(preferences.getString("contraseña",""));
    }
    /*public void home(View v){
        if(username != ""){
            Intent next = new Intent(this, Navigation.class);
            startActivity(next);
        }else {
            Intent next = new Intent(this, Navigation.class);
            startActivity(next);
        }

    }*/
}