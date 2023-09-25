package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    private EditText txtName, txtEmail, txtUsername, txtPassword;
    private Button btnSignUp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtName = findViewById(R.id.txtname);
        txtEmail = findViewById(R.id.txtemail);
        txtUsername = findViewById(R.id.txtusername);
        txtPassword = findViewById(R.id.txtpassword);
        btnSignUp = findViewById(R.id.signup);

        databaseHelper = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (databaseHelper.checkUserExists(username)) {
                        Toast.makeText(signup.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        if (databaseHelper.addUser(name, email, username, password)) {
                            Toast.makeText(signup.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(signup.this,startpage.class);
                            startActivity(intent);
                            // You can add further logic here, like navigating to another activity.
                        } else {
                            Toast.makeText(signup.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}