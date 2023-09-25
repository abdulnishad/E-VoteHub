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

public class ResultAdapter extends ArrayAdapter<ResultModel> {

    public ResultAdapter(Context context, List<ResultModel> resultModels) {
        super(context, 0, resultModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ResultModel resultModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_result, parent, false);
        }

        TextView textViewCandidateName = convertView.findViewById(R.id.textViewCandidateName);
        TextView textViewVoteCount = convertView.findViewById(R.id.textViewVoteCount);

        textViewCandidateName.setText(resultModel.getCandidateName());
        textViewVoteCount.setText(String.valueOf(resultModel.getVoteCount()));

        return convertView;
    }
}

