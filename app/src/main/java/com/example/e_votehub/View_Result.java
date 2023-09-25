package com.example.e_votehub;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class View_Result extends AppCompatActivity {

    private ListView listView;
    private List<ResultModel> resultModels;
    private int voteId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

        listView = findViewById(R.id.listViewResults);
        databaseHelper = new DatabaseHelper(this);

        // Get the vote ID from the EditText
        EditText editTextVoteId = findViewById(R.id.editTextVoteId);
        Button buttonShowResults = findViewById(R.id.buttonShowResults);

        buttonShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voteIdString = editTextVoteId.getText().toString();
                if (!voteIdString.isEmpty()) {
                    voteId = Integer.parseInt(voteIdString);
                    resultModels = databaseHelper.getVoteResults(voteId);

                    // Calculate total vote count
                    int totalVoteCount = 0;
                    for (ResultModel resultModel : resultModels) {
                        totalVoteCount += resultModel.getVoteCount();
                    }

                    // Add total vote count to the list
                    ResultModel totalVoteResult = new ResultModel(-1, "Total Votes", totalVoteCount);
                    resultModels.add(totalVoteResult);

                    // Display the results in ListView
                    ResultAdapter adapter = new ResultAdapter(View_Result.this, resultModels);
                    listView.setAdapter(adapter);
                }
            }
        });
    }
}
