package com.example.e_votehub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class CandidateAdapter extends ArrayAdapter<CandidateModel> {

    private Context context;
    private List<CandidateModel> candidates;

    public CandidateAdapter(Context context, List<CandidateModel> candidates) {
        super(context, 0, candidates);
        this.context = context;
        this.candidates = candidates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_candidate, parent, false);
        }

        CandidateModel candidate = candidates.get(position);

        TextView candidateTextView = convertView.findViewById(R.id.candidateTextView);
        candidateTextView.setText(candidate.getFullName());

        return convertView;
    }
}

