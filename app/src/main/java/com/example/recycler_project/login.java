package com.example.recycler_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        recuperarPreferencias();
    }
    public void login(View view){
        HttpsTrustManager.allowAllSSL(); //PErmite conexion incluso si expiro el ssl de la pagina (Página no segura)
        String usuario = txt_username.getText().toString();
        String contrasena = pass.getText().toString();

        if(usuario.equals("") || contrasena.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url="https://192.168.1.12/Sentencias/Login.php?usuario="+usuario+"&contrasena="+contrasena;
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url, (response) -> { //Logra realizar conexion
                    String idUsuario;
                    String[] datoUsuario=response.split("-");
                    response=datoUsuario[0];
                    idUsuario=datoUsuario[1];
            System.out.println(response+" "+idUsuario);
                    if(response.equalsIgnoreCase("Correcto")){ // usuario reciclador  rol=2
                        guardarPreferencias(usuario, contrasena, response, idUsuario);
                        Intent next = new Intent(this, Navigation.class);
                        startActivity(next);
                    }else if (response.equalsIgnoreCase("Admin")){ // usuario administrados  rol=1
                        guardarPreferencias(usuario, contrasena, response, idUsuario);
                        Intent next = new Intent(this, NavigationAdmin.class);
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

    public void guardarPreferencias(String usuario, String contraseña, String r, String idUsuario){ //guarda datos se sesion
        int rol = 0;

        if(r.equalsIgnoreCase("Correcto")){
            rol=2;
        }else if(r.equalsIgnoreCase("Admin")){
            rol=1;
        }

        SharedPreferences preferences = getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id",Integer.parseInt(idUsuario));
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

}