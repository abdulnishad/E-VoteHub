package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    TextView txtdidntcreateaccount;

    private DatabaseHelper databaseHelper;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        databaseHelper = new DatabaseHelper(this);


        usernameEditText = findViewById(R.id.userid);
        passwordEditText = findViewById(R.id.userpassword);
        loginButton = findViewById(R.id.btnloginuser);
        txtdidntcreateaccount=findViewById(R.id.txtdidntcreateaccount);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        txtdidntcreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both Username and Password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the user credentials are valid
        boolean isValidUser = databaseHelper.checkUserCredentials(username, password);
        if (isValidUser) {
            int userId = databaseHelper.checkUserCredentialsid(username, password);
            databaseHelper.setLoggedInUserId(userId);
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, User_menu.class);
            startActivity(intent);
        } else {

            Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
}