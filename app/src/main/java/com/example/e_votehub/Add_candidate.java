package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Add_candidate extends AppCompatActivity {

    private EditText etFullName, etVoteId, etPosition;
    private Button btnSave, btnPosition;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);

        etFullName = findViewById(R.id.editTextFullName);
        etVoteId = findViewById(R.id.addcanditatevoteid);
        etPosition = findViewById(R.id.addcanditatevoteposition);
        btnSave = findViewById(R.id.btnaddcandidatesave);
        btnPosition = findViewById(R.id.btnpositionrevile);

        databaseHelper = new DatabaseHelper(this);

        btnPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voteIdStr = etVoteId.getText().toString().trim();
                if (voteIdStr.isEmpty()) {
                    Toast.makeText(Add_candidate.this, "Please enter a valid Vote ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                int voteId = Integer.parseInt(voteIdStr);
                String position = databaseHelper.getVotePositionById(voteId);
                if (position == null) {
                    Toast.makeText(Add_candidate.this, "Invalid Voter ID", Toast.LENGTH_SHORT).show();
                } else {
                    etPosition.setText(position);
                    Toast.makeText(Add_candidate.this, "Position retrieved successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCandidate();
            }
        });
    }

    private void saveCandidate() {
        String fullName = etFullName.getText().toString().trim();
        String vidString = etVoteId.getText().toString().trim();
        String position = etPosition.getText().toString().trim();

        if (fullName.isEmpty() || vidString.isEmpty() || position.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int vid = Integer.parseInt(vidString);

        // Check if the voter ID exists in the vote database
        String votePosition = databaseHelper.getVotePositionById(vid);
        if (votePosition == null) {
            Toast.makeText(this, "Invalid voter ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Now you can add the candidate to the database
        long candidateId = databaseHelper.addCandidate(fullName, position, vid);

        if (candidateId != -1) {
            Toast.makeText(this, "Candidate added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add candidate. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}