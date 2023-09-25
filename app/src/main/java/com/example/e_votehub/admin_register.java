package com.example.e_votehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class admin_register extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText adminIdEditText;
    private EditText adminPasswordEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        databaseHelper = new DatabaseHelper(this);

        adminIdEditText = findViewById(R.id.adminIdEditText);
        adminPasswordEditText = findViewById(R.id.adminPasswordEditText);
        saveButton = findViewById(R.id.saveadminregister);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAdminCredentials();
            }
        });
    }

    private void saveAdminCredentials() {
        String adminId = adminIdEditText.getText().toString().trim();
        String adminPassword = adminPasswordEditText.getText().toString().trim();

        if (adminId.isEmpty() || adminPassword.isEmpty()) {
            Toast.makeText(this, "Please enter both Admin ID and Password", Toast.LENGTH_SHORT).show();

            return;
        }

        // Check if the admin credentials are already registered
        if (databaseHelper.checkAdminCredentials(adminId, adminPassword)) {
            Toast.makeText(this, "Admin credentials already registered", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the admin credentials to the database
        boolean isAdded = databaseHelper.addAdminCredentials(adminId, adminPassword);
        if (isAdded) {
            Toast.makeText(this, "Admin credentials saved successfully", Toast.LENGTH_SHORT).show();
            // Optionally, you can navigate to another activity after saving the admin credentials.
            // For example, go to the Admin Dashboard activity.
        } else {
            Toast.makeText(this, "Failed to save admin credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
