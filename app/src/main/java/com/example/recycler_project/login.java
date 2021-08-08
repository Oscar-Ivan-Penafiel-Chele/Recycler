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

public class login extends AppCompatActivity {

    EditText txt_username, pass;
    Button btn_login;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        txt_username = (EditText) findViewById(R.id.username_input);
        username = txt_username.getText().toString();
        pass = (EditText) findViewById(R.id.pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        //btn_login.setOnClickListener(this::home);
        recuperarPreferencias();
    }
    public void login(View view){
        HttpsTrustManager.allowAllSSL();
        String usuario = txt_username.getText().toString();
        String contrasena = pass.getText().toString();
        if(usuario.equals("") || contrasena.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        String url="https://192.168.1.3/Sentencias/Login.php?usuario="+usuario+"&contrasena="+contrasena;
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> {
                    //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                        guardarPreferencias(usuario, contrasena, response);
                    if(response.equalsIgnoreCase("Correcto")){
                        Intent next = new Intent(this, Navigation.class);
                        startActivity(next);

                    }else if (response.equalsIgnoreCase("Admin")){
                        Intent next = new Intent(this, AdminNavigationActivity.class);
                        startActivity(next);
                    }else if (response.equalsIgnoreCase("Incorrecto")){
                        Toast.makeText(this, "Usuario o contraseña invalidos", Toast.LENGTH_SHORT).show();
                    }

        }, (error) -> {
            System.out.println(error);
            Toast.makeText(getApplicationContext(),"error comunicacion", Toast.LENGTH_SHORT).show();
        });
        servicio.add(respuesta);


    }

    public void guardarPreferencias(String usuario, String contraseña, String rol){
        SharedPreferences preferences = getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("contraseña", contraseña);
        editor.putString("rol", rol);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    public void recuperarPreferencias(){
        txt_username = (EditText) findViewById(R.id.username_input);
        pass = (EditText) findViewById(R.id.pass);
        SharedPreferences preferences=getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
        txt_username.setText(preferences.getString("usuario","correo@gmail.com"));
        pass.setText(preferences.getString("contraseña","12345"));
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