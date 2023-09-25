package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CandidatesActivity extends AppCompatActivity {

    private RadioGroup radioGroupCandidates;
    private DatabaseHelper databaseHelper;
    private int voteId;
    private String votePosition;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates);

        radioGroupCandidates = findViewById(R.id.radioGroupCandidates);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve voteId and votePosition from the intent
        voteId = getIntent().getIntExtra("vote_id", -1);
        votePosition = getIntent().getStringExtra("vote_position");

        // Retrieve candidates for the selected vote from the database
        List<CandidateModel> candidates = databaseHelper.getCandidatesByVoteId(voteId);

        // Create radio buttons for each candidate
        for (CandidateModel candidate : candidates) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(candidate.getFullName());
            radioButton.setId(candidate.getCandidateId());
            radioGroupCandidates.addView(radioButton);
        }
        radioGroupCandidates.setOnCheckedChangeListener((group, checkedId) -> {
            // Get the selected candidate ID from the checked radio button
            int selectedCandidateId = checkedId;

            // Call the castVote method to handle voting
            castVote(selectedCandidateId);
        });
    }

    // Method to handle voting and prevent multiple votes for the same user and vote ID
    private void castVote(int selectedCandidateId) {
        // Check if the user has already voted for this vote ID
        boolean hasVoted = databaseHelper.hasUserVoted(voteId,databaseHelper.getLoggedInUserId());
        if (!hasVoted) {
            // Save the user's vote in the ResultTable
            int loggedInUserId = databaseHelper.getLoggedInUserId();
            databaseHelper.addUserVoteResult(voteId, selectedCandidateId, loggedInUserId);

            // Display a success message
            Toast.makeText(this, "Vote casted successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CandidatesActivity.this, thankyou.class);
            startActivity(intent);
            finish();

            // Finish the current activity to prevent the user from going back to the CandidatesActivity

        } else {
            // Display an error message (user already voted for this vote)
            Toast.makeText(this, "You have already casted your vote for this vote.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CandidatesActivity.this, thankyou.class);
            startActivity(intent);
            finish();
        }
    }

}
