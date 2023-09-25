package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DeleteVoteActivity extends AppCompatActivity {

    private Spinner voteSpinner;
    private Button deleteButton;
    private DatabaseHelper databaseHelper;
    private List<VoteModel> voteList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_vote);

        voteSpinner = findViewById(R.id.voteSpinner);
        deleteButton = findViewById(R.id.deleteButton);
        databaseHelper = new DatabaseHelper(this);

        loadVoteData();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedVoteId = getSelectedVoteId();
                if (selectedVoteId != -1) {
                    boolean isDeleted = databaseHelper.deleteVote(selectedVoteId);
                    if (isDeleted) {
                        Toast.makeText(DeleteVoteActivity.this, "Vote deleted successfully!", Toast.LENGTH_SHORT).show();
                        loadVoteData();
                    } else {
                        Toast.makeText(DeleteVoteActivity.this, "Failed to delete vote.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeleteVoteActivity.this, "Please select a vote to delete.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadVoteData() {
        voteList = databaseHelper.getAllVotes();
        List<String> voteTitles = new ArrayList<>();
        for (VoteModel vote : voteList) {
            voteTitles.add(vote.getVoteTitle());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, voteTitles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voteSpinner.setAdapter(spinnerAdapter);

        voteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection if needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private int getSelectedVoteId() {
        int selectedPosition = voteSpinner.getSelectedItemPosition();
        if (selectedPosition != Spinner.INVALID_POSITION && selectedPosition < voteList.size()) {
            return voteList.get(selectedPosition).getVoteId();
        }
        return -1;
    }
}
