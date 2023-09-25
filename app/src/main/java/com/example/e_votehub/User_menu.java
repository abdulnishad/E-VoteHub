package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_menu extends AppCompatActivity {
    Button btnviewresult,btncastvote;
    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        btncastvote=findViewById(R.id.btncastvote);
        btnviewresult=findViewById(R.id.btnresultview);
        databaseHelper = new DatabaseHelper(this);
        String usern = databaseHelper.getLoggedInUser();
        TextView txtStartName = findViewById(R.id.txtstartname);
        txtStartName.setText(usern);
        btncastvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_menu.this, AvailableVotes.class);
                startActivity(intent);
            }
        });
        btnviewresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_menu.this, View_Result.class);
                startActivity(intent);
            }
        });
        Button btnLogout = findViewById(R.id.btnlogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout method and navigate to the login activity
                logout();
            }
        });

    }
    private void logout() {
        // Clear any saved user data or session
        // For example, you can clear the logged-in user ID from the session table
        databaseHelper.setLoggedInUserId(-1);

        // Navigate to the login activity
        Intent intent = new Intent(User_menu.this, startpage.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent the user from going back to the User_menu activity
    }

}