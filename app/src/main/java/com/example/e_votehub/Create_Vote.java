package com.example.e_votehub;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Create_Vote extends AppCompatActivity {

    private EditText etPosition;
    private Button btnSelectUsers, btnSave;
    private List<String> selectedUsers = new ArrayList<>();
    DatabaseHelper databaseHelper;
    private final ActivityResultLauncher<Intent> selectUsersLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        selectedUsers = data.getStringArrayListExtra("selectedUsers");
                    }
                }
            }
    );


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vote);

        etPosition = findViewById(R.id.txtposition);
        btnSelectUsers = findViewById(R.id.btnselectusers);
        btnSave = findViewById(R.id.btnsavecreatevote);

        selectedUsers = new ArrayList<>();
        databaseHelper=new DatabaseHelper(this);

        btnSelectUsers.setOnClickListener(v -> {
            Intent intent = new Intent(Create_Vote.this, selectuser.class);
            selectUsersLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String position = etPosition.getText().toString().trim();
            saveVote(position, selectedUsers);
        });
    }





    // Callback to receive selected users from SelectUsersActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedUsers = data.getStringArrayListExtra("selectedUsers");
        }
    }

    private void saveVote(String position, List<String> selectedUsers) {
        // DatabaseHelper databaseHelper; // Make sure you have initialized databaseHelper before using it.

        if (position.isEmpty()) {
            Toast.makeText(this, "Please enter the position", Toast.LENGTH_SHORT).show();
            return;
        }

        long voteId = databaseHelper.addVote(position, selectedUsers);

        if (voteId != -1) {
            Toast.makeText(this, "Vote created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to create vote. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

}