package com.example.recycler_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        btn_login.setOnClickListener(this::home);
    }

    public void home(View v){
        if(username != ""){
            Intent next = new Intent(this, Navigation.class);
            startActivity(next);
        }else {
            Intent next = new Intent(this, Navigation.class);
            startActivity(next);
        }

    }
}