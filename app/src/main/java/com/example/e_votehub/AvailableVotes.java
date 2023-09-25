package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableVotes extends AppCompatActivity {

    private ListView listViewVotes;
    private ArrayAdapter<VoteModel> votesAdapter;
    private List<VoteModel> voteList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_votes);

        listViewVotes = findViewById(R.id.listViewVotes);

        databaseHelper = new DatabaseHelper(this);
        int loggedInUserId = databaseHelper.getLoggedInUserId();
        String loogedinusername=databaseHelper.getUsernameFromUserId(loggedInUserId);
        Log.d("the username is","username is:"+loogedinusername);


        List<VoteModel> voteList = databaseHelper.getVotesForUsername(loogedinusername);
        Log.d("AvailableVotes", "Number of votes retrieved: " + voteList.size());

        votesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, voteList);

        listViewVotes.setAdapter(votesAdapter);

        listViewVotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event when the user clicks on a vote
                VoteModel selectedVote = voteList.get(position);
                int voteId = selectedVote.getVoteId();
                String voteTitle = selectedVote.getVoteTitle();

                // Start a new activity to show the candidates for the selected vote
                Intent intent = new Intent(AvailableVotes.this, CandidatesActivity.class);
                intent.putExtra("vote_id", voteId);
                intent.putExtra("vote_title", voteTitle);
                startActivity(intent);
            }
        });
    }

}
