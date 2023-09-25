package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class VoteListActivity extends AppCompatActivity {

    private ListView listViewVotes;
    private List<VoteModel> voteList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_list);

        databaseHelper = new DatabaseHelper(this);

        listViewVotes = findViewById(R.id.listViewVotes);

        voteList = databaseHelper.getAllVotes();

        // Create and set the adapter
        VoteAdapter voteAdapter = new VoteAdapter(this, voteList);
        listViewVotes.setAdapter(voteAdapter);

        // Handle item click events on the ListView
        listViewVotes.setOnItemClickListener((parent, view, position, id) -> {
            VoteModel selectedVote = voteList.get(position);
            // Handle the selected vote here as needed
        });
    }
}



