package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class startpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
    }

    public void callLogin(View view) {
        Intent loginIntent = new Intent(this, login.class);
        startActivity(loginIntent);
    }


    public void callSignup(View view) {
        Intent signupIntent = new Intent(this, signup.class);
        startActivity(signupIntent);
    }


    public void callLoginAsAdmin(View view) {
        Intent adminIntent = new Intent(this,login_admin.class);
        startActivity(adminIntent);
    }
}
