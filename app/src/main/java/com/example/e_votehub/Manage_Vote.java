package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Manage_Vote extends AppCompatActivity {
    Button btnaddvotes,btndeletevotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vote);
        btnaddvotes=findViewById(R.id.btnaddvotes);
        btndeletevotes=findViewById(R.id.btndeletevotes);
        btnaddvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manage_Vote.this, Create_Vote.class);
                startActivity(intent);
            }
        });
        btndeletevotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manage_Vote.this, DeleteVoteActivity.class);
                startActivity(intent);
            }
        });
    }
}