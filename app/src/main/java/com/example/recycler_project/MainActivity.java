package com.example.recycler_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button botonIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        botonIngresar = findViewById(R.id.botonIn);
        botonIngresar.setOnClickListener(this::login);
    }

    public void login(View v){
        Intent login_view = new Intent(this, login.class);
        startActivity(login_view);
    }

    public void register(View v){
        Intent register_view = new Intent(this, register.class);
        startActivity(register_view);
    }
}