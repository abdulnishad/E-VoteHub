package com.example.e_votehub;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CandidateList extends AppCompatActivity {

    private ListView candidateListView;
    private EditText enterVoteIdEditText;
    private Button viewCandidatesButton;
    private DatabaseHelper databaseHelper;
    private List<CandidateModel> candidateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Get references to UI elements
        candidateListView = findViewById(R.id.candidateListView);
        enterVoteIdEditText = findViewById(R.id.selectedid);
        viewCandidatesButton = findViewById(R.id.viewCandidatesButton);

        // Set up click listener for the "View Candidates" button
        viewCandidatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voteIdText = enterVoteIdEditText.getText().toString().trim();

                if (voteIdText.isEmpty()) {
                    Toast.makeText(CandidateList.this, "Please enter a vote ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int selectedVoteId = Integer.parseInt(voteIdText);
                    fetchCandidates(selectedVoteId);
                } catch (NumberFormatException e) {
                    Toast.makeText(CandidateList.this, "Invalid vote ID format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchCandidates(int selectedVoteId) {
        // Fetch candidates for the selected voteId
        candidateList = databaseHelper.getCandidatesByVoteId(selectedVoteId);

        // Set up the ListView and Adapter


        if (candidateList.isEmpty()) {
            Toast.makeText(this, "No candidates found for the selected vote ID", Toast.LENGTH_SHORT).show();
        } else {

            CandidateAdapter adapter = new CandidateAdapter(this, candidateList);
            candidateListView.setAdapter(adapter);
        }
    }
}
