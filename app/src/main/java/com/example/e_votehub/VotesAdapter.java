package com.example.e_votehub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class VotesAdapter extends ArrayAdapter<VoteModel> {

    private List<VoteModel> voteList;
    private OnVoteClickListener onVoteClickListener;

    public VotesAdapter(Context context, List<VoteModel> voteList, OnVoteClickListener onVoteClickListener) {
        super(context, 0, voteList);
        this.voteList = voteList;
        this.onVoteClickListener = onVoteClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        VoteModel vote = voteList.get(position);
        Log.d("VotesAdapter", "Vote title at position " + position + ": " + vote.getVoteTitle());
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_vote, parent, false);
        }

        TextView textViewVotePosition = convertView.findViewById(R.id.textViewVotePosition);
        textViewVotePosition.setText(vote.getVoteTitle());

        convertView.setOnClickListener(v -> {
            if (onVoteClickListener != null) {
                onVoteClickListener.onVoteClick(vote.getVoteId(), vote.getVoteTitle());
            }
        });

        return convertView;
    }

    public interface OnVoteClickListener {
        void onVoteClick(int voteId, String voteTitle);
    }
}
