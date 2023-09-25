package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class login_admin extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText adminIdEditText;
    private EditText adminPasswordEditText;
    private Button loginButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        databaseHelper = new DatabaseHelper(this);

        adminIdEditText = findViewById(R.id.adminIdEditText);
        adminPasswordEditText = findViewById(R.id.adminPasswordEditText);
        loginButton = findViewById(R.id.btnloginadmin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAsAdmin();
            }
        });
    }

    private void loginAsAdmin() {
        String adminId = adminIdEditText.getText().toString().trim();
        String adminPassword = adminPasswordEditText.getText().toString().trim();

        if (adminId.isEmpty() || adminPassword.isEmpty()) {
            Toast.makeText(this, "Please enter both Admin ID and Password", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean isAdmin = databaseHelper.checkAdminCredentials(adminId, adminPassword);
        if (isAdmin) {
            Intent intent=new Intent(this,Admin_main.class);
            startActivity(intent);
            Toast.makeText(this, "Login successful as Admin", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Invalid Admin credentials. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}