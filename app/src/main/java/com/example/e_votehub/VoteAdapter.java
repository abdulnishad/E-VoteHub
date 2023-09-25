package com.example.e_votehub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VoteAdapter extends ArrayAdapter<VoteModel> {

    private Context context;
    private List<VoteModel> voteList;

    public VoteAdapter(Context context, List<VoteModel> voteList) {
        super(context, 0, voteList);
        this.context = context;
        this.voteList = voteList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.activity_vote_adapter, parent, false);
        }

        VoteModel currentVote = voteList.get(position);

        TextView voteIdTextView = listItemView.findViewById(R.id.voteIdTextView);
        TextView positionTextView = listItemView.findViewById(R.id.positionTextView);

        // Set the voteid and position in the respective TextViews
        voteIdTextView.setText("Vote ID: " + String.valueOf(currentVote.getVoteId()));
        positionTextView.setText("Position: " + currentVote.getVoteTitle());

        return listItemView;
    }
}