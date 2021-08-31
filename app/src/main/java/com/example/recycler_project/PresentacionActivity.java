package com.example.recycler_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class PresentacionActivity extends AppCompatActivity {
    //ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);
        //progressBar.findViewById(R.id.progressBar1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: abrir la ventana de navegacion de acuerdo al rol de la sesion iniciada

                SharedPreferences preferences=getSharedPreferences("preferenciaslogin", Context.MODE_PRIVATE);
                boolean sesion=preferences.getBoolean("sesion",false);
                int rol = preferences.getInt("rol",0);

                if(sesion){ //Si alguien inicio sesion y no la ha cerrado se abre la actiity de navegacion
                    if(rol == 2){
                        Intent intent = new Intent(getApplicationContext(), Navigation.class);
                        startActivity(intent);
                        finish();
                    }else if(rol == 1){
                        Intent intent = new Intent(getApplicationContext(), NavigationAdmin.class);
                        startActivity(intent);
                        finish();
                    }

                }else{ //Si no hay sesion iniciada se abre MainActivity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000); //MAntiene dos segundos el la barra de progreso hasta validar
    }
}
