package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class selectuser extends AppCompatActivity {

    private ListView listView;
    private List<UserModel> userList;
    private List<String> selectedUsers;
    private Button btnDone;
    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectuser);

        listView = findViewById(R.id.listView);
        btnDone = findViewById(R.id.btnSaveUsers);

        selectedUsers = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        // Fetch all users from the database
        // DatabaseHelper databaseHelper; // Make sure you have initialized databaseHelper before using it.
        userList = databaseHelper.getAllUsers();

        // Create a list of usernames to display in the ListView
        List<String> usernames = new ArrayList<>();
        for (UserModel user : userList) {
            usernames.add(user.getUsername());
        }

        // Set up the ListView with users
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, usernames);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Handle item clicks to add/remove users from the selectedUsers list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String username = usernames.get(position);
                if (selectedUsers.contains(username)) {
                    selectedUsers.remove(username);
                } else {
                    selectedUsers.add(username);
                }
            }
        });

        // Handle Done button click
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return the selected users to CreateVoteActivity
                Intent intent = new Intent();
                intent.putStringArrayListExtra("selectedUsers", (ArrayList<String>) selectedUsers);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
