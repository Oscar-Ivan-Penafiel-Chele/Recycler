package com.example.recycler_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
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