package com.example.recycler_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

public class register extends AppCompatActivity {

    EditText nombre1, apellido1, telefono1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);
        nombre1=(EditText)findViewById(R.id.name_input);
        apellido1=(EditText)findViewById(R.id.lastname_input);
        telefono1=(EditText)findViewById(R.id.phone_input);


    }
    public void next(View v){
        String nombre, apellido, telefono;
        nombre = nombre1.getText().toString();
        apellido = apellido1.getText().toString();
        telefono = telefono1.getText().toString();
        if(nombre.equals("") || apellido.equals("")||telefono.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent next = new Intent(this, register_2.class);
        next.putExtra("nombre",nombre);
        next.putExtra("apellido",apellido);
        next.putExtra("telefono",telefono);
        startActivity(next);
    }
}