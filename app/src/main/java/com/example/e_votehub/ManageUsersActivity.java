package com.example.e_votehub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private List<UserModel> userList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        // Fetch the user list from the database
        userList = databaseHelper.getAllUsers();

        // Create and set the custom ArrayAdapter to the ListView
        UserListAdapter adapter = new UserListAdapter(this, userList);
        listView.setAdapter(adapter);
    }

}
